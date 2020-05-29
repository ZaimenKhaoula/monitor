package Tasks;

public abstract class InternalMetric {
	 
	private String appName;
	
	private int Value;
	
		public int getValue() {
		return Value;
	}
	public void setValue(int value) {
		Value = value;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
}
