package Tasks;

public class UpdateMonitor extends Task {

	private String MetricName;
	private Rate rate;
	public String getMetricName() {
		return MetricName;
	}
	public void setMetricName(String MetricName) {
		this.MetricName = MetricName;
	}
	public Rate getRate() {
		return rate;
	}
	public void setRate(Rate rate) {
		this.rate = rate;
	}
	public void createRate(String type) {
	
		if(type.compareTo("stochastic")==0) {this.rate =new StochasticRate();}
		if(type.compareTo("periodic")==0) {this.rate=new PeriodicRate();}
		if(type.compareTo("time-serie")==0) setRate(new TimeSerieRate());
		
	}
}
