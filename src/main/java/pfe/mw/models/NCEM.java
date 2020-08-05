package pfe.mw.models;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.springframework.data.annotation.Transient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import io.kubernetes.client.models.*;
import io.kubernetes.client.util.Yaml;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.util.Config;

import org.zeromq.ZThread;

import pfe.mw.models.AdapterType;
import pfe.mw.models.Microservice;
import pfe.mw.models.NCConfigFile;
import pfe.mw.models.NodeComponent;
import pfe.mw.models.Status;

import io.kubernetes.client.models.V1ContainerState;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class NCEM {
	private String id;
	private String repSocketURL;
	private String pubSocketURL;
	private String routerSocketURL;
	private NodeComponent nc;

	String UPLOADED_FOLDER = "";
	String NODE_ROUTER_IP_ADDRESS;
	String SHAPER_IP_ADDRESS;
	String CLASSIFIER_IP_ADDRESS;
	// variable made static in order to be accessed by all NCEM instances
	static String MAIN_ROUTER_IP_ADDRESS = "";
	@Transient
	ZContext contextUsedToContactMsForMonitoring;
	@Transient
	ZContext context;
	@Transient
	ZContext contextConfiguration;
	@Transient
	ZMQ.Socket configSocket;
	@Transient
	CoreV1Api api;
	@Transient
	final transient GsonBuilder builder = new GsonBuilder();
	@Transient
	final transient Gson gson = builder.setPrettyPrinting().create();
	ZMQ.Socket MonitoringSocket;
	public NCEM(NodeComponent nc, String repSocketURL, String pubSocketURL, String routerSocketURL)throws Exception {

		this.id = nc.getId();
		this.nc = nc;
		this.repSocketURL = repSocketURL;
		this.pubSocketURL = pubSocketURL;
		this.routerSocketURL=routerSocketURL;
		this.init();
		this.runNCEM();

	}

	// k8s API
	public void init() throws IOException {

		// connect to the API Server
		ApiClient client = Config.defaultClient();
		// client.setDebugging(true);
		Configuration.setDefaultApiClient(client);
		api = new CoreV1Api();

	}

	public void runNCEM() {
		/**Setup socket for configuration**/
		launchConfigSocket();
		/** Respond the Router and µs requests **/
		context = new ZContext();
		try {
		contextUsedToContactMsForMonitoring = new ZContext();
		MonitoringSocket = contextUsedToContactMsForMonitoring.createSocket(SocketType.ROUTER);
		System.out.println(routerSocketURL);
		MonitoringSocket.bind(routerSocketURL);}catch(Exception e) {}

		
		ZMQ.Socket socketUsedForLaunchingThread = ZThread.fork(context, new ZThread.IAttachedRunnable() {
			@Override
			public void run(Object[] args, ZContext ctx, ZMQ.Socket pipe) {
				ZMQ.Socket socket = context.createSocket(SocketType.REP);
				socket.bind(repSocketURL);
				while (!Thread.currentThread().isInterrupted()) {
					byte[] source = socket.recv(0);
					byte[] msg = socket.recv(0);
					String response;
					System.out.println("Received : [ " + new String(source, ZMQ.CHARSET) + " ]" + " [ "
							+ new String(msg, ZMQ.CHARSET) + " ]");
					if (new String(source, ZMQ.CHARSET).equals("mainRouter")) {
						MAIN_ROUTER_IP_ADDRESS = new String(msg, ZMQ.CHARSET);

						response = "tcp://" + MAIN_ROUTER_IP_ADDRESS + ":2222" + "-tcp://" + MAIN_ROUTER_IP_ADDRESS
								+ ":2224" + "-tcp://" + MAIN_ROUTER_IP_ADDRESS + ":2223" + "-"
								+ gson.toJson(nc.getMainRouter().getMs_node_map());
						System.out.println(">>> " + response);
						socket.send(response.getBytes(ZMQ.CHARSET), 0);
					} else {
						if (new String(source, ZMQ.CHARSET).equals("nodeRouter")) {
							NODE_ROUTER_IP_ADDRESS = new String(msg, ZMQ.CHARSET);
							response = nc.getRouter().getRouterUniqueIdentifier() + "-tcp://" + MAIN_ROUTER_IP_ADDRESS
									+ ":2222" + "-tcp://" + MAIN_ROUTER_IP_ADDRESS + ":2223" + "-tcp://"
									+ MAIN_ROUTER_IP_ADDRESS + ":2224" + "-tcp://" + NODE_ROUTER_IP_ADDRESS + ":"
									+ nc.getRouter().getClientServerRouterURL() + "-tcp://" + NODE_ROUTER_IP_ADDRESS
									+ ":" + nc.getRouter().getConfPubSubRouterFrontendURL() + "-tcp://"
									+ NODE_ROUTER_IP_ADDRESS + ":" + nc.getRouter().getConfPubSubRouterBackendURL()
									+ "-tcp://" + NODE_ROUTER_IP_ADDRESS + ":"
									+ nc.getRouter().getPubSubRouterFrontendURL() + "-tcp://" + NODE_ROUTER_IP_ADDRESS
									+ ":" + nc.getRouter().getPubSubRouterBackendURL() + "-"
									+ gson.toJson(nc.getRouter().getRoutingTable());
							System.out.println(">>> " + response);
							socket.send(response.getBytes(ZMQ.CHARSET), 0);
							updateRouter();
						} else {
							if (new String(source, ZMQ.CHARSET).equals("shaper")) {
								SHAPER_IP_ADDRESS = new String(msg, ZMQ.CHARSET);
								JsonObject shaperConf = new JsonObject();
								shaperConf.addProperty("routerRouterSocketURL", "tcp://" + NODE_ROUTER_IP_ADDRESS + ":"
										+ nc.getRouter().getClientServerRouterURL());
								shaperConf.addProperty("shaperDealerSocketURL", "tcp://" + SHAPER_IP_ADDRESS + ":2222");
								shaperConf.addProperty("ncemConfSocketURL", pubSocketURL );
								shaperConf.addProperty("id", nc.getId()+"Shaper");
								socket.send(gson.toJson(shaperConf).getBytes(ZMQ.CHARSET), 0);

							} else {
								if (new String(source, ZMQ.CHARSET).equals("classifier")) {
									CLASSIFIER_IP_ADDRESS = new String(msg, ZMQ.CHARSET);
									JsonObject classifierConf = new JsonObject();
									classifierConf.addProperty("routerRouterSocketURL", "tcp://" + NODE_ROUTER_IP_ADDRESS
											+ ":" + nc.getRouter().getClientServerRouterURL());
									classifierConf.addProperty("classifierDealerSocketURL",
											"tcp://" + CLASSIFIER_IP_ADDRESS + ":2222");
									classifierConf.addProperty("shaperDealerSocketURL", "tcp://" + SHAPER_IP_ADDRESS + ":2222");
									classifierConf.addProperty("ncemConfSocketURL", pubSocketURL );
									classifierConf.addProperty("id", nc.getId()+"Classifier");
									socket.send(gson.toJson(classifierConf).getBytes(ZMQ.CHARSET), 0);

								} else {
									String key = new String(source, ZMQ.CHARSET);
									String id = new String(msg, ZMQ.CHARSET);
									response = id + "-tcp://" + NODE_ROUTER_IP_ADDRESS + ":"
											+ nc.getRouter().getClientServerRouterURL() + "-tcp://"
											 + NODE_ROUTER_IP_ADDRESS + ":"
											+ nc.getRouter().getConfPubSubRouterBackendURL() + "-tcp://"
											+ NODE_ROUTER_IP_ADDRESS + ":" + nc.getRouter().getPubSubRouterFrontendURL()
											+ "-tcp://" + NODE_ROUTER_IP_ADDRESS + ":"
											+ nc.getRouter().getPubSubRouterBackendURL()+"-"
													+routerSocketURL;
									System.out.println("Ncem to MS>>> " + response);
									socket.send(response.getBytes(ZMQ.CHARSET), 0);
									updateRouter();
								}
							}
						}

						/*
						 * try { Thread.sleep(1000); } catch (InterruptedException e) {
						 * e.printStackTrace(); }
						 */

					}
					
				}
				socket.close();
				context.close();
			}
		});
	}
	///////
	/////
	////
	///
	//
	private void launchConfigSocket() {
		try {
			 contextConfiguration = new ZContext();
			 configSocket = contextConfiguration.createSocket(SocketType.PUB);
			 configSocket.bind(pubSocketURL);
		}catch(Exception e) {
			
		}
		 
	}
	// send Routing Table
	public void updateRouter() {
			configSocket.sendMore("ConfRouter");
			System.out.println("Send ConfRouter   " + gson.toJson(nc.getRouter().getRoutingTable()));
			configSocket.send("" + gson.toJson(nc.getRouter().getRoutingTable()));
	}
	public void updateClassifier() {
		configSocket.sendMore("ConfClassifier");
		System.out.println("Send ConfClassifier   " + gson.toJson(nc.getClassifier().getPolicy()));
		configSocket.send("" + gson.toJson(nc.getClassifier().getPolicy()));
	}
	public void updateShaper() {
		configSocket.sendMore("ConfShaper");
		System.out.println("Send ConfShaper   " + gson.toJson(nc.getShaper().getPolicy()));
		configSocket.send("" + gson.toJson(nc.getShaper().getPolicy()));
	}

	// send MS conf
	public void configureMicroservice(String nodeName, String idMicroservice, String confMessage) {
			configSocket.sendMore("conf" + nodeName + "#" + idMicroservice);
			configSocket.send(confMessage);
	}

	// each application has its one namespace
	public static void createNameSpace(String appName) throws IOException, ApiException {
		ApiClient client = Config.defaultClient();
		Configuration.setDefaultApiClient(client);
		CoreV1Api api = new CoreV1Api();
		api.createNamespace(new V1Namespace().metadata(new V1ObjectMeta().name(appName)), null, null, null);
	}

	// deploy a config map file in the cluster
	public void deployConfigMap(NCConfigFile ncConfigFile) throws IOException, ApiException {

		V1ObjectMeta meta = new V1ObjectMeta();
		meta.name(ncConfigFile.getConfigFileName());
		Map<String, String> labels = new HashMap<>();
		labels.put("app", ncConfigFile.getConfigFileName());
		meta.labels(labels);

		Map<String, String> data = new HashMap<>();
		String string = "";
		for (String item : ncConfigFile.getData().values()) {
			string = string + item + "\n";
		}

		data.put("ncem.properties", string);

		V1ConfigMap configMap = new V1ConfigMap();
		configMap.apiVersion("v1");
		configMap.kind("ConfigMap");
		configMap.metadata(meta);

		configMap.data(data);

		api.createNamespacedConfigMap("" + nc.getAppName(), configMap, null, null, null);
	}

	// deploy a router
	public void deployRouter(String idRouter, String routerImageName, String host, String configMapName)
			throws Exception {

		// -------metadata
		V1ObjectMeta meta = new V1ObjectMeta();
		meta.name(idRouter + "-pod");
		Map<String, String> labels = new HashMap<>();
		labels.put("app", idRouter + "-pod");
		meta.labels(labels);

		// -------
		V1Container container = new V1Container();
		container.name(idRouter + "-container");
		container.image(routerImageName);
		container.imagePullPolicy("Always"); // or ifNotPresent

		V1VolumeMount v1VolumeMount = new V1VolumeMount();
		v1VolumeMount.name(idRouter + "-config-map-volume");
		v1VolumeMount.mountPath("NCEM.properties");
		v1VolumeMount.subPath("ncem.properties");
		container.volumeMounts(Arrays.asList(v1VolumeMount));

		// --------------------
		V1ObjectFieldSelector v1ObjectFieldSelector = new V1ObjectFieldSelector();
		v1ObjectFieldSelector.fieldPath("status.podIP");

		V1EnvVarSource envVarSource = new V1EnvVarSource();
		envVarSource.fieldRef(v1ObjectFieldSelector);

		V1EnvVar env1 = new V1EnvVar();
		env1.name("ROUTER_IP_ADDRESS");
		env1.valueFrom(envVarSource);

		container.env(Arrays.asList(env1));

		// --------
		V1Volume volume = new V1Volume();
		volume.name(idRouter + "-config-map-volume");
		V1ConfigMapVolumeSource v1ConfigMapVolumeSource = new V1ConfigMapVolumeSource();
		v1ConfigMapVolumeSource.name(configMapName);
		volume.configMap(v1ConfigMapVolumeSource);

		// ---------
		V1PodSpec spec = new V1PodSpec();
		spec.containers(Arrays.asList(container));
		spec.volumes(Arrays.asList(volume));
		spec.nodeName(host);

		V1Pod podBody = new V1Pod();
		podBody.apiVersion("v1");
		podBody.kind("Pod");
		podBody.metadata(meta);
		podBody.spec(spec);

		V1Pod pod = api.createNamespacedPod("" + nc.getAppName(), podBody, null, null, null);

	}
	
	//deploy shaper
	public void deployShaper(String idShaper, String shaperImageName, String host, String configMapName)
			throws Exception {

		// -------metadata
		V1ObjectMeta meta = new V1ObjectMeta();
		meta.name(idShaper + "-pod");
		Map<String, String> labels = new HashMap<>();
		labels.put("app", idShaper + "-pod");
		meta.labels(labels);

		// -------
		V1Container container = new V1Container();
		container.name(idShaper + "-container");
		container.image(shaperImageName);
		container.imagePullPolicy("Always"); // or ifNotPresent

		V1VolumeMount v1VolumeMount = new V1VolumeMount();
		v1VolumeMount.name(idShaper + "-config-map-volume");
		v1VolumeMount.mountPath("NCEM.properties");
		v1VolumeMount.subPath("ncem.properties");
		container.volumeMounts(Arrays.asList(v1VolumeMount));

		// --------------------
		V1ObjectFieldSelector v1ObjectFieldSelector = new V1ObjectFieldSelector();
		v1ObjectFieldSelector.fieldPath("status.podIP");

		V1EnvVarSource envVarSource = new V1EnvVarSource();
		envVarSource.fieldRef(v1ObjectFieldSelector);

		V1EnvVar env1 = new V1EnvVar();
		env1.name("SHAPER_IP_ADDRESS");
		env1.valueFrom(envVarSource);

		container.env(Arrays.asList(env1));

		// --------
		V1Volume volume = new V1Volume();
		volume.name(idShaper + "-config-map-volume");
		V1ConfigMapVolumeSource v1ConfigMapVolumeSource = new V1ConfigMapVolumeSource();
		v1ConfigMapVolumeSource.name(configMapName);
		volume.configMap(v1ConfigMapVolumeSource);

		// ---------
		V1PodSpec spec = new V1PodSpec();
		spec.containers(Arrays.asList(container));
		spec.volumes(Arrays.asList(volume));
		spec.nodeName(host);

		V1Pod podBody = new V1Pod();
		podBody.apiVersion("v1");
		podBody.kind("Pod");
		podBody.metadata(meta);
		podBody.spec(spec);

		V1Pod pod = api.createNamespacedPod("" + nc.getAppName(), podBody, null, null, null);

	}
	//deploy classifier
		public void deployClassifier(String idClassifier, String classifierImageName, String host, String configMapName)
				throws Exception {

			// -------metadata
			V1ObjectMeta meta = new V1ObjectMeta();
			meta.name(idClassifier + "-pod");
			Map<String, String> labels = new HashMap<>();
			labels.put("app", idClassifier + "-pod");
			meta.labels(labels);

			// -------
			V1Container container = new V1Container();
			container.name(idClassifier + "-container");
			container.image(classifierImageName);
			container.imagePullPolicy("Always"); // or ifNotPresent

			V1VolumeMount v1VolumeMount = new V1VolumeMount();
			v1VolumeMount.name(idClassifier + "-config-map-volume");
			v1VolumeMount.mountPath("NCEM.properties");
			v1VolumeMount.subPath("ncem.properties");
			container.volumeMounts(Arrays.asList(v1VolumeMount));

			// --------------------
			V1ObjectFieldSelector v1ObjectFieldSelector = new V1ObjectFieldSelector();
			v1ObjectFieldSelector.fieldPath("status.podIP");

			V1EnvVarSource envVarSource = new V1EnvVarSource();
			envVarSource.fieldRef(v1ObjectFieldSelector);

			V1EnvVar env1 = new V1EnvVar();
			env1.name("CLASSIFIER_IP_ADDRESS");
			env1.valueFrom(envVarSource);

			container.env(Arrays.asList(env1));

			// --------
			V1Volume volume = new V1Volume();
			volume.name(idClassifier + "-config-map-volume");
			V1ConfigMapVolumeSource v1ConfigMapVolumeSource = new V1ConfigMapVolumeSource();
			v1ConfigMapVolumeSource.name(configMapName);
			volume.configMap(v1ConfigMapVolumeSource);

			// ---------
			V1PodSpec spec = new V1PodSpec();
			spec.containers(Arrays.asList(container));
			spec.volumes(Arrays.asList(volume));
			spec.nodeName(host);

			V1Pod podBody = new V1Pod();
			podBody.apiVersion("v1");
			podBody.kind("Pod");
			podBody.metadata(meta);
			podBody.spec(spec);

			V1Pod pod = api.createNamespacedPod("" + nc.getAppName(), podBody, null, null, null);

		}

	// deploy microservice
	public void deployMservice(String id, String uniqueId, String imageName, String host, String configMapName)
			throws Exception {

		// -------metadata
		V1ObjectMeta meta = new V1ObjectMeta();
		meta.name(uniqueId + "-pod");
		Map<String, String> labels = new HashMap<>();
		labels.put("app", uniqueId + "-pod");
		meta.labels(labels);

		// -------
		V1Container container = new V1Container();
		container.name(uniqueId + "-container");
		container.image(imageName);
		container.imagePullPolicy("Always"); // or ifNotPresent

		V1EnvVar v1EnvVar = new V1EnvVar();
		v1EnvVar.name("CLIENT_ID");
		v1EnvVar.value(id);

		V1EnvVar v1EnvVar2 = new V1EnvVar();
		v1EnvVar2.name("ID");
		v1EnvVar2.value(uniqueId);
		container.env(Arrays.asList(v1EnvVar, v1EnvVar2));

		V1VolumeMount v1VolumeMount = new V1VolumeMount();
		v1VolumeMount.name(uniqueId + "-config-map-volume");
		v1VolumeMount.mountPath("NCEM.properties");
		v1VolumeMount.subPath("ncem.properties");
		container.volumeMounts(Arrays.asList(v1VolumeMount));

		// --------
		V1Volume volume = new V1Volume();
		volume.name(uniqueId + "-config-map-volume");
		V1ConfigMapVolumeSource v1ConfigMapVolumeSource = new V1ConfigMapVolumeSource();
		v1ConfigMapVolumeSource.name(configMapName);
		volume.configMap(v1ConfigMapVolumeSource);

		// ---------
		V1PodSpec spec = new V1PodSpec();
		spec.containers(Arrays.asList(container));
		spec.volumes(Arrays.asList(volume));
		spec.nodeName(host);

		V1Pod podBody = new V1Pod();
		podBody.apiVersion("v1");
		podBody.kind("Pod");
		podBody.metadata(meta);
		podBody.spec(spec);

		V1Pod pod = api.createNamespacedPod("" + nc.getAppName(), podBody, null, null, null);

	}

	// deploy adapter
	/*
	 * public void deployAdapter(AdapterType adapter) { try {
	 * this.deployMservice(adapter.toString(), adapter.toString(),
	 * "sofianechikhbled/" + adapter.toString(), this.getNc().getHostName(),
	 * this.getNc().getNcConfigFile().getConfigFileName()); } catch (Exception e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); }
	 * this.getNc().addAdapter(adapter); }
	 */

	public boolean deletePod(String name) throws Exception {

		V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
		for (V1Pod item : list.getItems()) {

			if (item.getMetadata().getName().equals(name)) {
				try {
					api.deleteNamespacedPod(name, "" + nc.getAppName(), new V1DeleteOptions(), null, null, null, null,
							null);
				} catch (Exception e) {
//					e.printStackTrace();
				}
				while (getPodStatus(name) != null)
					;

				return true;
			}
		}
		return false;

	}

	public ArrayList<String> getAllPodsName() throws IOException, io.kubernetes.client.ApiException {

		// connect to the API Server
		ApiClient client = Config.defaultClient();
		Configuration.setDefaultApiClient(client);

		CoreV1Api api = new CoreV1Api();
		ArrayList<String> podNames = new ArrayList<>();
		V1PodList list = api.listNamespacedPod("" + nc.getAppName(), null, null, null, null, null, null, null, null,
				null);
		for (V1Pod item : list.getItems()) {

			podNames.add(item.getMetadata().getName());
		}
		return podNames;
	}

	public Microservice getMicroserviceByName(String name) throws Exception {

		ApiClient client = Config.defaultClient();
		Configuration.setDefaultApiClient(client);

		CoreV1Api api = new CoreV1Api();

		V1PodList list = api.listNamespacedPod("" + nc.getAppName(), null, null, null, null, null, null, null, null,
				null);
		for (V1Pod item : list.getItems()) {
			if (item.getMetadata().getName().equals(name)) {
				return new Microservice(name, "docker", item.getSpec().getContainers().get(0).getImage());

			}
		}
		return null;

	}

	public void deleteConfigFile(String configFileName) throws Exception {

		ApiClient client = Config.defaultClient();
		Configuration.setDefaultApiClient(client);

		CoreV1Api api = new CoreV1Api();

		V1ConfigMapList list = api.listConfigMapForAllNamespaces(null, null, null, null, null, null, null, null, null);
		for (V1ConfigMap item : list.getItems()) {

			if (item.getMetadata().getName().equals(configFileName)) {
				api.deleteNamespacedConfigMap(item.getMetadata().getName(), "" + nc.getAppName(), new V1DeleteOptions(),
						null, null, null, null, null);
			}

		}

	}

	public static void deleteNameSpace(String name) throws Exception {

		ApiClient client = Config.defaultClient();
		Configuration.setDefaultApiClient(client);

		CoreV1Api api = new CoreV1Api();
		try {
			api.deleteNamespace("" + name, new V1DeleteOptions(), null, null, null, null, null);

		} catch (Exception e) {
			System.out.println("Error while delete namespace");
		}

	}

	/***********/

	public Status getPodStatus(String name) throws ApiException, IOException {

		V1PodList list = api.listNamespacedPod("" + nc.getAppName(), null, null, null, null, null, null, null, null,
				null);
		for (V1Pod item : list.getItems()) {
			if (item.getMetadata().getName().equals(name)) {
				try {
					if (item.getStatus().getContainerStatuses().get(0).getState().getRunning() != null) {
						return Status.running;
					} else
						return Status.installed;

				} catch (NullPointerException e) {
//				e.printStackTrace();
					return null;
				}
			}
		}
		return null;

	}

	// TODO to be changed
	public Status getNodeStatus() throws Exception {

		V1PodList list = api.listNamespacedPod("" + nc.getAppName(), null, null, null, null, null, null, null, null,
				null);
		for (Microservice ms : nc.getMicroServices()) {
			for (V1Pod item : list.getItems()) {
				if (item.getMetadata().getName().equals(ms.getIdMicroservice() + "-pod")) {
					try {
						if (item.getStatus().getContainerStatuses().get(0).getState().getRunning() == null) {
							System.out.println(ms.getIdMicroservice() + " not running");
							return Status.installed;
						}
					} catch (NullPointerException e) {
						// e.printStackTrace();
						return Status.installed;
					}

				}

			}
		}
		return Status.running;

	}

	// Create a Pod from a Yaml file
	public ResponseEntity<Void> deployFromFile(@PathVariable String path_name) throws IOException, ApiException {

		File file = new File(path_name);

		// connect to the API Server
		ApiClient client = Config.defaultClient();
		Configuration.setDefaultApiClient(client);

		V1Pod pod = (V1Pod) Yaml.load(file);
		System.out.println(pod.toString());

		CoreV1Api api = new CoreV1Api();
		api.createNamespacedPod("" + nc.getAppName(), pod, null, null, null);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{path_name}")
				.buildAndExpand(file.toString()).toUri();

		return ResponseEntity.created(location).build();

	}

	@RequestMapping(path = "/ms/addRunnable", method = RequestMethod.POST, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<String> addMicroserviceRunnable(@RequestBody Microservice mService,
			@RequestParam("file") MultipartFile uploadfile) {
		if (uploadfile.isEmpty()) {
			return new ResponseEntity<>("You must select a file!", HttpStatus.OK);
		}
		try {
			saveUploadedFiles(Arrays.asList(uploadfile));
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(),
				HttpStatus.OK);
	}

	// save file
	private void saveUploadedFiles(List<MultipartFile> files) throws IOException {
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue; // next pls
			}
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
		}
	}

	private Boolean jsonObjectIsValide(Microservice ms) {
		if (objectHasProperty(ms, "idMicroService") && objectHasProperty(ms, "status")
				&& objectHasProperty(ms, "maxInstances") && objectHasProperty(ms, "runnableName")
				&& objectHasProperty(ms, "type") && getAllFields(ms).size() == 5) {
			return true;
		}
		return false;
	}

	private Boolean objectHasProperty(Object obj, String propertyName) {
		List<Field> properties = getAllFields(obj);
		for (Field field : properties) {
			if (field.getName().equalsIgnoreCase(propertyName)) {
				return true;
			}
		}
		return false;
	}

	private static List<Field> getAllFields(Object obj) {
		List<Field> fields = new ArrayList<Field>();
		getAllFieldsRecursive(fields, obj.getClass());
		return fields;
	}

	private static List<Field> getAllFieldsRecursive(List<Field> fields, Class<?> type) {
		for (Field field : type.getDeclaredFields()) {
			fields.add(field);
		}
		if (type.getSuperclass() != null) {
			fields = getAllFieldsRecursive(fields, type.getSuperclass());
		}
		return fields;
	}

	public String getRepSocketURL() {
		return repSocketURL;
	}

	public void setRepSocketURL(String repSocketURL) {
		this.repSocketURL = repSocketURL;
	}

	public String getPubSocketURL() {
		return pubSocketURL;
	}

	public void setPubSocketURL(String pubSocketURL) {
		this.pubSocketURL = pubSocketURL;
	}

	public NodeComponent getNc() {
		return nc;
	}

	public void setNc(NodeComponent nc) {
		this.nc = nc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String sendMonitoringMsg(String des, String msg) {

	    String sourceOfResponse;
		String replyFromMS=null;
		while (replyFromMS == null) {
			MonitoringSocket.sendMore(des);
			MonitoringSocket.send(msg);
	        sourceOfResponse = MonitoringSocket.recvStr();
	        replyFromMS = MonitoringSocket.recvStr();
	        System.out.println("from ncem.... send monitoring msg to "+des+" get response "+replyFromMS);
			
		

	}
		return replyFromMS;
	}
	
	

	public String getRouterSocketURL() {
		return routerSocketURL;
	}

	public void setRouterSocketURL(String routerSocketURL) {
		this.routerSocketURL = routerSocketURL;
	}
}
