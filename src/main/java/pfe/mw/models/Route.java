package pfe.mw.models;

public class Route {
	private String source;
	private String destination;
	private String nextHop;
	private String state;
	private String tags;

	public Route(String source, String destination, String nextHop, String state, String tags) {
		this.source = source;
		this.destination = destination;
		this.nextHop = nextHop;
		this.state = state;
		this.tags = tags;
	}

	public String getNextHop() {
		return nextHop;
	}

	public void setNextHop(String nextHop) {
		this.nextHop = nextHop;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}