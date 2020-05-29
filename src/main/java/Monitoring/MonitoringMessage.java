package Monitoring;

import java.util.HashMap;
import java.util.Map;

public class MonitoringMessage {
 
	
  //oerations set to do on internal metrics
  private	Map<OperationType,String> orders =new HashMap<OperationType,String>();

public Map<OperationType,String> getOrders() {
	return orders;
}

public void setOrders(Map<OperationType,String> orders) {
	this.orders = orders;
}


	
}
