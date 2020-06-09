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
	   /* String s=""; for(int i=1; i < (input.length-2);i++) s=s+input[i]+".";
	    s=s+input[(input.length-2)];*/
        this.idMs =input[1];
	}
}
