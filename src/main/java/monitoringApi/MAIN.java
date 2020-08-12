package monitoringApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;






@SpringBootApplication
@ComponentScan({"monitoringApi","Monitoring","pfe.mw.models"})
@EnableMongoRepositories(basePackageClasses = pfe.mw.models.ApplicationRepository.class)
public class MAIN {
      
	public static void main(String[] args) throws InterruptedException {
		
		
		SpringApplication.run(MAIN.class,args );
	
      
	}	
	
}
