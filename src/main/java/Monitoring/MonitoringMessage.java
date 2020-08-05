package Monitoring;

public class MonitoringMessage {
 
	private OperationType op;
	private String metricName;
	private String metricType;
	private String urlDesMs="";

@Override
public String toString() {

	return metricName+"-"+metricType+"-"+op+"-"+urlDesMs;
}



public OperationType getOp() {
	return op;
}


public void setOp(OperationType op) {
	this.op = op;
}


public String getMetricName() {
	return metricName;
}


public void setMetricName(String metricName) {
	this.metricName = metricName;
}


public String getMetricType() {
	return metricType;
}


public void setMetricType(String metricType) {
	this.metricType = metricType;
}


public String getUrlDesMs() {
	return urlDesMs;
}


public void setUrlDesMs(String urlDesMs) {
	this.urlDesMs = urlDesMs;
}
	
}
