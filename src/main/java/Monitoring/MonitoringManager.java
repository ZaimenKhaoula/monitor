package Monitoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Alert.AlertGenerator;
import MetricScraping.*;
import Tasks.*;
import pfe.mw.models.*;
import Alert.influxdbAlertMapping;

@Service
public class MonitoringManager  {

	Map<String, Scraper> MonitoredMetrics;
    public static ArrayList<AdminMetric> metricsCurrentValues= new ArrayList<AdminMetric>() ;
	Map<String,AlertGenerator> currentAlertGenerators; 
	private ArrayList<Task> currentTasks;
	ScheduledExecutorService threadPoolforScraping;
	ScheduledExecutorService threadPoolToSendAlerts;
    @Autowired
	ApplicationRepository appRepository;
	InfluxDB influxDB;
	String urlBD="http://localhost:8086";
	public MonitoringManager() {
		 influxDB = InfluxDBFactory.connect(urlBD);
		 influxDB.query(new Query("CREATE DATABASE alertdb",""));
		 influxDB.query(new Query("CREATE DATABASE metricdb",""));
	      
		this.threadPoolforScraping = Executors.newScheduledThreadPool(5);
		this.threadPoolToSendAlerts = Executors.newScheduledThreadPool(5);
		currentAlertGenerators =new HashMap<String,AlertGenerator>();
		currentTasks= new ArrayList<Task>();
		MonitoredMetrics =new HashMap<String,Scraper>();
		System.out.println("monitoringmanager created");
	}

	
	public void taskProccessing(Task t){
	     
		
		/**start scraping metrics**/
		if(t instanceof CreateMonitor) {
			currentTasks.add(t);
			((CreateMonitor) t).getAdminmetric().setExpression(((CreateMonitor) t).expressionToString());
        System.out.println("++++Admin metric to scrap.."+((CreateMonitor) t).getAdminmetric().toString());
    ((CreateMonitor) t).getAdminmetric().setExpression(((CreateMonitor) t).expressionToString());
			 metricsCurrentValues.add(((CreateMonitor) t).getAdminmetric());
			
			
			if(((CreateMonitor) t).getRate() instanceof PeriodicRate ) {
				PeriodicScraper p = new PeriodicScraper(threadPoolforScraping,t,influxDB,appRepository);
				MonitoredMetrics.put(((CreateMonitor) t).getAdminmetric().getMetricName(),p);
				
				p.scrap();
			}
			if(((CreateMonitor) t).getRate() instanceof StochasticRate ) {
				
				StochasticScraper p = new StochasticScraper(threadPoolforScraping, t,influxDB,appRepository);
				MonitoredMetrics.put(((CreateMonitor) t).getAdminmetric().getMetricName(),p);
				
				p.scrap();
			
			}
			if(((CreateMonitor) t).getRate() instanceof TimeSerieRate ) {
				TimeSerieScraper p = new TimeSerieScraper(threadPoolforScraping,t,influxDB,appRepository);
				MonitoredMetrics.put(((CreateMonitor) t).getAdminmetric().getMetricName(),p);
				p.scrap();
			}
			t.setFinished(true);
			}
		
		/**stop monitoring task**/
        if(t instanceof DeleteMonitor) {
        		if (MonitoredMetrics.containsKey(((DeleteMonitor) t).getId()) ) {(MonitoredMetrics.get(((DeleteMonitor) t).getId())).cancelScraping();
        		 currentTasks.remove((DeleteMonitor) t);
        		MonitoredMetrics.remove(((DeleteMonitor) t).getId());
   QueryResult queryResult = influxDB.query(new Query("DELETE FROM metrics WHERE MetricName = '"+((DeleteMonitor)t).getId()+"'", "metricdb"));
     			}
       t.setFinished(true);
		}
     /**set metric rate**/
        if(t instanceof UpdateMonitor) {
        	if (MonitoredMetrics.containsKey(((UpdateMonitor) t).getMetricName()) ) {
        		
        		if(findMonitorById(((UpdateMonitor) t).getMetricName())!= null)
        		{
        		CreateMonitor task =new CreateMonitor();
        		task.setAdminmetric(findMonitorById(((UpdateMonitor) t).getMetricName()).getAdminmetric());
        		task.setExpression(findMonitorById(((UpdateMonitor) t).getMetricName()).getExpression());
        		task.setId(findMonitorById(((UpdateMonitor) t).getMetricName()).getId());
        		task.setRate(((UpdateMonitor) t).getRate());
        		(MonitoredMetrics.get(((UpdateMonitor) t).getMetricName())).cancelScraping();
        		MonitoredMetrics.remove(((UpdateMonitor) t).getId());
        		for(Task t1: currentTasks) if(t1.getId()==task.getId()) currentTasks.remove(t1);
        		currentTasks.add(task);	
        		if( task.getRate() instanceof PeriodicRate ) {
    				PeriodicScraper p = new PeriodicScraper(threadPoolforScraping,task,influxDB,appRepository);
    				MonitoredMetrics.put(task.getAdminmetric().getMetricName(),p);
    				p.scrap();
    			}
    			if(task.getRate() instanceof StochasticRate ) {
    				
    				StochasticScraper p = new StochasticScraper(threadPoolforScraping, task,influxDB,appRepository);
    				MonitoredMetrics.put(task.getAdminmetric().getMetricName(),p);
    				
    				p.scrap();
    			
    			}
    			if(task.getRate() instanceof TimeSerieRate ) {
    				TimeSerieScraper p = new TimeSerieScraper(threadPoolforScraping,task,influxDB,appRepository);
    				MonitoredMetrics.put(task.getAdminmetric().getMetricName(),p);
    				p.scrap();
    			}
        	}
    		}
        	
	     t.setFinished(true);
       }
        
      /**readMonitor is used to get all the adminMetrics or a specific adminMetric refrenced  
         by its name or it is used to get an internal metric value**/
       if(t instanceof ReadMonitor) {
    	   
    	 if(((ReadMonitor)t).isInternalMetric()) {
    		 System.out.println("it is internal metric");
    		 MonitoringMessage msg= new MonitoringMessage(); 
    		 
    		 if(((ReadMonitor)t).getInternalMetricType().compareTo("counter")==0) {
    			 msg.setMetricType("counter");
    		  String[] s= ((ReadMonitor)t).getInternalMetricUniqueIdentifier().split("\\.");
    		  msg.setMetricName(s[s.length-1]);
              msg.setOp(OperationType.GetValue);
        	((ReadMonitor)t).setOneMetricResult(sendMonitoringMessage(s[0],s[1],msg));}
    		
    	  
    		 else {
             /**measure rtt**/
    			 msg.setMetricType("rtt");
    			 String[] s= ((ReadMonitor)t).getRttInternalMetric().split(":");
    			 msg.setUrlDesMs(s[s.length-1]);
    			 String[] input=s[1].split("\\.");
        		 msg.setOp(OperationType.GetValue);			
              ((ReadMonitor)t).setOneMetricResult(sendMonitoringMessage(input[0],input[1],msg));

    		 }
    		
    		 
    		 }
    	   
    	   
       else{ 
    	   if(((ReadMonitor)t).isAll()) {
    		   
    	 QueryResult queryResult = influxDB.query(new Query("SELECT last(value), * FROM metrics GROUP BY MetricName", "metricdb"));
    	 InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<influxdbMappingClass> memoryPointList =  resultMapper
	     .toPOJO(queryResult, influxdbMappingClass.class);
		((ReadMonitor)t).setResult(memoryPointList);

    	   }
    	   else {
    		
    		   
   		    QueryResult queryResult = influxDB.query(new Query("SELECT * FROM metrics WHERE MetricName = '"+((ReadMonitor)t).getMetricName()+"' ORDER BY time DESC LIMIT "+((ReadMonitor)t).getLimit(), "metricdb"));
			InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
			List<influxdbMappingClass> memoryPointList = resultMapper
			  .toPOJO(queryResult, influxdbMappingClass.class);
			((ReadMonitor)t).setResult(memoryPointList);
		
    	       }
	
            }
    	 
 	    t.setFinished(true);
       }
       
       
       if(t instanceof DirectOpOnInternalMetric) {
    	   
    	   String[] s= ((DirectOpOnInternalMetric)t).getInternalMetric().split("\\.");
    	   MonitoringMessage msg= new MonitoringMessage(); 
    		 msg.setMetricType("counter");
    		msg.setMetricName(s[s.length-1]);
    	   switch( ((DirectOpOnInternalMetric)t).getOperation()) {
    	   case "disable":
    		  msg.setOp(OperationType.DisableMetric); 
    		  break;
    	   case "enable":
    		   msg.setOp(OperationType.EnableMetric); 
    	       break;
    	   case "reset":
    		   msg.setOp(OperationType.Reset); 
    		   break;
    	   default:
    	   }
    	 String result=  sendMonitoringMessage(s[0],s[1],msg);
    	 if(result.compareTo("ok")==0) t.setFinished(true);
    	   
       }
    
        
       /**create Notifier Task**/
        if(t instanceof CreateNotifier) {
        	currentTasks.add(t);
        
	     AlertGenerator a = new AlertGenerator((CreateNotifier)t, influxDB,threadPoolToSendAlerts);
	     for(String s :((CreateNotifier)t).getMetrics())
	     {
	    	for(AdminMetric am : metricsCurrentValues) {
	    		if(am.getMetricName().compareTo(s)==0) {
	    			AdminMetric metric=new AdminMetric();
	    			metric.setMetricName(s);
	    			metric.setMetrics(am.getMetrics());
	    			metric.setValue(am.getValue());
	    			metric.setExpression(am.getExpression());
	    			metric.setType(am.getType());
	    			a.getMetrics().add(metric);
	    			am.addPropertyChangeListener(a);
	    		}
	    	}
	     }
         a.generateAlert();
	    this.currentAlertGenerators.put(((CreateNotifier)t).getId(),a);
	    t.setFinished(true);
        }
        
        
     /**stop Notifier task**/
        if(t instanceof DeleteNotifier) {
        	
        	
     if (currentAlertGenerators.containsKey(((DeleteNotifier) t).getNotifierId())) { (currentAlertGenerators.get(((DeleteNotifier) t).getNotifierId())).cancelNotifying();
        	
       currentAlertGenerators.remove(((DeleteNotifier) t).getNotifierId());
       QueryResult queryResult = influxDB.query(new Query("DELETE FROM alerts WHERE id = '"+((DeleteNotifier) t).getNotifierId()+"'", "alertdb")); 
     }
		}
        
        
        /**Read Notifier Task**/
        
        if(t instanceof ReadNotifier) {
        	
        	if(((ReadNotifier)t).isAll()) {
        		   
       		    QueryResult queryResult = influxDB.query(new Query("SELECT * FROM alerts", "alertdb"));
    			InfluxDBResultMapper resultMapper1 = new InfluxDBResultMapper();
    			List<influxdbAlertMapping> memoryPointList1 = resultMapper1
    			  .toPOJO(queryResult, influxdbAlertMapping .class);
    			((ReadNotifier)t).setResult(memoryPointList1);  
     		   }
     	   
     	   else {
     		   
      		 QueryResult queryResult = influxDB.query(new Query("SELECT * FROM alerts WHERE id = '"+((ReadNotifier)t).getId()+"'", "alertdb"));
   			InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
   			List<influxdbAlertMapping> memoryPointList = resultMapper
   		    .toPOJO(queryResult, influxdbAlertMapping.class);
   			((ReadNotifier)t).setResult(memoryPointList);
     		   
     	       }
    	    t.setFinished(true);
        }
        
	}    


	


