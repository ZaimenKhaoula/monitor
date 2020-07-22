package Alert;
import java.util.ArrayList;

import Tasks.*;


public class AlertModel {

private String type;
private String appName;
private String AlertExpression;
private ArrayList<AdminMetric> metrics= new ArrayList<AdminMetric>();
private String threshold;
public String getAlertExression() {
	return AlertExpression;
}
public void setAlertExpression(String alertExpression) {
	AlertExpression = alertExpression;
	threshold=AlertExpression.split(">")[AlertExpression.split(">").length-1];
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public ArrayList<AdminMetric> getMetrics() {
	return metrics;
}
public void setMetrics(ArrayList<AdminMetric> metrics) {
	this.metrics = metrics;
}


// save the alert as string in the knowledge base

public String alertInfo(){
	return "Alert+"+type+"+"+appName+"+"+AlertExpression+"+"+threshold;
}

@Override
public String toString() {
	 String metric ="";
	for(AdminMetric m: metrics) {
	metric=metric+m.toString()+"-";
	}

	return alertInfo()+"+"+metric;
	
}
public String getThreshold() {
	return threshold;
}
public void setThreshold(String threshold) {
	this.threshold = threshold;
}
public String getAppName() {
	return appName;
}
public void setAppName(String appName) {
	this.appName = appName;
}



}
