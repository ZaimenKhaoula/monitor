package MetricScraping;



import org.springframework.beans.factory.annotation.Autowired;

import Monitoring.MonitoringMessage;
import Monitoring.OperationType;
import Tasks.Counter;
import Tasks.CreateMonitor;
import Tasks.InternalMetric;
import Tasks.RTT;
import pfe.mw.models.Application;
import pfe.mw.models.ApplicationRepository;
import pfe.mw.models.NCEM;

public abstract class Scraper{

	protected boolean enable=false;
	protected CreateMonitor task = new CreateMonitor();
	 @Autowired
	protected ApplicationRepository appRepository;
		
	public void scrap() throws InterruptedException{
		
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
                   if(getUrlMsByMsName(m.getAppName(),((RTT)m).getIDusDestination())!=null) {
                  	 msg.setUrlDesMs(getUrlMsByMsName(m.getAppName(),((RTT)m).getIDusDestination()));
                  
                   }
                   des=((RTT) m).getIDusSource();
                   ncem =findNcemToContactMS(((RTT) m).getIDusSource(),((RTT) m).getAppName());
  	    	}
      	    
      	    
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
	public void saveValue() {}
	
	
	public void evaluateExression() {}
	public void cancelScraping() {}


	public ApplicationRepository getAppRepository() {
		return appRepository;
	}


	public void setAppRepository(ApplicationRepository appRepository) {
		this.appRepository = appRepository;
	}
	
	
	public String getUrlMsByMsName(String appName,String msID) {
		Application app= appRepository.findApplicationByName(appName);
       int i=0;
      
       while(i<app.getNcems().size()) {
    	   
    	 if( app.getNcems().get(i).getMsIdAndURL().containsKey(msID)) {
    		  return app.getNcems().get(i).getMsIdAndURL().get(msID);
    	   }
    	 i++;
       }
		return null;
	}
	
	
	public 
	
	
}
