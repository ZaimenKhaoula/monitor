package pfe.mw.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Microservices")
public class Microservice {
	@Id 
	private String idMicroservice; 
	private String type;
	private String runnableName;
	Status status = Status.uninstalled;  
	private int maxInstances;




	public Microservice(String idMicroservice, String type, String runnableName) {
		this.idMicroservice = idMicroservice;
		this.type = type;
		this.runnableName = runnableName;
	}

	public String getRunnableName() {
		return runnableName;
	}
	public void setRunnableName(String runnableName) {
		this.runnableName = runnableName;
	}
	public String getIdMicroservice() {
		return idMicroservice;
	}
	public void setIdMicroservice(String idMicroservice) {
		this.idMicroservice = idMicroservice;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getMaxInstances() {
		return maxInstances;
	}
	public void setMaxInstances(int maxInstances) {
		this.maxInstances = maxInstances;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}


	 
}
