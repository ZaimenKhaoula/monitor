package Tasks;

import java.util.ArrayList;

public class CreateNotifier extends Task{
	
	private ArrayList<String> expression;
	private ArrayList<String> metrics; 
	private String NotifierId;
	private String url;
	public CreateNotifier() {
	this.expression =new ArrayList<String>(); 
	this.metrics=new ArrayList<String>();
	}
	public ArrayList<String> getExpression() {
		return expression;
	}
	public void setExression(ArrayList<String> expression) {
		this.expression = expression;
	}
	public ArrayList<String> getMetrics() {
		return metrics;
	}
	public void setMetrics(ArrayList<String> metrics) {
		this.metrics = metrics;
	}
	public String getNotifierId() {
		return NotifierId;
	}
	public void setNotifierId(String NotifierId) {
		this.NotifierId = NotifierId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return NotifierId+" expression :"+expression+" AlertUrl :"+url;
		
	}
	
	
}
