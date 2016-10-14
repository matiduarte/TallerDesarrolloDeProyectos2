package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entities.CourseSession;
import entities.CourseUnity;
import entities.User;
import service.ServiceResponse;

public class HasActiveSessionsActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HasActiveSessionsActionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	
    	Integer course_id = Integer.parseInt( request.getParameter("course_id") );
    	
    	List<CourseSession> sesiones = CourseSession.getByCourseId(course_id);
    	
    	Boolean hay_sesiones_activas = false;
    	for( CourseSession sesion : sesiones ) {
    		if ( sesion.isActive() ) {
    			hay_sesiones_activas = true;
    		}
    	}
    	
    	ServiceResponse message = new ServiceResponse( true, "", "{ \"hay_sesiones_activas\" : " + hay_sesiones_activas.toString() + " }");
    	
    	String json = new Gson().toJson(message);
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8"); 
    	response.getWriter().write(json); 
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	
    }
}
