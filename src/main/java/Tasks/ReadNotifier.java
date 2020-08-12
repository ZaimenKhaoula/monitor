package Tasks;

import java.util.ArrayList;
import java.util.List;

import Alert.influxdbAlertMapping;

public class ReadNotifier extends Task {
	private boolean all =false;
	private List<influxdbAlertMapping> result = new ArrayList<influxdbAlertMapping>();
	
	public boolean isAll() {
		return all;
	}
	public void setAll(boolean all) {
		this.all = all;
	}

	public List<influxdbAlertMapping> getResult() {
		return result;
	}
	public void setResult(List<influxdbAlertMapping>resulat) {
		this.result = resulat;
	}
	


}
