package entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dataBase.StoreData;

@XmlRootElement
public class Course {  
	
	private int id;  
	private String description;
	private String name;
	private String pictureUrl;
	private Integer teacherId;
	private String teacherName;
	
	private ArrayList<CourseSession> courseSessions;
	private ArrayList<CourseUnity> courseUnities;
	
	
	public Integer getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public ArrayList<CourseSession> getCourseSessions() {
		return courseSessions;
	}
	public void setCourseSessions(ArrayList<CourseSession> courseSessions) {
		this.courseSessions = courseSessions;
	}
	public ArrayList<CourseUnity> getCourseUnities() {
		return courseUnities;
	}
	public void setCourseUnities(ArrayList<CourseUnity> courseUnities) {
		this.courseUnities = courseUnities;
	}
	
	
	public static Course getById(int id){
		Course c = (Course)StoreData.getById(Course.class, id);
		if(c != null){
			c.setCourseSessions((ArrayList<CourseSession>) CourseSession.getByCourseId(c.getId()));
			c.setCourseUnities((ArrayList<CourseUnity>) CourseUnity.getByCourseId(c.getId()));
			
			if(c.getTeacherId() != null){
				User teacher = User.getById(c.getTeacherId());
				if(teacher != null){
					c.setTeacherName(teacher.getFirstName() + " " + teacher.getLastName());
				}
			}
		}
		return c;
	}
	
	public static List<Course> getByCategoryId(int categoryId){
		List<CourseCategory> listOfCouseCategory = CourseCategory.getByCategoryId(categoryId);	 
		List<Course> listOfCourses = new ArrayList<Course>();
		
		for (CourseCategory courseCategory : listOfCouseCategory) {
			Course course = Course.getById(courseCategory.getCourseId());
			if(course != null && course.getTeacherId() != null && course.hasActiveSession()){
				listOfCourses.add(course);
			}
		}
		
		return listOfCourses;
	}
	
	public static List<Course> getByTeacherId(int teacherId){
		return (List<Course>)StoreData.getByField(Course.class, "teacherId", String.valueOf(teacherId));
	}
	
	
	public static List<Course> getAll(){
		List<Course> courses = (List<Course>)StoreData.getByField(Course.class, "1", "1");
		List<Course> coursesFixed = new ArrayList<Course>();
		for (Course course : courses) {
			if(course.getTeacherId() != null && course.hasActiveSession()){
				coursesFixed.add(course);
			}
		}
		
		return coursesFixed;
	}
	
	public void save(){
		StoreData.save(this);
	}
	public static List<Course> search(String search) {
		List<Course> courses = (List<Course>)StoreData.getByField(Course.class, "name", search);
		List<Course> coursesFixed = new ArrayList<Course>();
		for (Course course : courses) {
			if(course.getTeacherId() != null && course.hasActiveSession()){
				coursesFixed.add(course);
			}
		}
		
		List<Category> cateogries = Category.search(search);
		for (Category category : cateogries) {
			List<Course> categoryCourses = Course.getByCategoryId(category.getId());
			//TODO: Validar si ya esta el curso en la lista
			coursesFixed.addAll(categoryCourses);
		}
		return coursesFixed;
	}
	
	public List<Category> getCategories(){
		List<Category> categories = new ArrayList<Category>();
		List<CourseCategory> listOfCouseCategory = CourseCategory.getByCourseId(this.getId());	
		
		for (CourseCategory courseCategory : listOfCouseCategory) {
			Category c = Category.getById(courseCategory.getCategoryId());
			if(c != null){
				categories.add(c);
			}
		}
		
		return categories;
	}
	public boolean hasActiveSession() {
		List<CourseSession> courseSessions = CourseSession.getByCourseId(this.getId());
		if(courseSessions != null && !courseSessions.isEmpty()){
			for (CourseSession courseSession : courseSessions) {
				if(courseSession.isActive()){
					return true;
				}
			}
		}
		return false;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}


}