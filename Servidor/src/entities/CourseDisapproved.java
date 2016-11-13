package entities;

import java.util.ArrayList;
import java.util.List;

import dataBase.StoreData;

public class CourseDisapproved {

	private String courseName;
	private Long dissaproved;
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Long getDissaproved() {
		return dissaproved;
	}
	public void setDissaproved(Long dissaproved) {
		this.dissaproved = dissaproved;
	}
	
	public static List<CourseDisapproved> getCourseDisapproved(){
		
		
		String query = "SELECT c.name , count(*)"
				+ " FROM Course c, User u, CourseSession cs, StudentExam se"
				+ " WHERE cs.courseId = c.id"
				+ " AND u.isStudent = 1"
				+ " AND se.sessionId = cs.id"
				+ " AND se.isFinal = 1"
				+ " AND se.result < 4"
				+ " AND se.studentId = u.id"
				+ " GROUP BY c.name"; 

			List<Object> obj = (List<Object>) StoreData.customQuery(query);
			
			
			List<CourseDisapproved> cdList = new ArrayList<CourseDisapproved>();
			
			for (Object object : obj) {
			  Object[] result = (Object[]) object;
			  CourseDisapproved cd = new CourseDisapproved();
			  cd.setCourseName((String) result[0]);
			  cd.setDissaproved((Long) result[1]);
			  cdList.add(cd);
			}

			return cdList;
			}
}
