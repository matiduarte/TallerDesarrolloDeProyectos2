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
        final String path = System.getProperty("user.dir") + "/Files/Course/" + course.getId() + "/";
        final Part filePart = request.getPart("picture");
        final String fileName = getFileName(filePart);
        if(fileName != ""){

            OutputStream out = null;
            InputStream filecontent = null;
            final PrintWriter writer = response.getWriter();
            
            final File parent = new File(path);
            parent.mkdirs();

            try {
                out = new FileOutputStream(new File(path, fileName));
                filecontent = filePart.getInputStream();

                int read = 0;
                final byte[] bytes = new byte[1024];

                while ((read = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                //writer.println("New file " + fileName + " created at " + path);
            } catch (FileNotFoundException fne) {
                writer.println("You either did not specify a file to upload or are "
                        + "trying to upload a file to a protected or nonexistent "
                        + "location.");
                writer.println("<br/> ERROR: " + fne.getMessage());
            } finally {
                if (out != null) {
                    out.close();
                }
                if (filecontent != null) {
                    filecontent.close();
                }
                if (writer != null) {
                    writer.close();
                }
            }
            
            course.setPictureUrl(path + fileName);
            course.save();
        }
    	
        processRequest(request, response);
    }
    
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}