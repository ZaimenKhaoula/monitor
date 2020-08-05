package MetricScraping;



import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.beans.factory.annotation.Autowired;

import Monitoring.MonitoringMessage;
import Monitoring.OperationType;
import Tasks.Counter;
import Tasks.CreateMonitor;
import Tasks.InternalMetric;
import Tasks.RTT;
import Tasks.Task;
import pfe.mw.models.Application;
import pfe.mw.models.ApplicationRepository;
import pfe.mw.models.NCEM;

public abstract class Scraper{
	

	protected boolean enable=false;
	protected CreateMonitor task = new CreateMonitor();
	 @Autowired
	protected ApplicationRepository appRepository;
	protected InfluxDB influxDB;
	protected final ScheduledExecutorService runner;
	protected Runnable scrap;
	 
	 public Scraper(ScheduledExecutorService runner, Task t,InfluxDB influxDB) {
		 this.influxDB=influxDB;
		 this.influxDB.setDatabase("metricdb");
		 this.runner=runner;
	     setEnable(true);
	     this.task=(CreateMonitor)t;
	     scrap = new Runnable()
		    {	        
		        public void run()
		        {
		        	getAllInternalMetricsValueOfTheAdminValue();
		        	evaluateExpression();
                    saveValue();
		        }         
		    };
	 }
	public void scrap(){
		
	}
	
	public void getAllInternalMetricsValueOfTheAdminValue()
	{
		 for(InternalMetric m: task.getAdminmetric().getMetrics()) {
      	   MonitoringMessage msg =new MonitoringMessage();
      	   String des="";
      	     msg.setOp(OperationType.GetValue);
      	     NCEM ncem;
      	    if(m instanceof Counter) {
      	    	msg.setMetricType("counter");
      	    	msg.setMetricName(((Counter) m).getMetricName());
      	    	des=((Counter) m).getIdMs();
      	    	ncem =findNcemToContactMS(((Counter) m).getIdMs(),((Counter) m).getAppName());
      	    	}
      	    else {
      	    	msg.setMetricType("rtt");
         /* if(getUrlMsByMsName(m.getAppName(),((RTT)m).getIDusDestination())!=null) {
                  	 msg.setUrlDesMs(getUrlMsByMsName(m.getAppName(),((RTT)m).getIDusDestination()));
                  
                   }*/
                   des=((RTT) m).getIDusSource();
                   ncem =findNcemToContactMS(((RTT) m).getIDusSource(),((RTT) m).getAppName());
  	    	}
      	    
      	    if(ncem== null) System.out.println("ncem not found to send monitoring msg");
      	    else
      	    m.setValue(Float.parseFloat(ncem.sendMonitoringMsg(des,msg.toString())));
      	    }
	}
	
	
	
	
	public NCEM findNcemToContactMS(String msIdentifier, String nameApp) {
		Application app= appRepository.findApplicationByName(nameApp);
	
	    int j=0;
	    int k;
	
	    	while(j<app.getNcems().size()) {
	    	     k=0;
	    		 while(k<app.getNcems().get(j).getNc().getMicroServices().size()) {
	    			 if(app.getNcems().get(j).getNc().getMicroServices().get(k).getIdMicroservice().compareTo(msIdentifier)==0)
	    		      return app.getNcems().get(j);
	    			 k++;
	    		 }
	    		
	    		j++; 
	    	}
	    	
		
		 return null;
	}
	
	
	
	

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public void saveValue() {
		
		Point point = Point.measurement("metrics")
				  .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
				  .tag("name", task.getAdminmetric().getMetricName())
				  .addField("type", task.getAdminmetric().getType())
				  .addField("value", task.getAdminmetric().getValue()) 
				  .build();
		influxDB.write("metricdb", "autogen", point);
		
		
	}
	
	
	public void cancelScraping() {}


	public ApplicationRepository getAppRepository() {
		return appRepository;
	}


	public void setAppRepository(ApplicationRepository appRepository) {
		this.appRepository = appRepository;
	}
	
	
/*	public String getUrlMsByMsName(String appName,String msID) {
		Application app= appRepository.findApplicationByName(appName);
       int i=0;
      
       while(i<app.getNcems().size()) {
    	   
    	 if( app.getNcems().get(i).getMsIdAndURL().containsKey(msID)) {
    		  return app.getNcems().get(i).getMsIdAndURL().get(msID);
    	   }
    	 i++;
       }
		return null;
	}*/

	
	private String replaceMetricByValue(String s) {
	
		int i;
	 if(s.contains("NbReqSent")||s.contains("NbRespSent")||s.contains("NBTopicSubscription")||s.contains("NbPublishedMsg")) 
	 {
		 String[] input= s.split("\\.");
		 i=0;
		 while( i<task.getAdminmetric().getMetrics().size()) {
			
			if(input[0].compareTo(((Counter)task.getAdminmetric().getMetrics().get(i)).getIdMs())==0 &&
           input[1].compareTo(((Counter)task.getAdminmetric().getMetrics().get(i)).getMetricName())==0) {
				return String.valueOf(((Counter)task.getAdminmetric().getMetrics().get(i)).getValue());}
		i++;	
		 }
		 
	 }
	 else {

		 String[] input= s.split("-");
		 i=0;
		 while( i<task.getAdminmetric().getMetrics().size()) {
				
				if(input[1].compareTo(((RTT)task.getAdminmetric().getMetrics().get(i)).getIDusSource())==0 &&
	           input[2].compareTo(((RTT)task.getAdminmetric().getMetrics().get(i)).getIDusDestination())==0) {
					return String.valueOf(((RTT)task.getAdminmetric().getMetrics().get(i)).getValue());}
			i++;	
			 }
		 
	 }
		 
		
		return null;
		
		
	}
	
	
	
	
	
	
	
public String prepareExpression() {
		
		String result="";
		
		for(String s: task.getExpression()) {
			if(s.contains("RTT")|| s.contains("NbReqSent")||s.contains("NbRespSent")||s.contains("NBTopicSubscription")||s.contains("NbPublishedMsg"))
			 {
				result=result+replaceMetricByValue(s);
			 }
			
			else {
				result=result+s;
			}
		}
		
		System.out.println(result);
		return result; 
	}

public double evaluateExpression(){
	
	Expression e = new Expression(prepareExpression());
	task.getAdminmetric().setValue(e.calculate());
	return e.calculate();
	
}
	
	
}
