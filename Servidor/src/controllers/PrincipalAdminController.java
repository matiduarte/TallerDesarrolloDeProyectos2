package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataBase.StoreData;
import entities.Category;
import entities.Course;
import entities.CourseCategory;
import entities.User;
import utils.TableCourse;

/**
 * Servlet implementation class PrincipalAdminController
 */
@WebServlet("/cursos/admin")
public class PrincipalAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrincipalAdminController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
    	       getServletConfig().getServletContext().getRequestDispatcher("/principalAdmin.jsp").forward(request,response);
    	    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	
    	System.out.print( "nombre:" + request.getParameter("nombre") );
    	System.out.print( "categoria:" + request.getParameter("categoria") );
    	System.out.print( "docente:" + request.getParameter("docente") );
    	
    	
    	ArrayList<TableCourse> cursos_de_tabla = new ArrayList<TableCourse>();
    	String nombreBuscado, categoriaBuscada, docenteBuscado;
    	if((request.getParameter("nombre") != null && request.getParameter("nombre") != "")
    			|| (request.getParameter("categoria") != null && request.getParameter("categoria") != "")
    			|| (request.getParameter("docente") != null && request.getParameter("docente") != "")){
    		List<Course> listOfCourses = new ArrayList<Course>();
    		if(request.getParameter("nombre") != null && request.getParameter("nombre") != ""){
    			nombreBuscado = request.getParameter("nombre");
        		listOfCourses = (List<Course>)StoreData.getByField(Course.class, "name", nombreBuscado);
        		if(request.getParameter("categoria") != null && request.getParameter("categoria") != ""){
        			categoriaBuscada = request.getParameter("categoria");
        			for (Course courseByName : listOfCourses) {
        				List<Category> courseCategories = courseByName.getCategories();
        				for (Category courseCategory : courseCategories) {
        					if(courseCategory.getName() != categoriaBuscada){
        						listOfCourses.remove(courseByName);
        					}
            			}
        			} 
        		}
    			if(request.getParameter("docente") != null && request.getParameter("docente") != ""){
    				docenteBuscado = request.getParameter("docente");
    				for (Course course : listOfCourses) {
    					if(User.getById(course.getTeacherId()).getLastName() != docenteBuscado){
    						listOfCourses.remove(course);
    					}
        			}
    			}
    		}
    		if(request.getParameter("categoria") != null && request.getParameter("categoria") != ""){
    			categoriaBuscada = request.getParameter("categoria");
    			List<Category> cateogries = Category.search(categoriaBuscada);
    			for (Category category : cateogries) {
    				List<Course> categoryCourses = Course.getByCategoryId(category.getId());
    				//TODO: Validar si ya esta el curso en la lista
    				listOfCourses.addAll(categoryCourses);
    			}
    			if(request.getParameter("docente") != null && request.getParameter("docente") != ""){
    				docenteBuscado = request.getParameter("docente");
    				for (Course course : listOfCourses) {
    					if(User.getById(course.getTeacherId()).getLastName() != docenteBuscado){
    						listOfCourses.remove(course);
    					}
        			}
    			}
    		}
			if(request.getParameter("docente") != null && request.getParameter("docente") != ""){
				docenteBuscado = request.getParameter("docente");
				User docente = User.getByLastName(docenteBuscado);
				listOfCourses = Course.getByTeacherId(docente.getId());
			}
			

			for ( Course curso : listOfCourses ) {
        		List<Category> categorias = curso.getCategories();
        		//@Todo: ver mejor solucion para cuando no tiene docente asignado
        		User docente = new User();
        		docente.setFirstName("");
        		docente.setLastName("");
        		if (curso.getTeacherId() != null){
            		docente = User.getById( curso.getTeacherId() );
        		}
        		TableCourse curso_de_tabla = new TableCourse( curso, docente, categorias );
        		cursos_de_tabla.add( curso_de_tabla );
        	}
			
    	} else {
        	for ( Course curso : Course.getAll() ) {
        		List<Category> categorias = curso.getCategories();
        		//@Todo: ver mejor solucion para cuando no tiene docente asignado
        		User docente = new User();
        		docente.setFirstName("");
        		docente.setLastName("");
        		if (curso.getTeacherId() != null){
            		docente = User.getById( curso.getTeacherId() );
        		}
        		TableCourse curso_de_tabla = new TableCourse( curso, docente, categorias );
        		cursos_de_tabla.add( curso_de_tabla );
        	}
    	}

    	request.setAttribute("table_courses", cursos_de_tabla);
    	
        processRequest(request, response);
    } 
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	   	
    	Integer id_curso = Integer.parseInt( request.getParameter("id") );
    	    	
    	Course curso_a_eliminar = Course.getById( id_curso );
    	
    	if ( false == curso_a_eliminar.hasStudents() && false == curso_a_eliminar.hasStarted() ) {
    		request.setAttribute("borrado", "true");
    		curso_a_eliminar.delete();
    		CourseCategory.deleteByCourseId( id_curso );
    	}
    	
    }

}
