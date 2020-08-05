package pfe.mw.models;


import java.util.Date;
import java.util.HashMap;

public class ShaperPolicy {
	private String id;
	private Long timestamp = new Date().getTime();
	private String target;
	private HashMap<String, ClassOperation> classMap;

	public ShaperPolicy(String id, Long timestamp, String target, HashMap<String, ClassOperation> classMap) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.target = target;
		this.classMap = classMap;
	}

	public HashMap<String, ClassOperation> getClassMap() {
		return classMap;
	}

	public void setClassMap(HashMap<String, ClassOperation> classMap) {
		this.classMap = classMap;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}

