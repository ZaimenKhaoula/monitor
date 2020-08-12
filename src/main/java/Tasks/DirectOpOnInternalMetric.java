package Tasks;

public class DirectOpOnInternalMetric extends Task {
	
	private String internalMetric;
	private String operation;
	
	public DirectOpOnInternalMetric (String name, String op) {
		this.internalMetric=name;
		this.operation=op;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getInternalMetric() {
		return internalMetric;
	}
	public void setInternalMetric(String internalMetric) {
		this.internalMetric = internalMetric;
	}
	
	
	
}
