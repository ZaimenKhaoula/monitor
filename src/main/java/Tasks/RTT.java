package Tasks;

public class RTT extends InternalMetric {

	private String IDusSource;
	private String IDusDestination;
	public String getIDusSource() {
		return IDusSource;
	}
	public void setIDusSource(String iDusSource) {
		String[] input=iDusSource.split("\\.");
		this.setAppName(input[0]);
		 String s=""; for(int i=1; i < (input.length-1);i++) s=s+input[i]+".";
		 s=s+input[(input.length-1)];
		IDusSource = s;
	}
	public String getIDusDestination() {
		return IDusDestination;
	}
	public void setIDusDestination(String iDusDestination) {
		String[] input=iDusDestination.split("\\.");
		this.setAppName(input[0]);
		 String s=""; for(int i=1; i < (input.length-1);i++) s=s+input[i]+".";
		 s=s+input[(input.length-1)];
		IDusDestination = s;
	}
	@Override
	public String toString() {
		return "rtt *"+IDusSource+"*"+IDusDestination+"*"+Value;
	}
	
	
}
