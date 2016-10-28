package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.fabric.xmlrpc.base.Array;

import entities.CourseMessage;
import entities.User;
import entities.UserMessage;

/**
 * Servlet implementation class ForumMessageController
 */
@WebServlet("/forummessage")
public class ForumMessageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForumMessageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sessionId = request.getParameter("id");
		String moderate = request.getParameter("mod");
		String courseId = request.getParameter("courseId");
		ArrayList<UserMessage> userMessageList = new ArrayList<UserMessage>();
		
		if (sessionId != null){
			ArrayList<CourseMessage> messageList = (ArrayList<CourseMessage>) CourseMessage.getBySessionId(Integer.valueOf(sessionId));
			
			for (CourseMessage c : messageList){
				UserMessage um = new UserMessage();
				User us = User.getById(c.getStudentId());
				String name = us.getFirstName();
				name += " " + us.getLastName();
				um.setName(name);
				um.setMessage(c.getMessage());
				um.setIsModerate(c.getIsModerate());
				userMessageList.add(um);
			}
			request.setAttribute("forumList", userMessageList);
			request.setAttribute("moderate", moderate);
			request.setAttribute("courseId", courseId);
			request.setAttribute("sessionId", sessionId);
		}
		
		getServletConfig().getServletContext().getRequestDispatcher("/forummessage.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sessionId = request.getParameter("sessionId");
		String[] messageList = request.getParameterValues("message[]");
		
		ArrayList<CourseMessage> courseMessageList = (ArrayList<CourseMessage>) CourseMessage.getBySessionId(Integer.valueOf(sessionId));
		
		Integer count = 1;
		for (CourseMessage cm : courseMessageList){
			cm.setId(count);
			if (!cm.getMessage().equals(messageList[count - 1]))
				cm.setIsModerate(true);
			
			cm.setMessage(messageList[count - 1]);
			cm.save();
			count++;
		}
		
		response.sendRedirect(request.getContextPath() + "/courseDetail?id=" + request.getParameter("courseId"));
		
	}

}
