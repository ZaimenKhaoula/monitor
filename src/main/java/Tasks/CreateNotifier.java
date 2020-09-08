package Tasks;

import java.util.ArrayList;

public class CreateNotifier extends Task{
	
	private ArrayList<String> expression;
	private ArrayList<String> metrics; 
	private String url;

	public void setExpression(ArrayList<String> expression) {
		this.expression = expression;
	}
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

	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	public String expressionToString() {
		String s="";
		for(String st: expression ) {
			s=s+st;
		}
		return s;
	}
	@Override
	public String toString() {
		return id+" expression "+expressionToString()+" AlertUrl "+url;
		
	}
	
	
}
