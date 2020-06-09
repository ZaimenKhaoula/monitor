package Monitoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import Alert.AlertGenerator;
import MetricScraping.*;
import Tasks.*;

public class MonitoringManager  {

	Map<String, Scraper> MonitoredMetrics;
	private static ArrayList<AdminMetric> metricsCurrentValues= new ArrayList<AdminMetric>() ;
	Map<String,AlertGenerator> currentAlertGenerators; 
	private ArrayList<Task> currentTasks;
	private ArrayList<String> activeInternalMetrics;
	ScheduledExecutorService executor;

	public MonitoringManager() {
		
		this.executor = Executors.newScheduledThreadPool(2);
		currentAlertGenerators =new HashMap<String,AlertGenerator>();
		currentTasks= new ArrayList<Task>();
		MonitoredMetrics =new HashMap<String,Scraper>();
		activeInternalMetrics=new ArrayList<String>();
		
	}

	
	public void taskProccessing(Task t){
	     
		
		// start monitoring task
		if(t instanceof CreateMonitor) {
			currentTasks.add(t);
			/* AdminMetric am= new AdminMetric(((CreateMonitor) t).getMetricName());
			 am.setMetricName(((CreateMonitor) t).getMetricName());*/
			 metricsCurrentValues.add(((CreateMonitor) t).getAdminmetric());
			
			
			if(((CreateMonitor) t).getRate() instanceof PeriodicRate ) {
				PeriodicScraper p = new PeriodicScraper(executor,t);
				MonitoredMetrics.put(((CreateMonitor) t).getAdminmetric().getMetricName(),p);
				p.scrap();
			}
			if(((CreateMonitor) t).getRate() instanceof StochasticRate ) {
				
				StochasticScraper p = new StochasticScraper(executor, t);
				MonitoredMetrics.put(((CreateMonitor) t).getAdminmetric().getMetricName(),p);
				
				p.scrap();
			
			}
			if(((CreateMonitor) t).getRate() instanceof TimeSerieRate ) {
				TimeSerieScraper p = new TimeSerieScraper(executor,t);
				MonitoredMetrics.put(((CreateMonitor) t).getAdminmetric().getMetricName(),p);
				p.scrap();
			}
				
		}
		
		// stop monitoring task
        if(t instanceof DeleteMonitor) {
        		if (MonitoredMetrics.containsKey(((DeleteMonitor) t).getId()) ) {(MonitoredMetrics.get(((DeleteMonitor) t).getId())).cancelScraping();
        		 currentTasks.remove((DeleteMonitor) t);
        		MonitoredMetrics.remove(((DeleteMonitor) t).getId());}
       
		}
      //SetMetricRate
        if(t instanceof UpdateMonitor) {
        	if (MonitoredMetrics.containsKey(((UpdateMonitor) t).getMetricName()) ) {
        		CreateMonitor task = new CreateMonitor();
        		 task=findMonitorById(((UpdateMonitor) t).getMetricName());
        		 task.setRate(((UpdateMonitor) t).getRate());
        		(MonitoredMetrics.get(((UpdateMonitor) t).getMetricName())).cancelScraping();
        		MonitoredMetrics.remove(((DeleteMonitor) t).getId());
        		if(((CreateMonitor) t).getRate() instanceof PeriodicRate ) {
    				PeriodicScraper p = new PeriodicScraper(executor,t);
    				MonitoredMetrics.put(((CreateMonitor) t).getAdminmetric().getMetricName(),p);
    				p.scrap();
    			}
    			if(task.getRate() instanceof StochasticRate ) {
    				
    				StochasticScraper p = new StochasticScraper(executor, task);
    				MonitoredMetrics.put(task.getAdminmetric().getMetricName(),p);
    				
    				p.scrap();
    			
    			}
    			if(task.getRate() instanceof TimeSerieRate ) {
    				TimeSerieScraper p = new TimeSerieScraper(executor,task);
    				MonitoredMetrics.put(task.getAdminmetric().getMetricName(),p);
    				p.scrap();
    			}
        	
    		}
        	
	     
       }
        
       
       if(t instanceof ReadMonitor) {
    	   
    	 if(((ReadMonitor)t).isInternalMetric()) {
    		 String[] s= ((ReadMonitor)t).getInternalMetricUniqueIdentifier().split(".");
    		 if(activeInternalMetrics.contains(((ReadMonitor)t).getMetricName()))
    	        sendMonitoringMessage("kjskfh",OperationType.GetValue,s[s.length-1]);
    		 else sendMonitoringMessage("kjskfh",OperationType.EnableMetric,s[s.length-1]);
    	 } 
    	   
    	   
       else{ 
    	   if(((ReadMonitor)t).isAll()) {
    		   for(AdminMetric am : metricsCurrentValues) {
    			   (((ReadMonitor)t).getResult()).add(am.toString()) ;
    			   
    		   }
    	   }
    	   else {
    		   boolean found =false;
    		   int i=0;
    		   while(i<metricsCurrentValues.size() && !found) {
    			if(metricsCurrentValues.get(i).getMetricName().compareTo(((ReadMonitor)t).getMetricName())==0) {
    				   found=true;
    				   ((ReadMonitor)t).getResult().add(metricsCurrentValues.get(i).toString());
    			   }
    			i++;
    		    }
    		   
    	       }
	
            }}
    
        
        // create Notifier Task
        if(t instanceof CreateNotifier) {
        	currentTasks.add(t);
        
	     AlertGenerator a = new AlertGenerator((CreateNotifier)t);
	     for(String s :((CreateNotifier)t).getMetrics())
	     {
	    	for(AdminMetric am : metricsCurrentValues) {
	    		if(am.getMetricName()==s) {
	    			AdminMetric metric=new AdminMetric();
	    			metric.setMetricName(s);
	    			metric.setValue(am.getValue());
	    			a.getMetrics().add(metric);
	    			am.addPropertyChangeListener(a);
	    		}
	    	}
	     }
         a.start();
	    this.currentAlertGenerators.put(((CreateNotifier)t).getId(),a);
        }
        
        
     // stop Notifier task
        if(t instanceof DeleteNotifier) {
        	
        	
     if (currentAlertGenerators.containsKey(((DeleteNotifier) t).getNotifierId())) { (currentAlertGenerators.get(((DeleteNotifier) t).getNotifierId())).cancelNotifying();
        	
       currentAlertGenerators.remove(((DeleteNotifier) t).getNotifierId());}
		}
        
        
        //Read Notifier Task
        
        if(t instanceof ReadNotifier) {
        	
        	if(((ReadNotifier)t).isAll()) {
     		   for(Task task : currentTasks) {
     			   if(task instanceof CreateNotifier) {
     			   (((ReadNotifier)t).getResult()).add(task.toString()) ;}
     			   
     		   }
     	   }
     	   else {
     		   boolean found =false;
     		   int i=0;
     		   while(i<currentTasks.size()&& !found) {
     			if(currentTasks.get(i) instanceof CreateNotifier && ((CreateNotifier)(currentTasks.get(i))).getId().compareTo(((ReadNotifier)t).getId())==0) {
     				   found=true;
     				   ((ReadNotifier)t).getResult().add(((CreateNotifier)currentTasks.get(i)).toString());
     			   }
     			i++;
     		    }
     		   
     	       }
        	
        }
        
	}    


	public static ArrayList<AdminMetric> getMetricsCurrentValues() {
		return metricsCurrentValues;
	}


	public static void setMetricsCurrentValues(ArrayList<AdminMetric> metricsCurrentValues) {
		MonitoringManager.metricsCurrentValues = metricsCurrentValues;
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
	
	
	public void sendMonitoringMessage(String dest, OperationType op, String internalMetric) {
	MonitoringMessage msg= new MonitoringMessage();
    msg.setOp(op);
    msg.setMetricName(internalMetric);
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
