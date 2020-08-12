package Alert;

import java.time.Instant;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Measurement(name = "alerts")
public class influxdbAlertMapping {

	
	@Column(name = "time")
	 private Instant time;
	
	public Instant getTime() {
		return time;
	}
	public void setTime(Instant time) {
		this.time = time;
	}
	@Column(name="id",tag=true)
	private String id;
	
	@Column(name = "value")
	private Double value=0.0;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
}
