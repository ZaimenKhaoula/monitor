package pfe.mw.models;


import java.util.ArrayList;

public class Route {
	private String source;
	private String destination;
	private String nextHop;
	private String state;
	private String redirections;

	public Route(String source, String destination, String nextHop, String state,String redirections) {
		this.source = source;
		this.destination = destination;
		this.nextHop = nextHop;
		this.state = state;
		this.redirections = redirections;
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

	public String getRedirections() {
		return redirections;
	}

	public void setRedirections(String redirections) {
		this.redirections = redirections;
	}

}
