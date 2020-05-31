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
	private Runnable scrap;
	CreateMonitor t = new CreateMonitor();
	private final ScheduledExecutorService runner;
	
	//constructeur de StochasticScraper... puisque la fonction run ne change pas on peut la mettre dans le consucteur
	public StochasticScraper(ScheduledExecutorService runner, Task t) {
		this.runner=runner;
		setEnable(true);
		this.t=(CreateMonitor)t;
		scrap = new Runnable()
	    {
	        
	        public void run()
	        {	
	        	//recupere le nom de l'app et pour chaque microservice trouver
	        	//le ncem adequat, l'utilieser pour contacter les microservice par msg de 
	        	//monitorig, attendre la reponse de ts les ncem, met à jr les valeurs des mitrique
	        	//preparer l'exppresion et l'evaluer, enfin sauvegarger la nouvelle valeur dans le 
	        	System.out.println("scaring....");
	        }
	
	
	         
	    };
	}
	 

	
	// fonction pour evaluer l'expression ( la règle d'alerte) 
	public void evaluateExression() {}
	public void saveValue() {}
	
	public void scrap(){

		
		 while(isEnable()==true) {
			 startTime = System.currentTimeMillis();
			 future= runner.submit(scrap);
			 endTime = System.currentTimeMillis();
			 System.out.println(endTime);
		
				try {
					future.get();
				} catch (InterruptedException | ExecutionException e) {
					
				} 

					waitNextScrap();
				
			}	 
	 
   }
	
	public long period() {
         String period= ((StochasticRate) (t.getRate())).getParameters().get(0);
		 if(((StochasticRate) (t.getRate())).getLoi()=="uniform")
		      return (long) (Math.random()*Long.parseLong(period));	 
		 else /*(((StochasticRate) (t.getRate())).getLoi()=="exponential")*/
	   	  		
			return (long) ((-Math.log(Math.random()))/Double.parseDouble("0.1"));	 

	}
	
	
	public void waitNextScrap()
	{
		if(((StochasticRate)(t.getRate())).getParameters().get(1).compareTo("us")==0)
			try {
				TimeUnit.MICROSECONDS.sleep(period()-(endTime - startTime));
			} catch (InterruptedException e) {
				
			}
		else {
			
		if(((StochasticRate)(t.getRate())).getParameters().get(1).compareTo("ms")==0)
			try {
				TimeUnit.MILLISECONDS.sleep(period()-(endTime - startTime));
			} catch (InterruptedException e) {

			}
		else {if(((StochasticRate)(t.getRate())).getParameters().get(1).compareTo("s")==0)
			try {
				TimeUnit.SECONDS.sleep(period()-(endTime - startTime));
			} catch (InterruptedException e) {
				
			}
		else {if(((StochasticRate)(t.getRate())).getParameters().get(1).compareTo("min")==0)
			try {
				TimeUnit.MINUTES.sleep(period()-(endTime - startTime));
			} catch (InterruptedException e) {
				
			}
		else {if(((StochasticRate)(t.getRate())).getParameters().get(1).compareTo("h")==0)
			try {
				TimeUnit.HOURS.sleep(period()-(endTime - startTime));
			} catch (InterruptedException e) {
				
			}
		else
			try {
				TimeUnit.DAYS.sleep(period()-(endTime - startTime));
			} catch (InterruptedException e) {
				
			}
		}}}}
		
	}
	
	
	public void cancelScraping() {
		setEnable(false);
		future.cancel(true);
		
	}
	
}
