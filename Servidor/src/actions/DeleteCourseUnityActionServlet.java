package actions;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entities.Answer;
import entities.CourseSession;
import entities.CourseUnity;
import entities.Question;
import entities.User;

/**
 * Servlet implementation class SignInController
 */
public class DeleteCourseUnityActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCourseUnityActionServlet() {
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
    		//Borro unidad
    		CourseUnity courseUnity = CourseUnity.getById(Integer.valueOf(unityId));
    		courseUnity.delete();
    		//Si tiene preguntas las borro
    		ArrayList<Question> questionList = (ArrayList<Question>) Question.getByUnityId(Integer.valueOf(unityId));
    		if (questionList.size() > 0){
				for (Question q : questionList){
					ArrayList<Answer> answerList = (ArrayList<Answer>) Answer.getByQuestionId(q.getId());
					//Borro las respuestas
					for (Answer a : answerList){
						a.delete();
					}
					q.delete();
				}
			}
    		
    	}

    	
    	String json = new Gson().toJson("ok");
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8"); 
    	response.getWriter().write(json); 
    }

}
