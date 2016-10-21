package entities;

import dataBase.StoreData;

public class CourseMessage {
	
	private int id;  
	private int sessionId;
	private int studentId;
	private String message;
	private boolean isModerate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
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
	
	public void save(){
		StoreData.save(this);
	}

}
