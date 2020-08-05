package pfe.mw.models;

import java.util.HashMap;

public class ClassOperation {
	
	private String policy;
	private HashMap<String, String> delay;
	private HashMap<String, String> drop;
	private HashMap<String, String> schedule;
	
	

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public HashMap<String, String> getDelay() {
		return delay;
	}

	public void setDelay(HashMap<String, String> delay) {
		this.delay = delay;
	}

	public HashMap<String, String> getDrop() {
		return drop;
	}

	public void setDrop(HashMap<String, String> drop) {
		this.drop = drop;
	}

	public HashMap<String, String> getShedule() {
		return schedule;
	}

	public void setShedule(HashMap<String, String> priority) {
		this.schedule = priority;
	}

}

