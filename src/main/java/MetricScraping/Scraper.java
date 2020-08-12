package MetricScraping;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Monitoring.MonitoringManager;
import Monitoring.MonitoringMessage;
import Monitoring.OperationType;
import Tasks.AdminMetric;
import Tasks.Counter;
import Tasks.CreateMonitor;
import Tasks.InternalMetric;
import Tasks.RTT;
import Tasks.Task;
import pfe.mw.models.Application;
import pfe.mw.models.ApplicationRepository;
import pfe.mw.models.NCEM;


public abstract class Scraper{
	
	protected boolean enable=true;
	protected CreateMonitor task = new CreateMonitor();
	protected ApplicationRepository appRepository;
	protected InfluxDB influxDB;
	protected final ScheduledExecutorService runner;
	protected Runnable scrap;
	 
	 public Scraper(ScheduledExecutorService runner, Task t,InfluxDB influxDB, ApplicationRepository app) {
		 this.influxDB=influxDB;
		 this.influxDB.setDatabase("metricdb");
		 this.runner=runner;
		 this.appRepository=app;
	     setEnable(true);
	     this.task=(CreateMonitor)t;
	    scrap = new Runnable()
		    {	        
		        public void run()
		        {
		        	System.out.println("inside sraper... start scraping");
		        	getAllInternalMetricsValueOfTheAdminValue();
		        	System.out.println("out ofgetInternalMertics function..");
		        	evaluateExpression();
                    saveValue();
		        }   
		    };
	 }
	public void scrap(){
		
	}
	
	public void getAllInternalMetricsValueOfTheAdminValue()
	{
		System.out.println("inside the function getAllInternalMetricsValueOfTheAdminValue.. we will look for ncems");
		 for(InternalMetric m: task.getAdminmetric().getMetrics()) {
			 
      	   MonitoringMessage msg =new MonitoringMessage();
      	   String des="";
      	     msg.setOp(OperationType.GetValue);
      	     NCEM ncem;
      	    if(m instanceof Counter) {
   
      	    	msg.setMetricType("counter");
      	    	msg.setMetricName(((Counter) m).getMetricName());
      	    	System.out.println("the name of internal metric is.."+msg.getMetricName());
      	    	des=((Counter) m).getIdMs();
      	    	System.out.println("the destnation of monitoring msg to give to ncem is.."+des);
      	    	ncem =findNcemToContactMS(((Counter) m).getIdMs(),((Counter) m).getAppName());
      	    	System.out.println("inside scraper and ncem found");
      	    	}
      	    else {
      	    	System.out.println("the type of the metric is rtt");
      	    	msg.setMetricType("rtt");
                des=((RTT) m).getIDusSource();
                ncem =findNcemToContactMS(((RTT) m).getIDusSource(),((RTT) m).getAppName());
  	    	}
      	    
      	    if(ncem== null) System.out.println("ncem not found to send monitoring msg");
      	    else {
      	    	
      	    System.out.println("info about ncem founded.."+ncem.getPubSocketURL()+" url rep"+ncem.getRepSocketURL());
      	    System.out.println("des to give to ncem is"+des+"and msg is "+msg.toString());
      	    m.setValue(Float.parseFloat(ncem.sendMonitoringMsg(des,msg.toString())));}
      	    }
	}
	
	
	
	
	public NCEM findNcemToContactMS(String msIdentifier, String nameApp) {
		Application app=null;
		System.out.println("inside the fucntion findNcemToContactMS");
		System.out.println("the name of the app we are looking for is ..."+nameApp +"and ms is ..."+msIdentifier);
		if(appRepository.existsById(nameApp))
		{app= appRepository.findApplicationByName(nameApp);
		System.out.println("app found with info ...."+app.getName()+app.getStatus());}
	
	    int j=0;
	    int k;
	
	    	while(j<app.getNcems().size()) {
	    		System.out.println("inside pp.getNcems().size() loop size of getncems :"+app.getNcems().size());
	    	     k=0;
	    		 while(k<app.getNcems().get(j).getNc().getMicroServices().size()) { 
	    			 System.out.println("inside app.getNcems().get(j).getNc().getMicroServices().size() loop looking for ms id ..."+msIdentifier
	    					 
	    					 
	    			+"and the ms we are comparing to is .."+app.getNcems().get(j).getNc().getMicroServices().get(k).getIdMicroservice());
	    			 if(app.getNcems().get(j).getNc().getMicroServices().get(k).getIdMicroservice().compareTo(msIdentifier)==0)
	    		      return app.getNcems().get(j);
	    			 k++;
	    		 }
	    		
	    		j++; 
	    	}
	    	
		System.out.println("inside find ncem to conatct and null is sent");
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
				  .tag("MetricName", task.getAdminmetric().getMetricName())
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
	
	

	
	private String replaceMetricByValue(String s) {
		 System.out.println("inside replaceMetricByValue and expression is .."+s);
		int i;
	 if(s.contains("NbReqSent")||s.contains("NbRespSent")||s.contains("NBTopicSubscription")||s.contains("NbPublishedMsg")) 
	 {System.out.println("the metric Contains s..."+s);
		 String[] input= s.split("\\.");
		 i=0;
		 while( i<task.getAdminmetric().getMetrics().size()) {
			
			if(input[0].compareTo(((Counter)task.getAdminmetric().getMetrics().get(i)).getIdMs())==0 &&
           input[1].compareTo(((Counter)task.getAdminmetric().getMetrics().get(i)).getMetricName())==0) {
				System.out.println("++++++replaced "+s +" by "+String.valueOf(((Counter)task.getAdminmetric().getMetrics().get(i)).getValue()));
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
		
		System.out.println("expression after preparation"+result);
		
		return result; 
	}

public void evaluateExpression(){
	String prepareExp=prepareExpression();
	System.out.println("inside scaper ....evaluating expression.."+prepareExp);
	Expression e = new Expression(prepareExp);
	System.out.println("inside scaper ....evaluating expression"+e.calculate());
	task.getAdminmetric().setValue(e.calculate());
	for (AdminMetric m: MonitoringManager.metricsCurrentValues) {
		if(m.getMetricName().compareTo(task.getAdminmetric().getMetricName())==0) {
			m.setValue(task.getAdminmetric().getValue());
		}
	}
		

	
}



	
	
}
