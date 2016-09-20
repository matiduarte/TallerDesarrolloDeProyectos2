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
	private int teacherId;
	
	
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
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
	
	public static Course getById(int id){
		return (Course)StoreData.getById(Course.class, id);
	}
	
	public static List<Course> getByCategoryId(int categoryId){
		List<CourseCategory> listOfCouseCategory = CourseCategory.getByCategoryId(categoryId);	 
		List<Course> listOfCourses = new ArrayList<Course>();
		
		for (CourseCategory courseCategory : listOfCouseCategory) {
			Course course = Course.getById(courseCategory.getCourseId());
			if(course != null){
				listOfCourses.add(course);
			}
		}
		
		return listOfCourses;
	}
	
	public static List<Course> getByTeacherId(int teacherId){
		return (List<Course>)StoreData.getByField(Course.class, "teacherId", String.valueOf(teacherId));
	}
	
	
	public static List<Course> getAll(){
		return (List<Course>)StoreData.getByField(Course.class, "1", "1");
	}
	
	public void save(){
		StoreData.save(this);
	}
	public static List<Course> search(String search) {
		List<Course> courses = (List<Course>)StoreData.getByField(Course.class, "name", search);
		List<Category> cateogries = Category.search(search);
		for (Category category : cateogries) {
			List<Course> categoryCourses = Course.getByCategoryId(category.getId());
			//TODO: Validar si ya esta el curso en la lista
			courses.addAll(categoryCourses);
		}
		return courses;
	}


}