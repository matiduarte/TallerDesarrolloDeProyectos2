package actions;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entities.Answer;
import entities.CourseUnity;
import entities.DataTransfer;
import entities.Question;

public class SaveQAActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveQAActionServlet() {
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
		
		int unityId = Integer.valueOf(request.getParameter("unityId"));
		String questionTittle = request.getParameter("question");
		String[] answersList = request.getParameterValues("answersArray[]");
		String[] checkboxsList = request.getParameterValues("selectedRows[]");
		
		CourseUnity unity = CourseUnity.getById(unityId);
		
		if(!(unityId > 0)){
    		unity = new CourseUnity();
    		int courseId = Integer.valueOf(request.getParameter("courseId"));
    		unity.setCourseId(courseId);
    		unity.save();
    		unityId = unity.getId();
    	}
		
		//Guardo la pregunta
		Question question = new Question();
		question.setQuestion(questionTittle);
		question.setUnityId(unityId);
		question.save();
		
		//Guardo todas las respuestas y seteo la correcta
		for (int i=0 ; i < answersList.length; i++){
			Answer answer = new Answer();
			answer.setQuestionId(question.getId());
			if (checkboxsList[i].equals("1")){
				answer.setIsCorrect(true);
				answer.setAnswer(answersList[i]);
			} else {
				answer.setIsCorrect(false);
				answer.setAnswer(answersList[i]);
			}
			answer.save();
		}
		
		DataTransfer dt = new DataTransfer();
		dt.setQuestion(question.getQuestion());
		dt.setUnityId(unityId);
		
		
		String json = new Gson().toJson(dt);
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8"); 
    	response.getWriter().write(json); ;
	}

}
