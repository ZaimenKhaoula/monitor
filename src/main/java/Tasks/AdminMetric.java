package Tasks;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class AdminMetric {

	private String MetricName;
	private Double value=0.0;
	private String type;
	private ArrayList<InternalMetric> metrics;
	private PropertyChangeSupport support;
	public AdminMetric(){
		metrics = new ArrayList<InternalMetric>();
		support = new PropertyChangeSupport(this);
	}
	public String getMetricName() {
		return MetricName;
	}
	public void setMetricName(String metricName) {
		MetricName = metricName;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		support.firePropertyChange(getMetricName(), this.value, value);
		this.value = value;
	}
	
	 public void addPropertyChangeListener(PropertyChangeListener pcl) {
	        support.addPropertyChangeListener(pcl);
	    }
	 
	    public void removePropertyChangeListener(PropertyChangeListener pcl) {
	        support.removePropertyChangeListener(pcl);
	    }
		
		
		@Override
		public String toString() {
			String s="";
			for(InternalMetric m : metrics) 
				s=s+m.toString()+":";
			return MetricName+"-"+type+"-"+Double.toString(value)+"-"+s;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public ArrayList<InternalMetric> getMetrics() {
			return metrics;
		}
		public void setMetrics(ArrayList<InternalMetric> metrics) {
			this.metrics = metrics;
		}
	
}
