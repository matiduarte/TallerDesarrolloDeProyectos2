package entities;

import java.util.List;

import dataBase.StoreData;

public class Certification {
	private String studentName;
	private String courseName;
	private String teachertName;
	private float result;
	private int studentId;
	private int id;
	
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
	
	public void save(){
		StoreData.save(this);
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public static List<Certification> getByStudentId(int studentId){
		return (List<Certification>)StoreData.getByField(Certification.class, "studentId", String.valueOf(studentId));
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
