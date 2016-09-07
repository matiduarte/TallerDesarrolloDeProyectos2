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
	
	public void save(){
		StoreData.save(this);
	}
}