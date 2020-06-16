package Tasks;

public class Counter  extends InternalMetric{
	
	private String MetricName;
    private String idMs;
	
	public String getMetricName() {
		return MetricName;
	}
	public void setMetricName(String metricName) {
		MetricName = metricName;
	}
	
	@Override 
	public String 	toString() {
	
		return 	"counter:"+MetricName+":"+getIdMs();
			
	}
	public String getIdMs() {
		return idMs;
	}
	public void setIdMs(String idMs) {
		String[] input=idMs.split("\\.");
		this.setMetricName(input[input.length-1]);
	    this.setAppName(input[0]);
        this.idMs =input[1];
	}
}
