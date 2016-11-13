package entities;

import java.util.ArrayList;
import java.util.List;


import dataBase.StoreData;

public class CourseApproved {

	private String courseName;
	private Long approved;
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Long getApproved() {
		return approved;
	}
	public void setApproved(Long approved) {
		this.approved = approved;
	}
	
	public static List<CourseApproved> getCourseApproved(){
		
		
		String query = "SELECT c.name , count(*)"
				+ " FROM Course c, User u, CourseSession cs, StudentExam se"
				+ " WHERE cs.courseId = c.id"
				+ " AND u.isStudent = 1"
				+ " AND se.sessionId = cs.id"
				+ " AND se.isFinal = 1"
				+ " AND se.result >= 4"
				+ " AND se.studentId = u.id"
				+ " GROUP BY c.name"; 

			List<Object> obj = (List<Object>) StoreData.customQuery(query);
			
			
			List<CourseApproved> caList = new ArrayList<CourseApproved>();
			
			for (Object object : obj) {
			  Object[] result = (Object[]) object;
			  CourseApproved ca = new CourseApproved();
			  ca.setCourseName((String) result[0]);
			  ca.setApproved((Long) result[1]);
			  caList.add(ca);
			}

			return caList;
		
	}
	
}
