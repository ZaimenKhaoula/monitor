package MetricScraping;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import java.util.concurrent.TimeUnit;


import Tasks.*;

public class TimeSerieScraper extends Scraper {
	long startTime;
	long endTime;
    Future<?> future;
    private final ScheduledExecutorService runner;
	private Runnable scrap;
	CreateMonitor t = new CreateMonitor();
	public TimeSerieScraper(ScheduledExecutorService runner, Task t) {
		this.runner=runner;
		setEnable(true);
		this.t=(CreateMonitor)t;
		scrap = new Runnable()
	    {
	        
	        public void run()
	        
	        {	System.out.println(System.currentTimeMillis()/1000);
	        	System.out.println("scraping");
	        
	    }
	
	
	         
	    };
	}
	
	public void evaluateExression() {}
	
	public void saveValue() {}
	
	
	
	public void scrap() throws InterruptedException {
		
		boolean repeat = (((TimeSerieRate)(t.getRate())).getParameters()).contains("loop");
		System.out.println(repeat);
		 do	{
			 oneTimeScraping() ;
			 
		 }while(repeat);
		 
		 
		 System.out.println("finished");	 
	 
  }
	
	public void oneTimeScraping() throws InterruptedException{
		
		 int i=1;
		
		 while(isEnable()==true && i<(((TimeSerieRate)(t.getRate())).getParameters()).size()) {
			 
			 startTime = System.currentTimeMillis();
			 future= runner.submit(scrap);
			 endTime = System.currentTimeMillis();
			 
			 try {future.get();} catch (ExecutionException e) {e.printStackTrace();}
			 waitNextScrap(i);
			 i=i+2;}
		 future= runner.submit(scrap);
	}
	
	
	
	
	public long period(int i) {
		
        return (long) (Long.parseLong(((TimeSerieRate) (t.getRate())).getParameters().get(i)));
		 
	}
	
	public void waitNextScrap(int i) throws InterruptedException
	{
		if(((TimeSerieRate)(t.getRate())).getParameters().get(1).compareTo("us")==0)
			TimeUnit.MICROSECONDS.sleep(period(i-1)-(endTime - startTime));
		else {
			
		if(((TimeSerieRate)(t.getRate())).getParameters().get(1).compareTo("ms")==0)
		 TimeUnit.MILLISECONDS.sleep(period(i-1)-(endTime - startTime));
		else {if(((TimeSerieRate)(t.getRate())).getParameters().get(1).compareTo("s")==0)
			TimeUnit.SECONDS.sleep(period(i-1)-(endTime - startTime));
		else {if(((TimeSerieRate)(t.getRate())).getParameters().get(1).compareTo("min")==0)
			 TimeUnit.MINUTES.sleep(period(i-1)-(endTime - startTime));
		else {if(((TimeSerieRate)(t.getRate())).getParameters().get(1).compareTo("h")==0)
			TimeUnit.HOURS.sleep(period(i-1)-(endTime - startTime));
		else
			TimeUnit.DAYS.sleep(period(i-1)-(endTime - startTime));
		}}}}
		
	}
	
	public void cancelScraping() {
		setEnable(false);
		future.cancel(true);
		
	}
	
}
