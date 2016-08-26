package service;

public class ServiceResponse {
	private boolean success = false;
	private String message = "";
	private String data = "";

	public ServiceResponse(boolean success, String message, String dataJson){
		this.success = success;
		this.message = message;
		this.data = dataJson;
	}
	
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}		
}
