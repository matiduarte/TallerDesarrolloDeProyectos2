package controllers;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.CourseUnity;
import utils.FileUtil;


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
		
		if(request.getParameter("courseId") != null){	
			request.setAttribute("courseId", request.getParameter("courseId"));
			if (request.getParameter("id") != ""){
				request.setAttribute("id", request.getParameter("id"));
				int id = Integer.valueOf(request.getParameter("id"));
				CourseUnity courseUnity = CourseUnity.getById(id);
				request.setAttribute("name", courseUnity.getName());
				request.setAttribute("description", courseUnity.getDescription());
				request.setAttribute("html", courseUnity.getHtml());
				if(courseUnity.getVideoUrl() != null && !(courseUnity.getVideoUrl().compareTo("") == 0)){
					request.setAttribute("videUrl", courseUnity.getVideoUrl());
					
					 File file = new File("WebContent/" + courseUnity.getVideoUrl());
					 DecimalFormat df = new DecimalFormat("#.##");
					 df.setRoundingMode(RoundingMode.FLOOR);
					 
					 double videoSize = file.length();
					 videoSize = (videoSize/1024)/1024;
					 
					 request.setAttribute("videoSize", df.format(videoSize));
					 
					 ArrayList<String> subtitles = FileUtil.getFileNamesInDirectory("WebContent/Files/CourseUnity/" + id + "/Subtitles");
					 request.setAttribute("subtitles", subtitles);
				}
			} 
		}
		getServletConfig().getServletContext().getRequestDispatcher("/newunity.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
    	String description = request.getParameter("description");
    	String html = request.getParameter("htmlEditor");
    	CourseUnity courseUnity = null;
    	
		if(request.getParameter("courseId") != null){
			int courseId = Integer.valueOf(request.getParameter("courseId"));
			HttpSession session = request.getSession(true);
			if(request.getParameter("id") != null && !request.getParameter("id").equals("")){
				int id = Integer.valueOf(request.getParameter("id"));
				courseUnity = CourseUnity.getById(id);
				courseUnity.setId(id);
				session.setAttribute("alertType", "edit");
			}else{
	   			courseUnity = new CourseUnity();
	   			session.setAttribute("alertType", "create");
			}
			courseUnity.setCourseId(courseId);
   			courseUnity.setName(name);
   			courseUnity.setDescription(description);
   			courseUnity.setHtml(html);
			courseUnity.save();
		}
		
		String create_btn = request.getParameter("create_btn");
		
		if (create_btn != null){
			response.sendRedirect(request.getContextPath() + "/courseDetail?id=" + request.getParameter("courseId"));
			
		}
	}

}
