package monitoringApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class MAIN {
      
	public static void main(String[] args) throws InterruptedException {
		
		
		SpringApplication.run(MAIN.class,args );
		//MonitoringManager monitor= new MonitoringManager();
		/*CreateNotifier notifier = new CreateNotifier();
		notifier.getMetrics().add("M1");
		notifier.getMetrics().add("M2");
		AdminMetric adm1 = new AdminMetric("M1");
		AdminMetric adm2 = new AdminMetric("M2");
		adm1.setValue(20.5);
		adm2.setValue(1.5);
		notifier.getExpression().add("M1");
		notifier.getExpression().add(">");
		notifier.getExpression().add("3");
		notifier.getExpression().add("/");
		notifier.getExpression().add("2");
		notifier.getExpression().add("*");
		notifier.getExpression().add("M2");
		MonitoringManager.getMetricsCurrentValues().add(adm1);
		System.out.println(MonitoringManager.getMetricsCurrentValues().get(0).getValue());
		MonitoringManager.getMetricsCurrentValues().add(adm2);*/
	/*******************************************************************************************/
   /*******************************************************************************************/	
		/*TimeSerieRate rate= new TimeSerieRate();
		
		rate.getParameters().add("10");
		rate.getParameters().add("s");
		rate.getParameters().add("15");
		rate.getParameters().add("s");
		rate.getParameters().add("20");
		rate.getParameters().add("s");*/
		//rate.getParameters().add("loop");
		
		/*CreateMonitor m= new CreateMonitor();
		m.setRate(rate);
		m.setMetricName("M1");*/
	
	
		/*System.out.println("creating monitoring manager and processing notifier task.... ");
		monitor.taskProccessing(m);
	System.out.println("terminer ");*/
       // MonitoringManager.getMetricsCurrentValues().get(1).setValue(2.9);
      
	}	
	
}
