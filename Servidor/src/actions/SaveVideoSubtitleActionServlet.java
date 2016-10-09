package actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import entities.CourseSession;
import entities.CourseUnity;
import entities.User;
import service.ServiceResponse;
import utils.FileUtil;

/**
 * Servlet implementation class SignInController
 */
public class SaveVideoSubtitleActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveVideoSubtitleActionServlet() {
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
    	int unityId = Integer.valueOf(request.getParameter("unityId"));
    	final Part filePart = request.getPart("subtitle");
    	String language = URLDecoder.decode(request.getParameter("language"), "UTF-8");
    	
    	//CourseUnity unity = CourseUnity.getById(unityId);
    	if(!(unityId > 0)){
    		//TODO: ver que hacer
    	}
    	
    	final String path = "WebContent/Files/CourseUnity/" + unityId + "/Subtitles/";
        final String urlPath = "Files/CourseUnity/" + unityId + "/Subtitles/";
        final String fileName = language;
        if(!(fileName.compareTo("") == 0)){
        	FileUtil.saveFile(path, filePart, fileName);
        }
    	
        String json = new Gson().toJson("ok");
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8"); 
    	response.getWriter().write(json); 
    	
    }

}
