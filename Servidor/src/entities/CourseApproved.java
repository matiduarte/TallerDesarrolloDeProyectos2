package entities;

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
		
		
		String query = "SELECT cat.name, c.name , count(*)"
				+ " FROM Course c, User u, CourseSession cs, StudentExam se, Category cat, CourseCategory cc"
				+ " WHERE cs.courseId = c.id"
				+ " AND u.isStudent = 1"
				+ " AND se.sessionId = cs.id"
				+ " AND se.isFinal = 1"
				+ " AND se.result >= 4"
				+ " AND se.studentId = u.id"
				+ " AND cc.courseId = c.id"
				+ " AND cc.categoryId = cat.id" 
				+ " GROUP BY cat.name, c.name"; 

			List<Object> obj = (List<Object>) StoreData.customQuery(query);
			
			
			List<CourseApproved> caList = new ArrayList<CourseApproved>();
			
			for (Object object : obj) {
			  Object[] result = (Object[]) object;
			  CourseApproved ca = new CourseApproved();
			  ca.setCategory((String) result[0]);
			  ca.setCourseName((String) result[1]);
			  ca.setApproved((Long) result[2]);
			  caList.add(ca);
			}

			return caList;
		
	}
	
}
