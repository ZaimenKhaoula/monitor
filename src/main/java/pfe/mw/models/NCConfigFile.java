package pfe.mw.models;

import java.util.HashMap;
import java.util.Map;

public class NCConfigFile {
		private String configFileName;
		private Map<String, String> data = new HashMap<>();
	    private String repSocketURL;
	    private String pubSocketURL;


		public NCConfigFile(String configFileName, String repSocketURL, String pubSocketURL) {
	        
	    	this.configFileName=configFileName;
	    	this.repSocketURL = repSocketURL;
	    	this.pubSocketURL = pubSocketURL;
	    	data.put("address_rep","address.rep= "+repSocketURL);
	        data.put("address_pub","address.pub= "+pubSocketURL);
	        /*
	        data.put("routerUniqueIdentifier","routerUniqueIdentifier= "+routerUniqueIdentifier);
	        data.put("clientServerRouterURL","clientServerRouterURL= "+clientServerRouterURL);
	        data.put("confPubSubRouterFrontendURL","confPubSubRouterFrontendURL= "+confPubSubRouterFrontendURL);
	        data.put("confPubSubRouterBackendURL","confPubSubRouterBackendURL= "+confPubSubRouterBackendURL);
	        data.put("pubSubRouterFrontendURL","pubSubRouterFrontendURL= "+pubSubRouterFrontendURL);
	        data.put("pubSubRouterBackendURL","pubSubRouterBackendURL= "+pubSubRouterBackendURL);
	        data.put("ROUTER_IP_ADDRESS","ROUTER_IP_ADDRESS= "+ROUTER_IP_ADDRESS);
	        
	        
	        data.put("mainRouterSocketURL","mainRouterSocketURL= "+mainRouterSocketURL);
	        data.put("mainSubSocketURL","mainSubSocketURL= "+mainSubSocketURL);
	        data.put("mainPubSocketURL","mainPubSocketURL= "+mainPubSocketURL);
			*/
	    }

		public String getConfigFileName() {
			return configFileName;
		}

		public void setConfigFileName(String configFileName) {
			this.configFileName = configFileName;
		}		
		
		public Map<String, String> getData() {
			return data;
		}

		public void setData(Map<String, String> data) {
			this.data = data;
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



}
