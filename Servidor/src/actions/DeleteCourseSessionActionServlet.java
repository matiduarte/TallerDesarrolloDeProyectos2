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
import service.ServiceResponse;

/**
 * Servlet implementation class SignInController
 */
public class DeleteCourseSessionActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCourseSessionActionServlet() {
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
    	String sessionId = request.getParameter("sessionId");
    	
    	ServiceResponse message = null;
    	if(sessionId != null && !sessionId.equals("")){
    		CourseSession courseSession = CourseSession.getById(Integer.valueOf(sessionId));
    		if(!courseSession.isActive()){
    			courseSession.delete();
    			message = new ServiceResponse(true, "", "");
    		}else{
    			message = new ServiceResponse(false, "No se puede borrar una sesion activa!", "");
    		}
    		
    	}

    	
    	String json = new Gson().toJson(message);
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8"); 
    	response.getWriter().write(json); 
    }

}
