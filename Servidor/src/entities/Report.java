package entities;

import java.util.ArrayList;
import java.util.List;


public class Report {

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
		return courseName;
	}
	public void setCategory(String category) {
		this.courseCategory = category;
	}	
	
	public static ArrayList<Report> getReportList(){
		
		ArrayList<Report> reportList = new ArrayList<Report>();
		List<CourseApproved> caList = CourseApproved.getCourseApproved();
		List<CourseDisapproved> cdList = CourseDisapproved.getCourseDisapproved();
		
		for (int i = 0; i < caList.size(); i++){
			Report r = new Report();
			r.setCourseName(caList.get(i).getCourseName());
			r.setPass((int) (long)caList.get(i).getApproved());
			r.setNoPass((int) (long)cdList.get(i).getDissaproved());
			reportList.add(r);
		}
		
		
		return reportList;
	}
	
	
}