	public Map<String, AlertGenerator> getCurrentAlertGenerators() {
		return currentAlertGenerators;
	}


	public void setCurrentAlertGenerators(Map<String, AlertGenerator> currentAlertGenerators) {
		this.currentAlertGenerators = currentAlertGenerators;
	}


	public ArrayList<Task> getCurrentTasks() {
		return currentTasks;
	}


	public void setCurrentTasks(ArrayList<Task> currentTasks) {
		this.currentTasks = currentTasks;
	}
	
	public CreateMonitor findMonitorById(String id) {
	
		int i=0;
		while(i<currentTasks.size()) {
			if(currentTasks.get(i) instanceof CreateMonitor ) {
				if(((CreateMonitor)(currentTasks.get(i))).getAdminmetric().getMetricName().compareTo(id)==0) {
					 return (CreateMonitor)(currentTasks.get(i));
					
				}
				
			}
			i++;
		}
		
		
		return null;
		
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
	
	public String sendMonitoringMessage(String appName, String msID, MonitoringMessage msg) {
	
  
    NCEM ncem =findNcemToContactMS(msID,appName);
    String result=   ncem.sendMonitoringMsg(msID,msg.toString());
    return result;
	}
	
	
	public boolean taskIsActive(String id) {
		int i=0;
		while(i<currentTasks.size()) {
			if((currentTasks.get(i).getId()).compareTo(id)==0) return true;
			i++;
			
		}
		return false;
	}
	
	
	}

