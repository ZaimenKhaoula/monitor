package pfe.mw.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Nodes")
public class Node {
	@Id 
	private String idNode;
	private String name;				
	private List<Microservice> microServices;
	private List<Link> links; 
	private List<String> dependencies;
	private List<String> exposedParameters; 
	
	
	public String getIdNode() {
		return idNode;
	}
	public void setIdNode(String idNode) {
		this.idNode = idNode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Microservice> getMicroServices() {
		return microServices;
	}
	public void setMicroServices(List<Microservice> microServices) {
		this.microServices = microServices;
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public List<String> getDependencies() {
		return dependencies;
	}
	public void setDependencies(List<String> dependencies) {
		this.dependencies = dependencies;
	}
	public List<String> getExposedParameters() {
		return exposedParameters;
	}
	public void setExposedParameters(List<String> exposedParameters) {
		this.exposedParameters = exposedParameters;
	}
	
}
