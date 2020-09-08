package Tasks;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.Instant;
import java.util.ArrayList;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

public class AdminMetric {
    
	
	private String MetricName;

	private Double value=0.0;

	private String type;
	
	
	private ArrayList<InternalMetric> metrics;
	private String expression;
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
		
	
	    public String internelmerticsToString() {
	    	String s="";
			for(InternalMetric m : metrics) 
				s=s+m.toString()+":";
			return s;
	    }
	    public String toSave() {
	    	return MetricName+"#"+type+"#"+Double.toString(value)
	    			+"#"+expression;
	    }
	    
		@Override
		public String toString() {
			
			return toSave()+"#"+internelmerticsToString();
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
		public String getExpression() {
			return expression;
		}
		public void setExpression(String expression) {
			this.expression = expression;
		}
	
	
}
