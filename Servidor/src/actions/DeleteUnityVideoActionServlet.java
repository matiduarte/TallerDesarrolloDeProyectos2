package actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entities.CourseSession;
import entities.CourseUnity;
import entities.User;
import utils.FileUtil;

/**
 * Servlet implementation class SignInController
 */
public class DeleteUnityVideoActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUnityVideoActionServlet() {
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
    	String unityId = request.getParameter("unityId");
    	
    	if(unityId != null && !unityId.equals("")){
    		CourseUnity courseUnity = CourseUnity.getById(Integer.valueOf(unityId));
    		courseUnity.setVideoUrl("");
    		courseUnity.save();
    		
    		FileUtil.cleanDirectory("WebContent/Files/CourseUnity/" + unityId);
    	}

    	
    	String json = new Gson().toJson("ok");
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8"); 
    	response.getWriter().write(json); 
    }

}
