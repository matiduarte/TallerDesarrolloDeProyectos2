package entities;

import java.util.List;

import dataBase.StoreData;

public class Certification {
	private String studentName;
	private String courseName;
	private String teachertName;
	private float result;
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public float getResult() {
		return result;
	}
	public void setResult(float result) {
		this.result = result;
	}
	public String getTeachertName() {
		return teachertName;
	}
	public void setTeachertName(String teachertName) {
		this.teachertName = teachertName;
	}
	
}
