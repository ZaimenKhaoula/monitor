package pfe.mw.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Links")
public class Link {

	private String ep1;
	private String ep2;
	private String type; 
	
	public String getEp1() {
		return ep1;
	}
	public void setEp1(String ep1) {
		this.ep1 = ep1;
	}
	public String getEp2() {
		return ep2;
	}
	public void setEp2(String ep2) {
		this.ep2 = ep2;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
