package MetricScraping;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import Tasks.*;


public class StochasticScraper extends Scraper{
	long startTime;
	long endTime;
	
    Future<?> future;
	private Runnable scraper;
	private final ScheduledExecutorService runner;
	
	//constructeur de StochasticScraper... puisque la fonction run ne change pas on peut la mettre dans le consucteur
	public StochasticScraper(ScheduledExecutorService runner, Task t) {
		this.runner=runner;
		setEnable(true);
		this.task=(CreateMonitor)t;
		scraper = new Runnable()
	    {	        
	        public void run()
	        {
	        	getAllInternalMetricsValueOfTheAdminValue();
	        }         
	    };
	}
	 

	

	
	public void scrap(){

		
		 while(isEnable()==true) {
			 startTime = System.currentTimeMillis();
			 future= runner.submit(scraper);

		
				try {
					future.get();
				} catch (InterruptedException | ExecutionException e) {
					
				} 
                    evaluateExression();
                    saveValue();
       			    endTime = System.currentTimeMillis();
					waitNextScrap();
				
			}	 
	 
   }
	
	public long period() {
         String period= ((StochasticRate) (task.getRate())).getParameters().get(0);
		 if(((StochasticRate) (task.getRate())).getLoi()=="uniform")
		      return (long) (Math.random()*Long.parseLong(period));	 
		 else /*(((StochasticRate) (t.getRate())).getLoi()=="exponential")*/
	   	  		
			return (long) ((-Math.log(Math.random()))/Double.parseDouble((task.getRate()).getParameters().get(0)));	 

	}
	
	
	public void waitNextScrap()
	{
		if(((StochasticRate)(task.getRate())).getParameters().get(1).compareTo("us")==0)
			try {
				if((endTime - startTime)*1000>period())
					TimeUnit.MILLISECONDS.sleep(0);
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
					TimeUnit.MILLISECONDS.sleep(0);
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
		}}}}
		
	}
	
	
	public void cancelScraping() {
		setEnable(false);
		future.cancel(true);
		
	}
	
}
