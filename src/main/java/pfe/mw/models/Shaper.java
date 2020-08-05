package pfe.mw.models;

public class Shaper {
	private String id;
	private ShaperPolicy policy;
	
	public Shaper(String id) {
		super();
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ShaperPolicy getPolicy() {
		return policy;
	}
	public void setPolicy(ShaperPolicy policy) {
		this.policy = policy;
	}
	
}
