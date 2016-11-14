package entities;

import java.util.ArrayList;
import java.util.List;


public class Report {

	private Integer courseId;
	private String courseName;
	private String courseCategory;
	private Integer pass;
	private Integer noPass;
	private Integer giveUp;
	
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
	
	public static ArrayList<Report> getReportList(){
		
		ArrayList<Report> reportList = new ArrayList<Report>();
		List<CourseApproved> caList = CourseApproved.getCourseApproved();
		List<CourseDisapproved> cdList = CourseDisapproved.getCourseDisapproved();
	
		//List<CourseReport> cReport = new ArrayList<CourseReport>();
		
		//TODO: Mergear listas porque rompe
		for (int i = 0; i < caList.size(); i++){
			Report r = new Report();
			r.setCategory(caList.get(i).getCategory());
			r.setCourseName(caList.get(i).getCourseName());
			r.setPass((int) (long)caList.get(i).getApproved());
			r.setNoPass((int) (long)cdList.get(i).getDissaproved());
			r.setGiveUp(0);
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
}
