package Alert;
import java.util.ArrayList;

import Tasks.*;


public class AlertModel {

private String type;
private String AlertExression;
private ArrayList<AdminMetric> metrics= new ArrayList<AdminMetric>();

public String getAlertExression() {
	return AlertExression;
}
public void setAlertExression(String alertExression) {
	AlertExression = alertExression;
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
@Override
public String toString() {
	 String metric ="";
	for(AdminMetric m: metrics) {
	metric=metric+m.toString()+"-";
	}

	return "Alert-"+type+"-"+AlertExression+"-"+metric;
	
}


}
