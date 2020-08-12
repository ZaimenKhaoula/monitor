package Tasks;

import java.util.ArrayList;
import java.util.List;

public class ReadMonitor extends Task{

	private boolean all =false;
	private String MetricName;
	private List<influxdbMappingClass> result= new ArrayList<influxdbMappingClass>();
	private boolean isInternalMetric=false;
	private String internalMetricType;
	private String oneMetricResult;
	private String rttInternalMetric;
	private String internalMetricUniqueIdentifier;
	private int limit;
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
	public List<influxdbMappingClass> getResult() {
		return result;
	}
	public void setResult(List<influxdbMappingClass> result) {
		this.result = result;
	}
	
	
	@Override
	public String toString () {
		String r = "";
		for(influxdbMappingClass s: result) {
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

	public String getRttInternalMetric() {
		return rttInternalMetric;
	}
	public void setRttInternalMetric(String rttInternalMetric) {
		this.rttInternalMetric = rttInternalMetric;
	}
	public String getOneMetricResult() {
		return oneMetricResult;
	}
	public void setOneMetricResult(String oneMetricResult) {
		this.oneMetricResult = oneMetricResult;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
}
