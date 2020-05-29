package monitoringApi;

public class MonitorModel {
	private String MetricName;
    private String expression;
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}

	private RateModel rythmeCollecte;
	public String getMetricName() {
		return MetricName;
	}
	public void setMetricName(String monitorId) {
		this.MetricName = monitorId;
	}
	
	public RateModel getRythmeCollecte() {
		return rythmeCollecte;
	}
	public void setRythmeCollecte(RateModel rythmeCollecte) {
		this.rythmeCollecte = rythmeCollecte;
	}
	
	@Override
	public String toString() {
		System.out.println("CreateMonitor id "+MetricName+" = "+expression+" "+rythmeCollecte.toString());
		return "CreateMonitor id "+MetricName+" = "+expression+" "+rythmeCollecte.toString();
	}
}
