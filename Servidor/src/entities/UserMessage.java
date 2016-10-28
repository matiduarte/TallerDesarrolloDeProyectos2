package entities;

public class UserMessage {

	private String name;
	private String message;
	private boolean isModerate;
	
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
	
	
}
