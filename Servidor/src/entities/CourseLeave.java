package entities;

import java.util.ArrayList;
import java.util.List;

import dataBase.StoreData;

public class CourseLeave extends CourseReport {

private Long leave;
	
	public Long getLeave() {
		return leave;
	}
	public void setLeave(Long leave) {
		this.leave = leave;
	}

	public static List<CourseLeave> getCourseLeave(){
		
		
		String query = "SELECT cat.name, c.id, c.name, count(*) FROM StudentSession ss, CourseSession cs, Course c,"
				+ " Category cat, CourseCategory cc"
				+ " WHERE ss.studentId NOT IN (SELECT se.studentId FROM StudentExam se"
				+ " WHERE se.sessionId = ss.sessionId)"
				+ " AND ss.sessionId = cs.id"
				+ " AND cs.courseId = c.id"
				+ " AND cc.courseId = c.id"
				+ " AND cc.categoryId = cat.id"
				+ " GROUP BY cat.name, c.id, c.name";

			List<Object> obj = (List<Object>) StoreData.customQuery(query);
			
			
			List<CourseLeave> clList = new ArrayList<CourseLeave>();
			
			for (Object object : obj) {
			  Object[] result = (Object[]) object;
			  CourseLeave cl = new CourseLeave();
			  cl.setCategory((String) result[0]);
			  cl.setCourseId((Integer) result[1]);
			  cl.setCourseName((String) result[2]);
			  cl.setLeave((Long) result[3]);
			  clList.add(cl);
			}

			return clList;
		
	}
	
}
