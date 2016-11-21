package entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import dataBase.StoreData;

public class CourseApproved extends CourseReport {

	
	private Long approved;
	
	public Long getApproved() {
		return approved;
	}
	public void setApproved(Long approved) {
		this.approved = approved;
	}

	public static List<CourseApproved> getCourseApproved(){
		
		
		String query = "SELECT cat.name, c.id, c.name , count(*)"
				+ " FROM Course c, User u, CourseSession cs, StudentExam se, Category cat, CourseCategory cc"
				+ " WHERE cs.courseId = c.id"
				+ " AND u.isStudent = 1"
				+ " AND se.sessionId = cs.id"
				+ " AND se.isFinal = 1"
				+ " AND se.result >= 6"
				+ " AND se.studentId = u.id"
				+ " AND cc.courseId = c.id"
				+ " AND cc.categoryId = cat.id" 
				+ " GROUP BY cat.name, c.id, c.name"; 

			List<Object> obj = (List<Object>) StoreData.customQuery(query);
			
			
			List<CourseApproved> caList = new ArrayList<CourseApproved>();
			
			for (Object object : obj) {
			  Object[] result = (Object[]) object;
			  CourseApproved ca = new CourseApproved();
			  ca.setCategory((String) result[0]);
			  ca.setCourseId((Integer) result[1]);
			  ca.setCourseName((String) result[2]);
			  ca.setApproved((Long) result[3]);
			  caList.add(ca);
			}

			return caList;
		
	}
	
	public static List<CourseApproved> getCourseApproved(Date desde, Date hasta){
		
		DateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
		
		String query = "SELECT cat.name, c.id, c.name , count(*)"
				+ " FROM Course c, User u, CourseSession cs, StudentExam se, Category cat, CourseCategory cc"
				+ " WHERE cs.courseId = c.id"
				+ " AND u.isStudent = 1"
				+ " AND se.sessionId = cs.id"
				+ " AND se.isFinal = 1"
				+ " AND se.result >= 6"
				+ " AND se.studentId = u.id"
				+ " AND cc.courseId = c.id"
				+ " AND cc.categoryId = cat.id"
				+ " AND cs.date >= '" + formato.format(desde) + "'"
				+ " AND cs.date <= '" + formato.format(hasta) + "'"		
				+ " GROUP BY cat.name, c.id, c.name"; 

			List<Object> obj = (List<Object>) StoreData.customQuery(query);
			
			
			List<CourseApproved> caList = new ArrayList<CourseApproved>();
			
			for (Object object : obj) {
			  Object[] result = (Object[]) object;
			  CourseApproved ca = new CourseApproved();
			  ca.setCategory((String) result[0]);
			  ca.setCourseId((Integer) result[1]);
			  ca.setCourseName((String) result[2]);
			  ca.setApproved((Long) result[3]);
			  caList.add(ca);
			}

			return caList;
		
	}	
	
}
