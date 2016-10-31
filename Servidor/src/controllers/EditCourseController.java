package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import entities.Category;
import entities.Course;
import entities.CourseCategory;
import entities.CourseSession;
import entities.CourseUnity;
import entities.User;
import utils.FileUtil;


@WebServlet("/editCourse")
public class EditCourseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCourseController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	getServletConfig().getServletContext().getRequestDispatcher("/editCourse.jsp").forward(request,response);
    	return;
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ArrayList<Category> allCategories = (ArrayList<Category>) Category.getAll();
        request.setAttribute("categories", allCategories);
        request.setAttribute("teachers", (ArrayList<User>)User.getAllPossibleTeachers());
        
        if(request.getParameter("id") != null){
        	 int courseId = Integer.valueOf(request.getParameter("id")); 
             Course course = Course.getById(courseId);
             ArrayList<Category> currentCategories = (ArrayList<Category>) course.getCategories();
             
             String currentTeacherName = "";
             if(course.getTeacherId() != null){
             	User teacher = User.getById(course.getTeacherId());     
                 if(teacher != null){
                 	currentTeacherName = teacher.getFirstName() + " " + teacher.getLastName();
                 }
             }
             
             request.setAttribute("course", course);
             request.setAttribute("currentCategories", currentCategories);
             request.setAttribute("currentTeacherName", currentTeacherName);
             
        	 List<CourseUnity> courseUnities = CourseUnity.getByCourseId(courseId);
        	 if(courseUnities != null){
        		 request.setAttribute("courseUnities", courseUnities);
        	 }
        	 
        	 List<CourseSession> courseSessions = CourseSession.getByCourseId(courseId);
        	 if(courseSessions != null){
        		 request.setAttribute("courseSessions", courseSessions);
        	 }             
             
        }else{
        	request.setAttribute("course", new Course());
            request.setAttribute("currentCategories", new ArrayList<Category>());
            request.setAttribute("currentTeacherName", "");
        }
        
        
        processRequest(request, response);
    } 
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	
    	String name = request.getParameter("name");
    	String description = request.getParameter("description");
    	String categoryIdsEntry = request.getParameter("categories");
    	String[] categoryIds = categoryIdsEntry.split(",");
    	
    	
    	if(request.getParameter("id") != null){
    		int courseId = Integer.valueOf(request.getParameter("id"));
	    	Course course = Course.getById(courseId);
	    	course.setName(name);
	    	course.setDescription(description);
	    	boolean error = false;
	    	if(!(request.getParameter("teacherSelectedId").compareTo("null") == 0)){
	    		int teacherId = Integer.valueOf(request.getParameter("teacherSelectedId"));
	    		if(course.hasActiveSession() && course.getTeacherId() != teacherId){
	    			error = true;
	    		}else{
	    			course.setTeacherId(teacherId);
	    		}
	    	}else{
	    		if(course.getTeacherId() != null){
	    			error = true;
	    		}
	    	}
	    	if(!error){
	    		course.save();
	    	
		    	//Borro todas las categorias del curso
		    	CourseCategory.deleteByCourseId(course.getId());
		    	
		    	for (String categoryId : categoryIds) {
					CourseCategory courseCategory = new CourseCategory();
					courseCategory.setCategoryId(Integer.parseInt(categoryId));
					courseCategory.setCourseId(course.getId());
					courseCategory.save();
				}
		    	
		    	 // Create path components to save the file
		        final String path = "WebContent/Files/Course/" + course.getId() + "/";
		        final String urlPath = "Files/Course/" + course.getId() + "/";
		        final Part filePart = request.getPart("picture");
		        final String fileName = FileUtil.getFileName(filePart);
		        if(!(fileName.compareTo("") == 0)){
		        	FileUtil.saveFile(path, filePart, fileName);
		            
		            course.setPictureUrl(urlPath + fileName);
		            course.save();
		        }
		    	
		        request.setAttribute("saveSucces", true);
	    	}else{
	    		request.setAttribute("saveErrorTeacher", "No se puedecambiar/eliminar el docente ders un cuo con sesion activa!");
	    	}
	    	
	    	
    	}
		doGet(request,response);
    }

}