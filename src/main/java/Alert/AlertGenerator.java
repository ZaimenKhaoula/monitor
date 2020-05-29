package Alert;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;


import org.apache.http.client.methods.CloseableHttpResponse;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.mariuszgromada.math.mxparser.Expression;




import Tasks.*;

public class AlertGenerator implements PropertyChangeListener {
    
	 private Boolean enable=true;
	 private ArrayList<AdminMetric> metrics;
	 



	CreateNotifier notifier= new CreateNotifier();
	 public AlertGenerator(CreateNotifier t) {
		 
		this.notifier=t;
		 metrics = new ArrayList<AdminMetric>();
		
	 }
	 public ArrayList<AdminMetric> getMetrics() {
			return metrics;
		}

		public void setMetrics(ArrayList<AdminMetric> metrics) {
			this.metrics = metrics;
		}
	 
	 public Boolean isEnable() {
			return enable;
		}
		
		public void setEnable(Boolean enable) {
			this.enable = enable;
		}
	 
	 
	public boolean evaluateExpression(){
		
		Expression e = new Expression(prepareExpression());
		if(e.calculate()==1.0) return true;
		else return false;
		
	}
	
	
	
	public void generateAlert() {
		if(isEnable()) {
		if(evaluateExpression()) {
			
	AlertModel a= new AlertModel();	
	boolean found =false;
	int i=0;
	while (!found && i<metrics.size() ) {
		if((metrics.get(i)).getMetricName().compareTo(notifier.getMetrics().get(0))==0) {
			a.setType(metrics.get(i).getType());
			a.getMetrics().add(metrics.get(i));
			found=true;}
		i++; 
	}
	
	System.out.println("alerte generee");}}
	}
	
	

	
	
	
	public void propertyChange(PropertyChangeEvent evt) {
		if(isEnable()) {
		for (AdminMetric m : metrics)
		{
			if (m.getMetricName()==evt.getPropertyName())
				 m.setValue((Double)evt.getNewValue());
		}
		
		generateAlert();}
	}
	
	
	
	
	public String prepareExpression() {
		
		String result="";
		for(String s: notifier.getExpression()) {
			if(notifier.getMetrics().contains(s)) {
				result=result+replaceMetricByValue(s);
			}
			
			else {
				result=result+s;
			}
		}
		
		System.out.println(result);
		return result; 
	}
	
	
	
	private String replaceMetricByValue(String s) {
		boolean found=false;
		int i=0;
		while(!found && i<metrics.size()) {
			if(metrics.get(i).getMetricName().compareTo(s)==0) {
				found=true;
				System.out.println(s+"   "+Double.toString(metrics.get(i).getValue()));
				return Double.toString(metrics.get(i).getValue());
			}
			i++;
		}
		return null;
		
		
	}
	public void cancelNotifying() {
		this.enable=false;
		
	}
	public void start() {
	   evaluateExpression();		
	}
	
	public void sendAlert() {
		{

	        HttpPost post = new HttpPost(notifier.getUrl());

	      
	        JSONObject json = new JSONObject();
	        StringEntity params = null;
			try {
				params = new StringEntity(json.toString());
			} catch (UnsupportedEncodingException e1) {
				
				e1.printStackTrace();
			}
	        post.addHeader("content-type", "application/json");
	        post.setEntity(params);

	        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	             CloseableHttpResponse response = httpClient.execute(post)){

	           System.out.println( EntityUtils.toString(response.getEntity()));
	        } catch (IOException e) {
				
				e.printStackTrace();
			}

	    }
	}
	

}
