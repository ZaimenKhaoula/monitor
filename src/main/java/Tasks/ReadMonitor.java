package Tasks;

import java.util.ArrayList;
import java.util.List;

public class ReadMonitor extends Task{

	private boolean all =false;
	private String MetricName;
	private List<AdminMetric> result= new ArrayList<AdminMetric>();
	private boolean isInternalMetric=false;
	private String internalMetricType;
	private String internalMetricValue;
	private String rttInternalMetric;
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
	public List<AdminMetric> getResult() {
		return result;
	}
	public void setResult(List<AdminMetric> result) {
		this.result = result;
	}
	
	
	@Override
	public String toString () {
		String r = "";
		for(AdminMetric s: result) {
			r=r+", "+s.toString();
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
	public String getInternalMetricType() {
		return internalMetricType;
	}
	public void setInternalMetricType(String internalMetricType) {
		this.internalMetricType = internalMetricType;
	}

	public String getInternalMetricValue() {
		return internalMetricValue;
	}
	public void setInternalMetricValue(String internalMetricValue) {
		this.internalMetricValue = internalMetricValue;
	}
	public String getRttInternalMetric() {
		return rttInternalMetric;
	}
	public void setRttInternalMetric(String rttInternalMetric) {
		this.rttInternalMetric = rttInternalMetric;
	}
}
