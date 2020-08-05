package pfe.mw.models;


public class ClassCriteria {
	private String id;
	private Criterias criterias;
	private String cos;

	public Criterias getCriterias() {
		return criterias;
	}

	public ClassCriteria(String id, Criterias criterias, String cos) {
		this.id = id;
		this.criterias = criterias;
		this.cos = cos;
	}

	public void setCriterias(Criterias criterias) {
		this.criterias = criterias;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCos() {
		return cos;
	}

	public void setCos(String cos) {
		this.cos = cos;
	}

}

