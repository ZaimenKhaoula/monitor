package pfe.mw.models;


import java.util.Date;
import java.util.LinkedList;

public class RoutingTable {
	private Long timestamp = new Date().getTime();
	private String target;
	private LinkedList<Route> routes = new LinkedList<Route>();

	public RoutingTable(String target) {
		this.target = target;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public LinkedList<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(LinkedList<Route> routes) {
		this.routes = routes;
	}

	public void addRoute(Route route) {
		routes.add(route);
	}

	public Route removeRoute(String source, String destination) {
		Boolean found = false;
		int i = 0;
		while (!found && i < routes.size()) {
			if (routes.get(i).getSource() == source && routes.get(i).getDestination() == destination)
				return routes.remove(i);
			i++;
		}
		return null;
	}

	public String getNextHop(String source, String destination) {
		String nextHop = null;
		Route route = null;
		int i = 0;
		while (nextHop == null && i < routes.size()) {
			route = routes.get(i);
			if (route.getSource() == source && route.getDestination() == destination)
				nextHop = route.getNextHop();
			i++;
		}
		return nextHop;
	}

}


