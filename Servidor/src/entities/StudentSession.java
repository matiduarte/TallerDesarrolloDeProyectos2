package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dataBase.StoreData;

@XmlRootElement
public class StudentSession {  
	
	private int id;  
	private int sessionId;
	private int studentId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public static StudentSession getById(int id){
		return (StudentSession)StoreData.getById(StudentSession.class, id);
	}
	
	public static List<StudentSession> getByStudentId(int studentId){
		return (List<StudentSession>)StoreData.getByField(StudentSession.class, "studentId", String.valueOf(studentId));
	}
	
	public static List<Course> getCursesSubscribed(int studentId){
		List<StudentSession> studentSessions = StudentSession.getByStudentId(studentId);
		
		List<Course> courses = new ArrayList<Course>();
		
		for (StudentSession studentSession : studentSessions) {
			CourseSession courseSession = CourseSession.getById(studentSession.getSessionId());
			if(courseSession != null){
				Course course = Course.getById(courseSession.getCourseId(), studentId);
				if(course != null){
					courses.add(course);
				}
			}
		}
		
		return courses;
	}
	
	public static StudentSession getByStudentIdAndSessionId(int studentId, int sessionId){
		List<StudentSession> studentSessions = (List<StudentSession>)StoreData.getByTwoFields(StudentSession.class, "studentId", String.valueOf(studentId), "sessionId", String.valueOf(sessionId));
		if(studentSessions != null && !studentSessions.isEmpty()){
			return studentSessions.get(0);
		}
		return null;
	}
	
	public void save(){
		StoreData.save(this);
	}
	
	public void delete(){
		StoreData.delete(this);
	}

	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
}