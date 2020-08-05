package pfe.mw.models;

import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
import pfe.mw.controllers.NCEM;

@Document(collection = "applications")
public class Application {

	@Id
	private String name;
	@ApiModelProperty(hidden = true)
	private Status status = Status.uninstalled;

	private List<Node> nodes;

	@ApiModelProperty(hidden = true)
	private List<NodeComponent> nodeComponents = new ArrayList<NodeComponent>();

	@ApiModelProperty(hidden = true)
	private List<NCEM> ncems = new ArrayList<NCEM>();

	public List<NCEM> getNcems() {
		return ncems;
	}

	public void setNcems(List<NCEM> ncems) {
		this.ncems = ncems;
	}

	public List<NodeComponent> getNodeComponents() {
		return nodeComponents;
	}

	public void setNodeComponents(List<NodeComponent> nodeComponents) {
		this.nodeComponents = nodeComponents;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public MainRouter getMainRouter() {
		int i = 0;
		MainRouter mainRouter = null;
		while (i < ncems.size()) {
			if (ncems.get(i).getNc().getMainRouter() != null)
				mainRouter = ncems.get(i).getNc().getMainRouter();
			i++;
		}
		return mainRouter;
	}

	public NodeComponent getMainRouterNC() {
		int i = 0;
		NodeComponent nc = null;

		while (i < ncems.size()) {
			if (ncems.get(i).getNc().getMainRouter() != null)
				nc = ncems.get(i).getNc();
			i++;
		}
		return nc;
	}

	/*public NodeComponent getAdapterNC(AdapterType adapter) {
		int i = 0;
		NodeComponent nc = null;
		boolean found = false;
		while (i < ncems.size() && !found) {
			found = ncems.get(i).getNc().hasAdapter(adapter);
			if (found)
				nc = ncems.get(i).getNc();
			i++;
		}
		return nc;

	}*/

	public void setMainRouter(MainRouter mainRouter) {
		if (getMainRouter() == null)
			getNcems().get(0).getNc().setMainRouter(mainRouter);
	}
}
