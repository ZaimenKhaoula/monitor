package pfe.mw.models;

import java.util.Map;

import org.springframework.data.annotation.Id;

public class MainRouter {
	@Id
	private String appName;
	private String imageName;
	private String routerSocketURL;
	private String subSocketURL, pubSocketURL;
	private Map<String, String> ms_node_map;

	public MainRouter(String appName, String imageName, String routerSocketURL, String subSocketURL,
			String pubSocketURL, Map<String, String> ms_node_map) {
		this.appName = appName;
		this.imageName = imageName;
		this.routerSocketURL = routerSocketURL;
		this.subSocketURL = subSocketURL;
		this.pubSocketURL = pubSocketURL;
		this.ms_node_map = ms_node_map;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getRouterSocketURL() {
		return routerSocketURL;
	}

	public void setRouterSocketURL(String routerSocketURL) {
		this.routerSocketURL = routerSocketURL;
	}

	public String getSubSocketURL() {
		return subSocketURL;
	}

	public void setSubSocketURL(String subSocketURL) {
		this.subSocketURL = subSocketURL;
	}

	public String getPubSocketURL() {
		return pubSocketURL;
	}

	public void setPubSocketURL(String pubSocketURL) {
		this.pubSocketURL = pubSocketURL;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Map<String, String> getMs_node_map() {
		return ms_node_map;
	}

	public void setMs_node_map(Map<String, String> ms_node_map) {
		this.ms_node_map = ms_node_map;
	}
}
