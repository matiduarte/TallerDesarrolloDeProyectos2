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
import utils.FileUtil;

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
    	return;
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
    	String categoryIdsEntry = request.getParameter("categories");
    	String[] categoryIds = categoryIdsEntry.split(",");

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
		doGet(request,response);
    }


}