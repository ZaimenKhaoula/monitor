package monitoringApi;

import java.net.URL;

public class NotifierModel {
	
	private String expression;
	private URL urlToSendAlert;
	

	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public URL getUrlToSendAlert() {
		return urlToSendAlert;
	}
	public void setUrlToSendAlert(URL urlToSendAlert) {
		this.urlToSendAlert = urlToSendAlert;
	}
	
	@Override	
    public String toString() {
	return "CreateNotifier ="+expression+" url "+urlToSendAlert;
	
}
	
	
}
