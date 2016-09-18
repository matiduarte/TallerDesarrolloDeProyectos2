package utils;

import java.util.List;

import entities.Category;
import entities.Course;
import entities.User;

public class TableCourse {
	
	Course curso;
	List<User> docentes;
	List<Category> categorias;
	
	public TableCourse() {}
	
	public TableCourse( Course curso, List<User> docentes, List<Category> categorias ) {
		this.curso = curso;
		this.docentes = docentes;
		this.categorias = categorias;
	}
	
	public void setCurso( Course curso ) {
		this.curso = curso;
	}

	public void setDocentes( List<User> docentes ) {
		this.docentes = docentes;
	}
	
	public void setCategorias( List<Category> categorias ) {
		this.categorias = categorias;
	}
	
}
