package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dataBase.StoreData;

public class CourseComment {
	
	private int id;  
	private int courseId;
	private int studentId;
	private String message;
	private Date time;
	private String studentFirstName;
	private String studentLastName;
	
	
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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

	public static List<CourseComment> getByCourseId(int courseId){
		List<CourseComment> comments =  (List<CourseComment>)StoreData.getByField(CourseComment.class, "courseId", String.valueOf(courseId));
		ArrayList<CourseComment> commentsFixed = new ArrayList<CourseComment>();
		
		for (CourseComment courseComment : comments) {
			User user = User.getById(courseComment.getStudentId());
			if(user != null){
				courseComment.setStudentFirstName(user.getFirstName());
				courseComment.setStudentLastName(user.getLastName());
				commentsFixed.add(courseComment);
			}
		}
		
		return commentsFixed;
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
}
