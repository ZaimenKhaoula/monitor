package MetricScraping;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.springframework.stereotype.Component;

import Tasks.*;
import pfe.mw.models.ApplicationRepository;

@Component
public class TimeSerieScraper extends Scraper {
	long startTime;
	long endTime;
    Future<?> future;
    
	public TimeSerieScraper(ScheduledExecutorService runner, Task t,InfluxDB influxDB,ApplicationRepository app) {
		super(runner,t,influxDB,app);	
	}
	
	public void scrap()  {
		
		boolean repeat = (((TimeSerieRate)(task.getRate())).getParameters()).contains("loop");
		System.out.println(repeat);
		 do	{
			 oneTimeScraping() ;
			 
		 }while(repeat);
		 
	 
  }
	
	public void oneTimeScraping(){
		
		 int i=1;
		
		 while(isEnable()==true && i<(((TimeSerieRate)(task.getRate())).getParameters()).size()+1) {
			 
			 startTime = System.currentTimeMillis();
			 future= runner.submit(scrap);
			 endTime = System.currentTimeMillis();
			 
			try {
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				
			}
			 waitNextScrap(i);
			 i=i+2;}
		// future= runner.submit(scrap);
	}
	
	
	
	
	public long period(int i) {
		
        return (long) (Long.parseLong(((TimeSerieRate) (task.getRate())).getParameters().get(i)));
		 
	}
	
	public void waitNextScrap(int i)
	{
		if(((TimeSerieRate)(task.getRate())).getParameters().get(1).compareTo("us")==0)
			try {
				TimeUnit.MICROSECONDS.sleep(period(i-1)-(endTime - startTime));
			} catch (InterruptedException e) {
				
			}
		else {
			
		if(((TimeSerieRate)(task.getRate())).getParameters().get(1).compareTo("ms")==0)
			try {
				TimeUnit.MILLISECONDS.sleep(period(i-1)-(endTime - startTime));
			} catch (InterruptedException e) {
				
			}
		else {if(((TimeSerieRate)(task.getRate())).getParameters().get(1).compareTo("s")==0)
			try {
				TimeUnit.SECONDS.sleep(period(i-1)-(endTime - startTime));
			} catch (InterruptedException e) {
				
			}
		else {if(((TimeSerieRate)(task.getRate())).getParameters().get(1).compareTo("min")==0)
			try {
				TimeUnit.MINUTES.sleep(period(i-1)-(endTime - startTime));
			} catch (InterruptedException e) {
				
			}
		else {if(((TimeSerieRate)(task.getRate())).getParameters().get(1).compareTo("h")==0)
			try {
				TimeUnit.HOURS.sleep(period(i-1)-(endTime - startTime));
			} catch (InterruptedException e) {
				
			}
		else
			try {
				TimeUnit.DAYS.sleep(period(i-1)-(endTime - startTime));
			} catch (InterruptedException e) {
				
			}
		}}}}
		
	}
	
	public void cancelScraping() {
		setEnable(false);
		future.cancel(true);
		
	}
	
}
