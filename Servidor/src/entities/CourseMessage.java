package entities;

import java.util.Date;
import java.util.List;

import dataBase.StoreData;

public class CourseMessage {
	
	private int id;  
	private int sessionId;
	private int studentId;
	private String message;
	private boolean isModerate;
	private Date time;
	private String studentFirstName;
	private String studentLastName;
	
	
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
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public void save(){
		StoreData.save(this);
	}

	public static List<CourseMessage> getBySessionId(int sessionId){
		return (List<CourseMessage>)StoreData.getByField(CourseMessage.class, "sessionId", String.valueOf(sessionId));
	}
	public String getStudentFirstName() {
		return studentFirstName;
	}
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	public String getStudentLastName() {
		return studentLastName;
	}
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
}
