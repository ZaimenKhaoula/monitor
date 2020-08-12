package Tasks;

import java.time.Instant;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;


@Measurement(name = "metrics")
public class influxdbMappingClass {
	 
	@Column(name = "time")
	 private Instant time;
	
	public Instant getTime() {
		return time;
	}
	public void setTime(Instant time) {
		this.time = time;
	}
	@Column(name="MetricName",tag=true)
	private String MetricName;
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMetricName() {
		return MetricName;
	}
	public void setMetricName(String metricName) {
		MetricName = metricName;
	}
	@Column(name = "value")
	private Double value=0.0;
	@Column(name = "type")
	private String type;
	

}
