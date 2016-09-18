package entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dataBase.StoreData;

@XmlRootElement
public class CourseCategory {  
	
	private int id;  
	private int courseId;
	private int categoryId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public static CourseCategory getById(int id){
		return (CourseCategory)StoreData.getById(CourseCategory.class, id);
	}
	
	public static List<CourseCategory> getByCategoryId(int categoryId){
		return (List<CourseCategory>)StoreData.getByField(CourseCategory.class, "categoryId", String.valueOf(categoryId));
	}
	
	public static List<CourseCategory> getByCourseId(int courseId){
		return (List<CourseCategory>)StoreData.getByField(CourseCategory.class, "courseId", String.valueOf(courseId));
	}
	
	public static void deleteByCourseId(int courseId){
		List<CourseCategory> courseCategoryToDelete = (List<CourseCategory>)StoreData.getByField(CourseCategory.class, "courseId", String.valueOf(courseId));
		for (CourseCategory courseCategory : courseCategoryToDelete) {
			courseCategory.delete();
		}
	}
	
	public void save(){
		StoreData.save(this);
	}
	
	public void delete(){
		StoreData.delete(this);
	}
}