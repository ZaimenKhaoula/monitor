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
public class StochasticScraper extends Scraper{
	long startTime;
	long endTime;
	
    Future<?> future;
	
	//constructeur de StochasticScraper... puisque la fonction run ne change pas on peut la mettre dans le consucteur
	public StochasticScraper(ScheduledExecutorService runner, Task t,InfluxDB influxDB,ApplicationRepository app) {
		super(runner, t, influxDB,app);
		
	}
	 

	

	
	public void scrap(){

		
		 while(isEnable()==true) {
			 startTime = System.currentTimeMillis();
			 future= runner.submit(scrap);
  
		
				try {
					
					future.get();
				} catch (InterruptedException | ExecutionException e) {
					
					
				} 
       			    endTime = System.currentTimeMillis();
					try {
						 
						waitNextScrap();
						
					} catch (InterruptedException e) {
						System.out.println("exception in waiting stocastic");
					}
					
			}	 
	 
   }
	
	public long period() {
         String period= ((StochasticRate) (task.getRate())).getParameters().get(0);
		 if(((StochasticRate) (task.getRate())).getLoi()=="uniform")
		      return (long) (Math.random()*Long.parseLong(period));	 
		 else /*(((StochasticRate) (t.getRate())).getLoi()=="exponential")*/
	   
			return (long) ((-Math.log(Math.random()))/Double.parseDouble((task.getRate()).getParameters().get(0)));	 

	}
	
	
	public void waitNextScrap() throws InterruptedException
	{
		/*if(((StochasticRate)(task.getRate())).getParameters().get(1).compareTo("us")==0)
			try {
				if((endTime - startTime)*1000>period())
					TimeUnit.MILLISECONDS.sleep(period()/1000);
				else
				TimeUnit.MICROSECONDS.sleep(period()-(endTime - startTime)*1000);
			} catch (InterruptedException e) {
				
			}
		else {
			
		if(((StochasticRate)(task.getRate())).getParameters().get(1).compareTo("ms")==0)
			try {
			if((endTime - startTime)>period())
				TimeUnit.MILLISECONDS.sleep(0);
			else
				TimeUnit.MILLISECONDS.sleep(period()-(endTime - startTime));
			} catch (InterruptedException e) {

			}
		else {if(((StochasticRate)(task.getRate())).getParameters().get(1).compareTo("s")==0)
			try {
				if((endTime - startTime)/1000>period())
					TimeUnit.SECONDS.sleep(period());
				else
				TimeUnit.SECONDS.sleep(period()-(endTime - startTime)/1000);
			} catch (InterruptedException e) {
				
			}
		else {if(((StochasticRate)(task.getRate())).getParameters().get(1).compareTo("min")==0)
			try {
				if((endTime - startTime)/60000>period())
					TimeUnit.MILLISECONDS.sleep(0);
				else
				TimeUnit.MINUTES.sleep(period()-(endTime - startTime)/60000);
			} catch (InterruptedException e) {
				
			}
		else {if(((StochasticRate)(task.getRate())).getParameters().get(1).compareTo("h")==0)
			try {
				TimeUnit.HOURS.sleep(period());
			} catch (InterruptedException e) {
				
			}
		else
			try {
				TimeUnit.DAYS.sleep(period());
			} catch (InterruptedException e) {
				
			}
		}}}}*/
		
		long p= period();
		while(p==0) {
			p=period();
		}
		System.out.println("the next scraping will start aftar .."+p+((StochasticRate)(task.getRate())).getParameters().get(1));
		
		if(((StochasticRate)(task.getRate())).getParameters().get(1).compareTo("us")==0)

				TimeUnit.MICROSECONDS.sleep(p);
		
			
		if(((StochasticRate)(task.getRate())).getParameters().get(1).compareTo("ms")==0)
			
				TimeUnit.MILLISECONDS.sleep(p);
		
		if(((StochasticRate)(task.getRate())).getParameters().get(1).compareTo("s")==0)
		{
				TimeUnit.SECONDS.sleep(p);
				
		}
		if(((StochasticRate)(task.getRate())).getParameters().get(1).compareTo("min")==0)
			
				TimeUnit.MINUTES.sleep(p);
			
		if(((StochasticRate)(task.getRate())).getParameters().get(1).compareTo("h")==0)
			
				TimeUnit.HOURS.sleep(p);
			
		if(((StochasticRate)(task.getRate())).getParameters().get(1).compareTo("jr")==0)
		
				TimeUnit.DAYS.sleep(p);
			
		}
		

	
	
	public void cancelScraping() {
		setEnable(false);
		future.cancel(true);
		
	}
	
}
