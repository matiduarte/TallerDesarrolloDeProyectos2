package actions;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.FileUtil;

import com.google.gson.Gson;
import com.mysql.cj.fabric.xmlrpc.base.Data;

import entities.Answer;
import entities.CourseUnity;
import entities.DataTransfer;
import entities.Question;

/**
 * Servlet implementation class DeleteQAActionServlet
 */

public class DeleteQAActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteQAActionServlet() {
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
		
		String unityId = request.getParameter("unityId");
		String questionId = request.getParameter("questionId");
    	
    	if(unityId != null && !unityId.equals("")){
    		ArrayList<Question> questionList = (ArrayList<Question>) Question.getByUnityId(Integer.valueOf(unityId));
    		if (questionList.size() > 0){
					ArrayList<Answer> answerList = (ArrayList<Answer>) Answer.getByQuestionId(Integer.valueOf(questionId));
					//Borro las respuestas
					for (Answer a : answerList){
						a.delete();
					}
					for (Question q : questionList){
						if (q.getId() == Integer.valueOf(questionId))
							q.delete();
					}
			}
    	}
    	
    	DataTransfer dt = new DataTransfer();
    	dt.setQuestionId(Integer.valueOf(questionId));
    	
    	String json = new Gson().toJson(dt);
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8"); 
    	response.getWriter().write(json); 
	}

}
