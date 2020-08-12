package monitoringApi;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import Monitoring.MonitoringManager;
import TaskGeneration.ParseException;
import TaskGeneration.Analyseur;
import Tasks.*;




@RestController
@RequestMapping("/api")
public class MonitoringsRestController {
	

    @Autowired
	MonitoringManager monitorManager;
    

	Analyseur parser =null;
	String operation ="";
	InputStream is;

	
	
	@GetMapping("/monitorings")
	public List<influxdbMappingClass> getAllMonitoringTasks() {
       ReadMonitor reader = new ReadMonitor();
       reader.setAll(true);
	   monitorManager.taskProccessing(reader);
	  return reader.getResult();
		
	}
	
	@GetMapping("/monitorings/{MetricName}")
	public String getMonitoringTask(@PathVariable(required = true) String MetricName,@PathVariable(required = true) int nbDemandee) throws InterruptedException, ParseException {
		is = new ByteArrayInputStream(MetricName.getBytes());
		if (parser==null) parser= new Analyseur(is); 
		else parser.ReInit(is);
		ReadMonitor r = new ReadMonitor();
		r=parser.ReadMonitoringResource();
		
		monitorManager.taskProccessing(r);

		return r.getOneMetricResult();
	
		
	}
	
	@PostMapping("/monitorings/add")
	public ResponseEntity<String> addMonitoringTask(@RequestBody MonitorModel monitor) throws InterruptedException, ParseException {
		is = new ByteArrayInputStream((monitor.toString()).getBytes());
		if (parser==null) parser= new Analyseur(is); 
		else parser.ReInit(is);
		monitorManager.taskProccessing(parser.CreateMonitoringResource());
		return new ResponseEntity<>("start scraping metric...", HttpStatus.OK);
	}
	
	
	@PatchMapping("/monitorings/update/{MetricName}")
	public ResponseEntity<String> updateMonitoringTask(@PathVariable String MetricName ,@RequestBody RateModel newRate){
		
		operation="UpdateMonitor"+MetricName+newRate.toString();
		is = new ByteArrayInputStream(operation.getBytes());
		if (parser==null) parser= new Analyseur(is); 
		else parser.ReInit(is);
		UpdateMonitor m=null;
		
		try {
			m=parser.UpdateMonitoringResource();
			monitorManager.taskProccessing(m);
		} catch (ParseException e) {
			
		}
		if(m.isFinished())
	     	return new ResponseEntity<> ("MonitoringRessource Updated" , HttpStatus.OK);
		 else 
				return new ResponseEntity<String>("operation failed", HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/monitorings/delete/{MetricName}")
public ResponseEntity<String> deleteMonitoringTask(@PathVariable String MetricName) throws InterruptedException, ParseException {
		DeleteMonitor deleteM = new DeleteMonitor();
		deleteM.setId(MetricName);
		monitorManager.taskProccessing(deleteM);
	   if(deleteM.isFinished())
		return new ResponseEntity<> ("MonitoringRessource Deleted" , HttpStatus.OK);
	   else 
			return new ResponseEntity<String>("operation failed", HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping(path = "/monitorings/disable/{InternalMetricName}")
	public ResponseEntity<String> disableMetric(@PathVariable(required = true) String InternalMetricName)  {
	    DirectOpOnInternalMetric m = new DirectOpOnInternalMetric (InternalMetricName,"disable") ;     

	          monitorManager.taskProccessing(m);
	          if (m.isFinished())
			return new ResponseEntity<String>("intenal metric disabled ", HttpStatus.OK);

		 else 
			return new ResponseEntity<String>("operation failed", HttpStatus.BAD_REQUEST);
		

	}	
	
	
	
	@PutMapping(path = "/monitorings/enable/{InternalMetricName}")
	public ResponseEntity<String> enableMetric(@PathVariable(required = true) String InternalMetricName)  {
		 DirectOpOnInternalMetric m = new DirectOpOnInternalMetric (InternalMetricName,"enable") ;    
	          monitorManager.taskProccessing(m);
	          if (m.isFinished())
			return new ResponseEntity<String>("intenal metric enabled ", HttpStatus.OK);

		 else 
			return new ResponseEntity<String>("operation failed", HttpStatus.BAD_REQUEST);
		

	}
	
	
	@PutMapping(path = "/monitorings/resetValue/{InternalMetricName}")
	public ResponseEntity<String> resetMetricValue(@PathVariable(required = true) String InternalMetricName)  {
		 DirectOpOnInternalMetric m = new DirectOpOnInternalMetric (InternalMetricName,"reset") ;    
	          monitorManager.taskProccessing(m);
	          if (m.isFinished())
			return new ResponseEntity<String>("intenal metric enabled ", HttpStatus.OK);

		 else 
			return new ResponseEntity<String>("operation failed", HttpStatus.BAD_REQUEST);
		

	}
	
	
	@GetMapping("/notifiers")
	public void getAllNotifierTasks() {
		ReadNotifier readerN = new ReadNotifier();
	     readerN.setAll(true);
         monitorManager.taskProccessing(readerN);
			
	}
	
	@PostMapping("/notifiers/update")
	public ResponseEntity<String> addNotifierTask(@RequestBody NotifierModel notifier) throws InterruptedException, ParseException{
		is = new ByteArrayInputStream((notifier.toString()).getBytes());
		if (parser==null) parser= new Analyseur(is); 
		else parser.ReInit(is);
		monitorManager.taskProccessing(parser.CreateNotifierResource());
		
		return new ResponseEntity<String> ("Notifier added succesfully" , HttpStatus.OK);
	}
		
   @DeleteMapping("/notifiers/delete/{notifierId}")
   public ResponseEntity<String> deleteNotifierTask(@PathVariable String notifierId) throws InterruptedException, ParseException {
	  
	   if(monitorManager.taskIsActive(notifierId)) {
	   DeleteNotifier deleteN = new DeleteNotifier();
	    deleteN.setNotifierId(notifierId);
		monitorManager.taskProccessing(deleteN);
		 return new ResponseEntity<String> ("Notifier deleted succesfully" , HttpStatus.OK);
	   }
	   else return new ResponseEntity<String> ("Notifier not found" , HttpStatus.BAD_REQUEST);
		   
				 
			}	
   
	
	
}
