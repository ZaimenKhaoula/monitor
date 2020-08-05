package pfe.mw.models;


import java.util.ArrayList;
import java.util.Date;

public class ClassifierPolicy {
	private String id;
	private Long timestamp = new Date().getTime();
	private String target;
	private ArrayList<ClassCriteria> criteriaMap;

	public ClassifierPolicy(String id, String target, ArrayList<ClassCriteria> criteriaMap) {
		this.id = id;
		this.target = target;
		this.criteriaMap = criteriaMap;
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

	public ArrayList<ClassCriteria> getCriteriaMap() {
		return criteriaMap;
	}

	public void setCriteriaMap(ArrayList<ClassCriteria> criteriaMap) {
		this.criteriaMap = criteriaMap;
	}
}

