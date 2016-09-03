package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

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

/**
 * Servlet implementation class SignInController
 */
@WebServlet("/newCourse")
public class NewCourseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewCourseController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	getServletConfig().getServletContext().getRequestDispatcher("/newCourse.jsp").forward(request,response);
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ArrayList<Category> allCategories = (ArrayList<Category>) Category.getAll();
        
        request.setAttribute("categories", allCategories);
        
        processRequest(request, response);
    } 
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	
    	String name = request.getParameter("name");
    	String description = request.getParameter("description");
    	String[] categoryIds = request.getParameterValues("categories");
    	
    	Course course = new Course();
    	course.setName(name);
    	course.setDescription(description);
    	course.save();
    	
    	for (String categoryId : categoryIds) {
			CourseCategory courseCategory = new CourseCategory();
			courseCategory.setCategoryId(Integer.parseInt(categoryId));
			courseCategory.setCourseId(course.getId());
			courseCategory.save();
		}
    	
//    	File newFile = new File("Course/1/picture,jpg");
//    	boolean isCreated = newFile.createNewFile();
//    	BufferedWriter out = new BufferedWriter(new FileWriter(newFile));
//    	out.write(request.getParameter("picture"));
//    	out.close();
    	
        processRequest(request, response);
    }

}