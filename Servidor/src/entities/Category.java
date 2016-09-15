package entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dataBase.StoreData;

@XmlRootElement
public class Category {  
	
	private int id;  
	private String name;
	private List<Course> courses;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public static Category getById(int id){
		return (Category)StoreData.getById(Category.class, id);
	}
	
	public static List<Category> getAll(){
		return (List<Category>)StoreData.getByField(Category.class, "1", "1");
	}
	
	public static List<Category> getAllWithCourses(){
		List<Category> categories = (List<Category>)StoreData.getByField(Category.class, "1", "1");
		List<Category> result = new ArrayList<Category>();
		for (Category category : categories) {
			List<Course> courses = Course.getByCategoryId(category.getId());
			category.setCourses(courses);
			result.add(category);
		}
		
		return result;
	}
	
	public static List<Category> search(String search) {
		return (List<Category>)StoreData.getByField(Category.class, "name", search);
	}
	
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
}