package pfe.mw.models;

public class Classifier {
	private String id;
	private String IP_ADDRESS ;
	private ClassifierPolicy policy;
	
	
	public Classifier(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIP_ADDRESS() {
		return IP_ADDRESS;
	}
	public void setIP_ADDRESS(String iP_ADDRESS) {
		IP_ADDRESS = iP_ADDRESS;
	}
	public ClassifierPolicy getPolicy() {
		return policy;
	}
	public void setPolicy(ClassifierPolicy policy) {
		this.policy = policy;
	}
}
