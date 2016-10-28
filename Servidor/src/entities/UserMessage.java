package entities;

import java.util.Date;

public class UserMessage {

	private String name;
	private String message;
	private boolean isModerate;
	private Date time;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean getIsModerate() {
		return isModerate;
	}
	public void setIsModerate(boolean isModerate) {
		this.isModerate = isModerate;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	
	
}
