package MetricScraping;


import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;

import Tasks.*;

public class PeriodicScraper extends  Scraper {
	ScheduledFuture<?> futureTask;
	public PeriodicScraper(ScheduledExecutorService executor, Task t,InfluxDB influxDB) {
		super(executor,t,influxDB);
		
	}
	

	public void scrap() {
		
			if(isEnable())
			futureTask = runner.scheduleAtFixedRate(scrap,0,period(), TimeUnit.MICROSECONDS);
		   
		  
		
	
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
