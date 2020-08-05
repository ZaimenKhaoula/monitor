package pfe.mw.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.data.annotation.Id;

import io.swagger.annotations.ApiModelProperty;

public class NodeComponent {
	@Id
	private String id;
	private String hostName;
	private String appName;
	private Router router;
	private List<Microservice> microServices = new ArrayList<>();
	private MainRouter mainRouter;
	private Classifier classifier;
	private Shaper shaper;
	public Shaper getShaper() {
		return shaper;
	}

	public void setShaper(Shaper shaper) {
		this.shaper = shaper;
	}

	private NCConfigFile ncConfigFile;


	public NodeComponent(String id, String hostName, String appName) {
		super();
		this.id = id;
		this.hostName = hostName;
		this.appName = appName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public List<Microservice> getMicroServices() {
		return microServices;
	}

	public void setMicroServices(List<Microservice> microServices) {
		this.microServices = microServices;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Router getRouter() {
		return router;
	}

	public void setRouter(Router router) {
		this.router = router;
	}

	public void setNcConfigFile(NCConfigFile ncConfigFile) {
		this.ncConfigFile = ncConfigFile;
	}

	public NCConfigFile getNcConfigFile() {
		return ncConfigFile;
	}

	public String getAppName() {
		return appName;
	}

	public MainRouter getMainRouter() {
		return mainRouter;
	}

	public void setMainRouter(MainRouter mainRouter) {
		this.mainRouter = mainRouter;
	}
	public Classifier getClassifier() {
		return classifier;
	}

	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}
