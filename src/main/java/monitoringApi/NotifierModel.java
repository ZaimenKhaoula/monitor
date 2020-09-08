package monitoringApi;

import java.net.URL;

public class NotifierModel {
	
	private String expression;
	private String urlToSendAlert;
	

	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getUrlToSendAlert() {
		return urlToSendAlert;
	}
	public void setUrlToSendAlert(String urlToSendAlert) {
		this.urlToSendAlert = urlToSendAlert;
	}
	
	@Override	
    public String toString() {
	return "CreateNotifier ="+expression+" url "+urlToSendAlert;
	
}
	
	
}
