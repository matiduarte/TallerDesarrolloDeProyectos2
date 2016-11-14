package entities;

import java.util.ArrayList;
import java.util.List;

import dataBase.StoreData;

public class CourseDisapproved extends CourseReport {

	private Long dissaproved;
	
	public Long getDissaproved() {
		return dissaproved;
	}
	public void setDissaproved(Long dissaproved) {
		this.dissaproved = dissaproved;
	}
	
	public static List<CourseDisapproved> getCourseDisapproved(){
		
		
		String query = "SELECT cat.name, c.name , count(*)"
				+ " FROM Course c, User u, CourseSession cs, StudentExam se, Category cat, CourseCategory cc"
				+ " WHERE cs.courseId = c.id"
				+ " AND u.isStudent = 1"
				+ " AND se.sessionId = cs.id"
				+ " AND se.isFinal = 1"
				+ " AND se.result < 4"
				+ " AND se.studentId = u.id"
				+ " AND cc.courseId = c.id"
				+ " AND cc.categoryId = cat.id" 
				+ " GROUP BY cat.name, c.name"; 

			List<Object> obj = (List<Object>) StoreData.customQuery(query);
			
			
			List<CourseDisapproved> cdList = new ArrayList<CourseDisapproved>();
			
			for (Object object : obj) {
			  Object[] result = (Object[]) object;
			  CourseDisapproved cd = new CourseDisapproved();
			  cd.setCategory((String) result[0]);
			  cd.setCourseName((String) result[1]);
			  cd.setDissaproved((Long) result[2]);
			  cdList.add(cd);
			}

			return cdList;
			}
}
