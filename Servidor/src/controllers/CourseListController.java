package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.api.x.Collection;

import entities.Course;
import entities.User;

/**
 * Servlet implementation class CourseListController
 */
@WebServlet("/courselist")
public class CourseListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseListController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		
		request.setAttribute( "teacher_id", user.getId() );
		
		if (user != null){
			List<Course> list = (ArrayList<Course>) Course.getByTeacherId(user.getId());
			Collections.sort(list, new Comparator<Course>() {
			    @Override
			    public int compare(Course c1, Course c2) {
			        return c1.getName().compareTo(c2.getName());
			    }
			});
			
			
			if (list.size() == 0)
				request.setAttribute("noCourse", true);
			if (list.size() == 1){
				request.setAttribute("noCourse", null);	
				request.setAttribute("col", "");
				request.setAttribute("max", "width:360px;height:270px;");
				request.setAttribute("btn_ver", "btn_ver_1");
				request.setAttribute("course", "btn_course_1");
			}
			if (list.size() == 2){
				request.setAttribute("noCourse", null);	
				request.setAttribute("col", "col-xs-6");
				request.setAttribute("max", "width:360px;height:270px;");
				request.setAttribute("btn_ver", "btn_ver_2");
				request.setAttribute("course", "btn_course_2");
			}
			if (list.size() > 2){
				request.setAttribute("noCourse", null);	
				request.setAttribute("col", "col-md-4");
				request.setAttribute("max", "width:290px;height:220px;");
				request.setAttribute("btn_ver", "btn_ver_more");
				request.setAttribute("course", "btn_course_more");
			}
			
			request.setAttribute("list", list);
		}
		
		getServletConfig().getServletContext().getRequestDispatcher("/courselist.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
