package Tasks;

import java.util.ArrayList;

public class ReadMonitor extends Task{

	private boolean all =false;
	private String MetricName;
	private ArrayList<String> result= new ArrayList<String>();
	private boolean isInternalMetric=false;
	private String internalMetricUniqueIdentifier;
	public boolean isAll() {
		return all;
	}
	public void setAll(boolean all) {
		this.all = all;
	}
	public String getMetricName() {
		return MetricName;
	}
	public void setMetricName(String MetricName) {
		this.MetricName = MetricName;
	}
	public ArrayList<String> getResult() {
		return result;
	}
	public void setResult(ArrayList<String> result) {
		this.result = result;
	}
	
	
	@Override
	public String toString () {
		String r = "";
		for(String s: result) {
			r=r+", "+s;
		}
		
		return r;
	}
	public boolean isInternalMetric() {
		return isInternalMetric;
	}
	public void setInternalMetric(boolean isInternalMetric) {
		this.isInternalMetric = isInternalMetric;
	}
	public String getInternalMetricUniqueIdentifier() {
		return internalMetricUniqueIdentifier;
	}
	public void setInternalMetricUniqueIdentifier(String internalMetricUniqueIdentifier) {
		this.internalMetricUniqueIdentifier = internalMetricUniqueIdentifier;
	}
}
