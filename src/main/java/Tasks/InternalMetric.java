package Tasks;

public abstract class InternalMetric {
	 
	private String appName;

	private float Value;
	
		public float getValue() {
		return Value;
	}
	public void setValue(float value) {
		Value = value;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	
}
