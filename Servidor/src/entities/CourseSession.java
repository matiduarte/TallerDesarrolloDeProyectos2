package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dataBase.StoreData;

@XmlRootElement
public class CourseSession {  
	
	private int id;  
	private int courseId;
	private String date;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public static CourseSession getById(int id){
		return (CourseSession)StoreData.getById(CourseSession.class, id);
	}
	
	public static List<CourseSession> getByCourseId(int courseId){
		return (List<CourseSession>)StoreData.getByField(CourseSession.class, "courseId", String.valueOf(courseId));
	}
	
	public void save(){
		StoreData.save(this);
	}
	
	public void delete(){
		StoreData.delete(this);
	}
	public boolean isActive() {
		String date = this.getDate();
		if(date != null){
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

		    Date date1;
			try {
				date1 = format.parse(date);
			} catch (ParseException e) {
				return false;
			}
		    Date currenDate = new Date();
		    
		    if (date1.compareTo(currenDate) <= 0) {
		    	return true;
		    }
		}
		return false;
	}
}