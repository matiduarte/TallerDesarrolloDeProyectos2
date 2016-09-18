package utils;

import java.util.ArrayList;
import java.util.List;

import entities.Category;
import entities.Course;
import entities.User;

public class TableCourse {
	
	Course course;
	User teacher;
	List<Category> categories;
	
	public TableCourse() {}
	
	public TableCourse( Course course, User teacher, List<Category> categories ) {
		this.course = course;
		this.teacher = teacher;
		this.categories = categories;
	}
	
	public void setCourse( Course course ) {
		this.course = course;
	}

	public void setTeacher( User teacher ) {
		this.teacher = teacher;
	}
	
	public void setCategories( List<Category> categories ) {
		this.categories = categories;
	}
	
	public Course getCourse() {
		return this.course;
	}
	
	public User getTeacher() {
		return this.teacher;
	}
	
	public List<Category> getCategories() {
		return this.categories;
	}
	
	public List<String> getCategoriesNames() {
		List<String> names = new ArrayList<String>();
		for ( Category category : this.categories ) {
			names.add( category.getName() );
		}
		return names;
	}
}
