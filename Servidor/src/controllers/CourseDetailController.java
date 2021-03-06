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


@WebServlet("/courseDetail")
public class CourseDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	getServletConfig().getServletContext().getRequestDispatcher("/courseDetail.jsp").forward(request,response);
    	return;
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        if(request.getParameter("id") != null){
        	 int courseId = Integer.valueOf(request.getParameter("id")); 
             Course course = Course.getById(courseId);
             
             if(course != null){
            	 request.setAttribute("course", course);
            	 List<CourseUnity> courseUnities = CourseUnity.getByCourseId(courseId);
            	 if(courseUnities != null){
            		 request.setAttribute("courseUnities", courseUnities);
            	 }
            	 
            	 List<CourseSession> courseSessions = CourseSession.getByCourseId(courseId);
            	 if(courseSessions != null){
            		 request.setAttribute("courseSessions", courseSessions);
            	 }
            	 
            	 ArrayList<Category> currentCategories = (ArrayList<Category>) course.getCategories();
            	 String categoriesString = "";
            	 if(currentCategories != null){
            		 for (Category category : currentCategories) {
            			 if(!categoriesString.equals("")){
            				 categoriesString += ", ";
            			 }
            			 categoriesString += category.getName();
					}
            	 }
            	 request.setAttribute("categories", categoriesString);
             }
             
             
        }else{
        	request.setAttribute("course", new Course());
        }
        
        
        processRequest(request, response);
    } 
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		doGet(request,response);
    }

}