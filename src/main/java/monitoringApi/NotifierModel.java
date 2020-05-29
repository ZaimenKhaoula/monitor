package monitoringApi;

import java.net.URL;

public class NotifierModel {

	private String notifierId;
	private String expression;
	private URL urlToSendAlert;
	
	
	public String getNotifierId() {
		return notifierId;
	}
	public void setNotifierId(String notifierId) {
		this.notifierId = notifierId;
	}
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
	return "CreateNotifier"+notifierId+"="+expression+" url "+urlToSendAlert;
	
}
	
	
}
