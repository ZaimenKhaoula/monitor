package MetricScraping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pfe.mw.models.Application;
import pfe.mw.models.ApplicationRepository;
import pfe.mw.models.NCEM;

public abstract class Scraper{

	private boolean enable=false;
	
	 @Autowired
	 private ApplicationRepository appRepository;
		
	public void scrap() throws InterruptedException{
		
	}
	
	public NCEM findNcemToContactMS(String msIdentifier) {
		List<Application> apps= appRepository.findAll();
	    int i=0;
	    int j;
	    int k;
	    while(i<apps.size())
	    { j=0;
	    	while(j<apps.get(i).getNcems().size()) {
	    	     k=0;
	    		 while(k<apps.get(i).getNcems().get(j).getNc().getMicroServices().size()) {
	    			 if(apps.get(i).getNcems().get(j).getNc().getMicroServices().get(k).getIdMicroservice().compareTo(msIdentifier)==0)
	    		      return apps.get(i).getNcems().get(j);
	    			 k++;
	    		 }
	    		 j++;
	    		 
	    	}
	    	i++; 
	    	
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
	
	
}
