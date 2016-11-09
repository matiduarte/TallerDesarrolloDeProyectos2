package entities;

import java.util.ArrayList;


public class Report {

	private String courseName;
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
	
	public static ArrayList<Report> getReportList(){
		
		ArrayList<Report> reportList = new ArrayList<Report>();
		
		Report r1 = new Report();
		r1.setCourseName("Algoritmos y Programación I");
		r1.setPass(30);
		r1.setNoPass(10);
		r1.setGiveUp(5);
		reportList.add(r1);
		
		Report r2 = new Report();
		r1.setCourseName("Algoritmos y Programación I");
		r1.setPass(35);
		r1.setNoPass(13);
		r1.setGiveUp(1);
		reportList.add(r2);
		
		Report r3 = new Report();
		r1.setCourseName("Algebra II");
		r1.setPass(25);
		r1.setNoPass(20);
		r1.setGiveUp(15);
		reportList.add(r3);
		
		Report r4 = new Report();
		r1.setCourseName("Análisis Matematico II");
		r1.setPass(27);
		r1.setNoPass(20);
		r1.setGiveUp(15);
		reportList.add(r4);
		
		Report r5 = new Report();
		r1.setCourseName("Taller De Desarrollo De Proyectos II");
		r1.setPass(40);
		r1.setNoPass(0);
		r1.setGiveUp(0);
		reportList.add(r5);
		
		Report r6 = new Report();
		r1.setCourseName("Química");
		r1.setPass(40);
		r1.setNoPass(14);
		r1.setGiveUp(8);
		reportList.add(r6);
		
		Report r7 = new Report();
		r1.setCourseName("Estructura Del Computador");
		r1.setPass(18);
		r1.setNoPass(7);
		r1.setGiveUp(3);
		reportList.add(r7);
		
		return reportList;
	}
	
	
}
