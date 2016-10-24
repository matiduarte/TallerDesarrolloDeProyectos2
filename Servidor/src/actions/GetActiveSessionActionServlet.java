package actions;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entities.CourseSession;

/**
 * Servlet implementation class GetActiveSessionActionServlet
 */
public class GetActiveSessionActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetActiveSessionActionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int courseId = Integer.valueOf(request.getParameter("courseId"));
		ArrayList<CourseSession> cs_list = (ArrayList<CourseSession>) CourseSession.getByCourseId(courseId);
		int sessionId = 0;
		
		if (cs_list.size() > 0){
			for (CourseSession cs : cs_list){
				if (cs.isActive())
					sessionId = cs.getId();
			}
		}
		
		ArrayList<Integer> data = new ArrayList<Integer>();
		data.add(sessionId);
		data.add(courseId);
		String json = new Gson().toJson(data);
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8"); 
    	response.getWriter().write(json);
	}

}
