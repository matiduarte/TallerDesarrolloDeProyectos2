package entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dataBase.StoreData;


public class Report {

	private Integer courseId;
	private String courseName;
	private String courseCategory;
	private Integer pass;
	private Integer noPass;
	private Integer giveUp;
	private Long all;
	private Long allSession;
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Integer getPass() {
		return pass;
	}
	public void setPass(Integer pass) {
		this.pass = pass;
	}
	public Integer getNoPass() {
		return noPass;
	}
	public void setNoPass(Integer noPass) {
		this.noPass = noPass;
	}
	public Integer getGiveUp() {
		return giveUp;
	}
	public void setGiveUp(Integer giveUp) {
		this.giveUp = giveUp;
	}
	public String getCategory() {
		return courseCategory;
	}
	public void setCategory(String category) {
		this.courseCategory = category;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	
	
	public Integer getTotalPupils() {
		return this.getPass() + this.getNoPass() + this.getGiveUp();
	}
	
	public Long getAll() {
		return all;
	}
	public void setAll(Long all) {
		this.all = all;
	}
	
	
	public Long getAllSession() {
		return allSession;
	}
	public void setAllSession(Long allSession) {
		this.allSession = allSession;
	}
	public static ArrayList<Report> getReportList(){
		
		ArrayList<Report> reportList = new ArrayList<Report>();
		List<CourseApproved> caList = CourseApproved.getCourseApproved();
		List<Report> allList = Report.getAllStudentWithExams();
		List<Report> allSessionList = Report.getAllStudentWithSession();
		
		//List<CourseReport> cReport = new ArrayList<CourseReport>();
		
		//TODO: Mergear listas porque rompe
		for (int i = 0; i < caList.size(); i++){
			Report r = new Report();
			r.setCourseId(caList.get(i).getCourseId());
			r.setCategory(caList.get(i).getCategory());
			r.setCourseName(caList.get(i).getCourseName());
			r.setPass((int) (long)caList.get(i).getApproved());
			Integer noPass = ((int)(long)allList.get(i).getAll()) - (int) (long)caList.get(i).getApproved();
			r.setNoPass(noPass);
			Integer giveUp = ((int)(long) allSessionList.get(i).getAllSession()  - ((int) (long)caList.get(i).getApproved() + noPass)  );
			r.setGiveUp(giveUp);
			reportList.add(r);
		}
		
		
		return reportList;
	}
	
	public static ArrayList<Report> PARA_TEST_getReportList(){
		
		ArrayList<Report> reportList = new ArrayList<Report>();
		
		Report r1 = new Report();
		r1.setCourseId(1);
		r1.setCourseName("Algoritmos y Programación I");
		r1.setCategory("computacion");
		r1.setPass(30);
		r1.setNoPass(10);
		r1.setGiveUp(5);
		reportList.add(r1);
		
		Report r2 = new Report();
		r2.setCourseId(2);
		r2.setCourseName("Algoritmos y Programación II");
		r2.setCategory("computacion");
		r2.setPass(35);
		r2.setNoPass(13);
		r2.setGiveUp(1);
		reportList.add(r2);
		
		Report r3 = new Report();
		r3.setCourseId(3);
		r3.setCourseName("Algebra II");
		r3.setCategory("matematicas");
		r3.setPass(25);
		r3.setNoPass(20);
		r3.setGiveUp(15);
		reportList.add(r3);
		
		Report r4 = new Report();
		r4.setCourseId(4);
		r4.setCourseName("Análisis Matematico II");
		r4.setCategory("matematicas");
		r4.setPass(27);
		r4.setNoPass(20);
		r4.setGiveUp(15);
		reportList.add(r4);
		
		Report r5 = new Report();
		r5.setCourseId(5);
		r5.setCourseName("Taller De Desarrollo De Proyectos II");
		r5.setCategory("computacion");
		r5.setPass(40);
		r5.setNoPass(0);
		r5.setGiveUp(0);
		reportList.add(r5);
		
		Report r6 = new Report();
		r6.setCourseId(6);
		r6.setCourseName("Química");
		r6.setCategory("quimica");
		r6.setPass(40);
		r6.setNoPass(14);
		r6.setGiveUp(8);
		reportList.add(r6);
		
		Report r7 = new Report();
		r7.setCourseId(7);
		r7.setCourseName("Estructura Del Computador");
		r7.setCategory("computacion");
		r7.setPass(18);
		r7.setNoPass(7);
		r7.setGiveUp(3);
		reportList.add(r7);
		
		return reportList;
	}
	
	public static List<Report> getAllStudentWithExams(){
		
		String query = "SELECT cat.name, c.id, c.name, count(*) FROM StudentSession ss, CourseSession cs, Course c,"
				+ " Category cat, CourseCategory cc"
				+ " WHERE ss.studentId IN (SELECT se.studentId FROM StudentExam se"
				+ " WHERE se.sessionId = ss.sessionId)"
				+ " AND ss.sessionId = cs.id"
				+ " AND cs.courseId = c.id"
				+ " AND cc.courseId = c.id"
				+ " AND cc.categoryId = cat.id"
				+ " GROUP BY cat.name, c.id, c.name";

			List<Object> obj = (List<Object>) StoreData.customQuery(query);
			
			
			List<Report> clList = new ArrayList<Report>();
			
			for (Object object : obj) {
			  Object[] result = (Object[]) object;
			  Report cl = new Report();
			  cl.setCategory((String) result[0]);
			  cl.setCourseId((Integer) result[1]);
			  cl.setCourseName((String) result[2]);
			  cl.setAll((Long) result[3]);
			  clList.add(cl);
			}

			return clList;
	}
	
public static List<Report> getAllStudentWithSession(){
		
		String query = "SELECT cat.name, c.id, c.name, count(*) FROM StudentSession ss, CourseSession cs, Course c,"
				+ " Category cat, CourseCategory cc"
				+ " WHERE ss.sessionId = cs.id"
				+ " AND cs.courseId = c.id"
				+ " AND cc.courseId = c.id"
				+ " AND cc.categoryId = cat.id"
				+ " GROUP BY cat.name, c.id, c.name";

			List<Object> obj = (List<Object>) StoreData.customQuery(query);
			
			
			List<Report> clList = new ArrayList<Report>();
			
			for (Object object : obj) {
			  Object[] result = (Object[]) object;
			  Report cl = new Report();
			  cl.setCategory((String) result[0]);
			  cl.setCourseId((Integer) result[1]);
			  cl.setCourseName((String) result[2]);
			  cl.setAllSession((Long) result[3]);
			  clList.add(cl);
			}

			return clList;
	}

public static ArrayList<Report> getReportList(Date desde, Date hasta) {
	
	ArrayList<Report> reportList = new ArrayList<Report>();
	List<CourseApproved> caList = CourseApproved.getCourseApproved( desde, hasta );
	List<Report> allList = Report.getAllStudentWithExams( desde, hasta );
	List<Report> allSessionList = Report.getAllStudentWithSession( desde, hasta );
	
	//List<CourseReport> cReport = new ArrayList<CourseReport>();
	
	//TODO: Mergear listas porque rompe
	for (int i = 0; i < caList.size(); i++){
		Report r = new Report();
		r.setCourseId(caList.get(i).getCourseId());
		r.setCategory(caList.get(i).getCategory());
		r.setCourseName(caList.get(i).getCourseName());
		r.setPass((int) (long)caList.get(i).getApproved());
		Integer noPass = ((int)(long)allList.get(i).getAll()) - (int) (long)caList.get(i).getApproved();
		r.setNoPass(noPass);
		Integer giveUp = ((int)(long) allSessionList.get(i).getAllSession()  - ((int) (long)caList.get(i).getApproved() + noPass)  );
		r.setGiveUp(giveUp);
		reportList.add(r);
	}
	
	
	return reportList;
	}

public static List<Report> getAllStudentWithExams(Date desde, Date hasta){
	
	DateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
	
	String query = "SELECT cat.name, c.id, c.name, count(*) FROM StudentSession ss, CourseSession cs, Course c,"
			+ " Category cat, CourseCategory cc"
			+ " WHERE ss.studentId IN (SELECT se.studentId FROM StudentExam se"
			+ " WHERE se.sessionId = ss.sessionId)"
			+ " AND ss.sessionId = cs.id"
			+ " AND cs.courseId = c.id"
			+ " AND cc.courseId = c.id"
			+ " AND cc.categoryId = cat.id"
			+ " AND cs.date >= '" + formato.format(desde) + "'"
			+ " AND cs.date <= '" + formato.format(hasta) + "'"
			+ " GROUP BY cat.name, c.id, c.name";

		List<Object> obj = (List<Object>) StoreData.customQuery(query);
		
		
		List<Report> clList = new ArrayList<Report>();
		
		for (Object object : obj) {
		  Object[] result = (Object[]) object;
		  Report cl = new Report();
		  cl.setCategory((String) result[0]);
		  cl.setCourseId((Integer) result[1]);
		  cl.setCourseName((String) result[2]);
		  cl.setAll((Long) result[3]);
		  clList.add(cl);
		}

		return clList;
}

public static List<Report> getAllStudentWithSession(Date desde, Date hasta){
	
	DateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
	
	String query = "SELECT cat.name, c.id, c.name, count(*) FROM StudentSession ss, CourseSession cs, Course c,"
			+ " Category cat, CourseCategory cc"
			+ " WHERE ss.sessionId = cs.id"
			+ " AND cs.courseId = c.id"
			+ " AND cc.courseId = c.id"
			+ " AND cc.categoryId = cat.id"
			+ " AND cs.date >= '" + formato.format(desde) + "'"
			+ " AND cs.date <= '" + formato.format(hasta) + "'"
			+ " GROUP BY cat.name, c.id, c.name";

		List<Object> obj = (List<Object>) StoreData.customQuery(query);
		
		
		List<Report> clList = new ArrayList<Report>();
		
		for (Object object : obj) {
		  Object[] result = (Object[]) object;
		  Report cl = new Report();
		  cl.setCategory((String) result[0]);
		  cl.setCourseId((Integer) result[1]);
		  cl.setCourseName((String) result[2]);
		  cl.setAllSession((Long) result[3]);
		  clList.add(cl);
		}

		return clList;
}

}
