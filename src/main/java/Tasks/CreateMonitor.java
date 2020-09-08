package Tasks;

import java.util.ArrayList;

public class CreateMonitor extends Task{

	 private ArrayList<String> expression;
	private AdminMetric adminmetric;
	
	public void setAdminmetric(AdminMetric adminmetric) {
		this.adminmetric = adminmetric;
	}
	private Rate rate;
	
	public CreateMonitor() {
		adminmetric = new AdminMetric();
		setExpression(new ArrayList<String>());
	}
	public ArrayList<String> getExpression() {
		return expression;
	}
	public void setExpression(ArrayList<String> expression) {
		this.expression = expression;
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
	
	@Override
	public String toString() {return adminmetric.getMetricName()+expressionToString();}
	public AdminMetric getAdminmetric() {
		return adminmetric;
	}
	
	public String expressionToString() {
	 String a="";
	 for (String m : expression) {
	  a=a+m;	 
	 }
	 return a;
	}
}
