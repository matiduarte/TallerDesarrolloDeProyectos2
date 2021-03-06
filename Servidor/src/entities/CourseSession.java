package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

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
	
	public boolean startsToday() {
		String date = this.getDate();
		if(date != null){
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		    Date date1;
			try {
				date1 = format.parse(date);
			} catch (ParseException e) {
				return false;
			}
		    Date currenDate = new Date();
		    
		    
		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    Calendar c = Calendar.getInstance();
		    
		    c.setTime(currenDate);
		    
		    c.add(Calendar.DATE, -1);  // number of days to add
		    Date yesterdayDate = c.getTime();

		    if (date1.compareTo(currenDate) <= 0 && date1.compareTo(yesterdayDate) >= 0) {
		    	return true;
		    }
		}
		return false;
	}
	
	public boolean isFinalExamAvailable(int unitiesSize) {
		String date = this.getDate();
		if(date != null){
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		    Date date1;
			try {
				date1 = format.parse(date);
			} catch (ParseException e) {
				return false;
			}
		    
			Date currenDate = new Date();
		    
		    
		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    Calendar c = Calendar.getInstance();
		    
		    c.setTime(date1);
		    
		    c.add(Calendar.DATE, 7*unitiesSize);  // number of days to add
		    Date beginFinalExamDate = c.getTime();
		    
		    c.add(Calendar.DATE, 7);
		    Date endFinalExamDate = c.getTime();
		    
		    
		    if (currenDate.compareTo(beginFinalExamDate) >= 0 && currenDate.compareTo(endFinalExamDate) <= 0) {
		    	return true;
		    }
		}
		return false;
	}
}