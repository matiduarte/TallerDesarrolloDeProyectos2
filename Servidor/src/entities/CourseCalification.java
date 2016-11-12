package entities;

import java.util.Date;
import java.util.List;

import dataBase.StoreData;

public class CourseCalification {
	
	private int id;  
	private int courseId;
	private int studentId;
	private Date time;
	private String studentFirstName;
	private String studentLastName;
	private int calification;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
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

	public static List<CourseCalification> getByCourseId(int courseId){
		return (List<CourseCalification>)StoreData.getByField(CourseCalification.class, "courseId", String.valueOf(courseId));
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
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getCalification() {
		return calification;
	}
	public void setCalification(int calification) {
		this.calification = calification;
	}
}
