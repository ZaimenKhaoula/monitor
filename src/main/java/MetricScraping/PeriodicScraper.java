package MetricScraping;


import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import Tasks.*;

public class PeriodicScraper extends  Scraper {
	private final ScheduledExecutorService executor;
	private Runnable scrap;
	ScheduledFuture<?> futureTask;
	public PeriodicScraper(ScheduledExecutorService executor, Task t) {
		this.executor=executor;
	    setEnable(true);
	
		this.task=(CreateMonitor)t;
		scrap = new Runnable()
	    {
	        
	        public void run()
	        {	
	        	System.out.println("period");
	        	//period();
	        	
	        	System.out.println((System.currentTimeMillis())/1000);
	    }
	
	
	         
	    };
	}
	

	public void scrap() {
		
		
		     if(isEnable())
			futureTask = executor.scheduleAtFixedRate(scrap,0,period(), TimeUnit.MICROSECONDS);
		   
		  
		
	
	}
	public long period() {
      return Long.parseLong(((PeriodicRate)(task.getRate())).getParameters().get(0));
		 
	}
	
	public TimeUnit unit()
	{
		if(((PeriodicRate)(task.getRate())).getParameters().get(1).compareTo("us")==0)
			return TimeUnit.MICROSECONDS;
		else {
			
		if(((PeriodicRate)(task.getRate())).getParameters().get(1).compareTo("ms")==0)
		return TimeUnit.MILLISECONDS;
		else {if(((PeriodicRate)(task.getRate())).getParameters().get(1).compareTo("s")==0)
			return TimeUnit.SECONDS;
		else {if(((PeriodicRate)(task.getRate())).getParameters().get(1).compareTo("min")==0)
			return TimeUnit.MINUTES;
		else {if(((PeriodicRate)(task.getRate())).getParameters().get(1).compareTo("h")==0)
			return TimeUnit.HOURS;
		else
			return TimeUnit.DAYS;
		}}}
		}
	}
	public void cancelScraping() {
		setEnable(false);
		futureTask.cancel(true);
		
	}
	
}
