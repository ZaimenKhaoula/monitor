package monitoringApi;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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

import com.github.fge.jsonpatch.JsonPatch;

import Monitoring.MonitoringManager;
import TaskGeneration.ParseException;
import TaskGeneration.Analyseur;
import Tasks.*;

@RestController
@RequestMapping("/api")
public class MonitoringsRestController {
	
	Analyseur parser =null;
	String operation ="";
	MonitoringManager monitorManager = new MonitoringManager();
	InputStream is;
	
	
	@GetMapping("/monitorings")
	public String getAllMonitoringTasks() throws ParseException, InterruptedException {
       ReadMonitor reader = new ReadMonitor();
       reader.setAll(true);
		monitorManager.taskProccessing(reader);
		return reader.toString();
		
	}
	
	@GetMapping("/monitorings/{MetricName}")
	public void getMonitoringTask(@PathVariable String MetricName) throws InterruptedException, ParseException {
		
		is = new ByteArrayInputStream(MetricName.getBytes());
		if (parser==null) parser= new Analyseur(is); 
		else parser.ReInit(is);
		monitorManager.taskProccessing(parser.ReadMonitoringResource());
	}
	
	@PostMapping("/monitorings")
	public ResponseEntity<String> addMonitoringTask(@RequestBody MonitorModel monitor) throws InterruptedException, ParseException {
		is = new ByteArrayInputStream((monitor.toString()).getBytes());
		if (parser==null) parser= new Analyseur(is); 
		else parser.ReInit(is);
		System.out.println((parser.CreateMonitoringResource()).toString());
		return new ResponseEntity<>("start scraping metric", HttpStatus.OK);

		//monitorManager.taskProccessing(parser.CreateMonitoringResource());
		
	}
	
	@PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
public void updateoMonitoringTask(@PathVariable String id, @RequestBody JsonPatch patch) throws InterruptedException, ParseException {
		
		/*operation="UpdateMonitor"+MetricName+newRate.toString();
		is = new ByteArrayInputStream(operation.getBytes());
		if (parser==null) parser= new Analyseur(is); 
		else parser.ReInit(is);
		monitorManager.taskProccessing(parser.UpdateMonitoringResource());*/
		
	}
	@PutMapping("/monitorings/{MetricName}")
	public void updateMonitoringTask(@PathVariable String MetricName ,@RequestBody RateModel newRate) throws InterruptedException, ParseException {
		
		operation="UpdateMonitor"+MetricName+newRate.toString();
		is = new ByteArrayInputStream(operation.getBytes());
		if (parser==null) parser= new Analyseur(is); 
		else parser.ReInit(is);
		monitorManager.taskProccessing(parser.UpdateMonitoringResource());
		
	}
	
	@DeleteMapping("/monitorings/{MetricName}")
public void deleteMOnitoringTask(@PathVariable String MetricName) throws InterruptedException, ParseException {
		DeleteMonitor deleteM = new DeleteMonitor();
		deleteM.setMetricName(MetricName);
		monitorManager.taskProccessing(deleteM);
	
		
	}
	@GetMapping("/notifiers")
	public void getAllNotifierTasks() {}
	
	@PostMapping("/notifiers")
	public void addMonitoringTask(@RequestBody NotifierModel notifier) throws InterruptedException, ParseException{
		is = new ByteArrayInputStream((notifier.toString()).getBytes());
		if (parser==null) parser= new Analyseur(is); 
		else parser.ReInit(is);
		monitorManager.taskProccessing(parser.CreateNotifierResource());
		
	}
		
   @DeleteMapping("/notifiers/{notifierId}")
   public void deleteNotifierTask(@PathVariable String notifierId) throws InterruptedException, ParseException {
	    DeleteNotifier deleteN = new DeleteNotifier();
	    deleteN.setNotifierId(notifierId);
		monitorManager.taskProccessing(deleteN);
				
			}	
   
	
	
}
