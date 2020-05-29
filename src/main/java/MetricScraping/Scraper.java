package MetricScraping;

public abstract class Scraper{

	private boolean enable=false;
	
		
	public void scrap() throws InterruptedException{
		
	}
	

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public void saveValue() {}
	
	
	public void evaluateExression() {}
	public void cancelScraping() {}
	
	
}
