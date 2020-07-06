package pfe.mw.models;

import org.springframework.data.annotation.Id;


public class Router {
	@Id
	private String routerUniqueIdentifier;

	private String imageName;

	// MainRouter properties
	private String mainRouterSocketURL, mainSubSocketURL, mainPubSocketURL;

	// client server router properties
	private String clientServerRouterURL;

	// publisher subscriber router properties
	private String pubSubRouterFrontendURL, pubSubRouterBackendURL;

	// configuration router properties
	private String confPubSubRouterFrontendURL, confPubSubRouterBackendURL;

	private RoutingTable routingTable;

	public Router(String routerUniqueIdentifier, String imageName, String clientServerRouterURL,
			String pubSubRouterFrontendURL, String pubSubRouterBackendURL, String confPubSubRouterFrontendURL,
			String confPubSubRouterBackendURL, RoutingTable routingTable) {

		this.routerUniqueIdentifier = routerUniqueIdentifier;
		this.imageName = imageName;
		this.clientServerRouterURL = clientServerRouterURL;
		this.pubSubRouterFrontendURL = pubSubRouterFrontendURL;
		this.pubSubRouterBackendURL = pubSubRouterBackendURL;
		this.confPubSubRouterFrontendURL = confPubSubRouterFrontendURL;
		this.confPubSubRouterBackendURL = confPubSubRouterBackendURL;
		this.setRoutingTable(routingTable);
	}

	public String getMainSubSocketURL() {
		return mainSubSocketURL;
	}

	public void setMainSubSocketURL(String mainSubSocketURL) {
		this.mainSubSocketURL = mainSubSocketURL;
	}

	public String getMainPubSocketURL() {
		return mainPubSocketURL;
	}

	public void setMainPubSocketURL(String mainPubSocketURL) {
		this.mainPubSocketURL = mainPubSocketURL;
	}

	public String getMainRouterSocketURL() {
		return mainRouterSocketURL;
	}

	public void setMainRouterSocketURL(String mainRouterSocketURL) {
		this.mainRouterSocketURL = mainRouterSocketURL;
	}

	public String getClientServerRouterURL() {
		return clientServerRouterURL;
	}

	public void setClientServerRouterURL(String clientServerRouterURL) {
		this.clientServerRouterURL = clientServerRouterURL;
	}

	public String getPubSubRouterBackendURL() {
		return pubSubRouterBackendURL;
	}

	public void setPubSubRouterBackendURL(String pubSubRouterBackendURL) {
		this.pubSubRouterBackendURL = pubSubRouterBackendURL;
	}

	public String getPubSubRouterFrontendURL() {
		return pubSubRouterFrontendURL;
	}

	public void setPubSubRouterFrontendURL(String pubSubRouterFrontendURL) {
		this.pubSubRouterFrontendURL = pubSubRouterFrontendURL;
	}

	public String getConfPubSubRouterBackendURL() {
		return confPubSubRouterBackendURL;
	}

	public void setConfPubSubRouterBackendURL(String confPubSubRouterBackendURL) {
		this.confPubSubRouterBackendURL = confPubSubRouterBackendURL;
	}

	public String getConfPubSubRouterFrontendURL() {
		return confPubSubRouterFrontendURL;
	}

	public void setConfPubSubRouterFrontendURL(String confPubSubRouterFrontendURL) {
		this.confPubSubRouterFrontendURL = confPubSubRouterFrontendURL;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getRouterUniqueIdentifier() {
		return routerUniqueIdentifier;
	}

	public void setRouterUniqueIdentifier(String routerUniqueIdentifier) {
		this.routerUniqueIdentifier = routerUniqueIdentifier;
	}

	public RoutingTable getRoutingTable() {
		return routingTable;
	}

	public void setRoutingTable(RoutingTable routingTable) {
		this.routingTable = routingTable;
	}
}
