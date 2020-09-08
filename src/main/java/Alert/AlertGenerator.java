package Alert;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.CloseableHttpResponse;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.mariuszgromada.math.mxparser.Expression;




import Tasks.*;

public class AlertGenerator implements PropertyChangeListener {
    
	 private Boolean enable=true;
	 private ArrayList<AdminMetric> metrics;
	 InfluxDB influxDB; 
	 Future<?> future;
	 final ScheduledExecutorService runner;
	 Runnable AlertSender;
	 AlertModel alert;

	CreateNotifier notifier= new CreateNotifier();
	 public AlertGenerator(CreateNotifier t, InfluxDB influxDB, ScheduledExecutorService runner ) {
		this.runner = runner;
		this.influxDB=influxDB;
	   	this.notifier=t;
		 metrics = new ArrayList<AdminMetric>();
		 this.influxDB.setDatabase("alertdb");
		 AlertSender = new Runnable()
		    {	        
		        public void run()
		        {
		        	sendAlert(alert.toString());
		        }         
		    };
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
			
	 alert= new AlertModel();	
	boolean found =false;
	int i=0;
	
	while (!found && i<metrics.size() ) {
		if((metrics.get(i)).getMetricName().compareTo(notifier.getMetrics().get(0))==0) {
			alert.setType(metrics.get(i).getType());
			alert.getMetrics().add(metrics.get(i));
			alert.setAlertExpression(notifier.expressionToString());
			alert.setAppName((metrics.get(i)).getMetrics().get(0).getAppName());
			found=true;}
		i++; 
	}
	
	           Point point = Point.measurement("alerts")
			  .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
			  .tag("id", notifier.getId()) 
			  .addField("value", alert.toString()) 
			  .build();
	           influxDB.write("alertdb", "autogen", point);
		}}
		
		future= runner.submit(AlertSender);
	
	}
		
	
	public void propertyChange(PropertyChangeEvent evt) {
		if(isEnable()) {
		for (AdminMetric m : metrics)
		{
			if (m.getMetricName().compareTo(evt.getPropertyName())==0)
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
		
		System.out.println("replacing values in alert expression..."+result);
		return result; 
	}
	
	
	
	private String replaceMetricByValue(String s) {
		boolean found=false;
		int i=0;
		while(!found && i<metrics.size()) {
			if(metrics.get(i).getMetricName().compareTo(s)==0) {
				found=true;
				System.out.println("the value of metric : "+s+" is "+Double.toString(metrics.get(i).getValue()));
				return Double.toString(metrics.get(i).getValue());
			}
			i++;
		}
		return null;
		
		
	}
	public void cancelNotifying() {
		this.enable=false;
		
	}

	
	public void sendAlert(String alert) {
		{

			System.out.println();
			System.out.println("*********************AlertGenerator******************");
			System.out.println("The observed alert expression : "+notifier.expressionToString()+ " is verified");
			System.out.println("Generating alert...");
			System.out.println("Send alert to the following url "+notifier.getUrl());
			System.out.println("******************************************************");
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
	        HttpPost post = new HttpPost(notifier.getUrl());

	      
	       
	        StringEntity params = null;
			try {
				params = new StringEntity(alert);
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
