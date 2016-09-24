package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.mailing.Mailer;
import entities.Category;
import entities.Course;
import entities.CourseUnity;
import entities.User;

/**
 * Servlet implementation class NewUnityController
 */
@WebServlet("/newunity")
public class NewUnityController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewUnityController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		if(request.getParameter("id") != null){
			request.setAttribute("id", request.getParameter("id"));
		}
		getServletConfig().getServletContext().getRequestDispatcher("/newunity.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
    	String description = request.getParameter("description");
    	boolean existe = true;
    	
		if(request.getParameter("id") != null){
       	 	int courseId = Integer.valueOf(request.getParameter("id")); 
       	 	CourseUnity courseUnity = CourseUnity.getById(courseId);
       	 	
       	 		if (courseUnity == null){
       	 			existe = false;
       	 			courseUnity = new CourseUnity();
       	 			courseUnity.setCourseId(courseId);
       	 			courseUnity.setName(name);
       	 			courseUnity.setDescription(description);
       	 			courseUnity.save();
       	 		}   
		}
		
		String create_btn = request.getParameter("create_btn");
		
		if (create_btn != null){
			if (!existe){
				
				response.sendRedirect(request.getContextPath() + "/courseDetail?id=" + request.getParameter("id"));
				
			}
		}
	}

}
