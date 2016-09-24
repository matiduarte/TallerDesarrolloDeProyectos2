package actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entities.CourseSession;
import entities.User;

/**
 * Servlet implementation class SignInController
 */
public class SaveCourseSessionActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveCourseSessionActionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    } 
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {   
    	String sessionDate = request.getParameter("sessionDate");
    	int courseId = Integer.valueOf(request.getParameter("courseId"));
    	String sessionId = request.getParameter("sessionId");
    	
    	CourseSession courseSession = new CourseSession();
    	courseSession.setCourseId(courseId);
    	courseSession.setDate(sessionDate);
    	
    	if(sessionId != null && !sessionId.equals("")){
    		//Edit
    		courseSession.setId(Integer.valueOf(sessionId));
    	}
    	courseSession.save();
    	
    	String json = new Gson().toJson(courseSession);
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8"); 
    	response.getWriter().write(json); 
    }

}
