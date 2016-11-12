package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import entities.Answer;
import entities.Course;
import entities.CourseUnity;
import entities.Question;
import entities.StudentExam;
import entities.StudentSession;
import entities.User;

@Path("/exam")
public class ExamService {
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
    public ServiceResponse saveExam(@FormParam("studentId")int studentId, @FormParam("unityId")int unityId, @FormParam("sessionId")int sessionId, @FormParam("result")float result, @FormParam("isFinal")boolean isFinal) {
		User user = User.getById(studentId); 
		if(user != null){
			StudentExam studentExam = new StudentExam();
			studentExam.setStudentId(studentId);
			studentExam.setUnityId(unityId);
			studentExam.setSessionId(sessionId);
			studentExam.setResult(result);
			studentExam.setIsFinal(isFinal);
			
			studentExam.save();
			
			return new ServiceResponse(true, "", String.valueOf(studentExam.getId()));
		}
		
		return new ServiceResponse(false, "No se encontro el estudiante", "");
    }
	
	@Path("{courseId}")
	@GET
	@Produces("application/json")
	public ServiceResponse getTest(@PathParam("courseId") int courseId){
		Course course = Course.getById(courseId);	 
		if (course != null){
			ArrayList<CourseUnity> unities = (ArrayList<CourseUnity>) CourseUnity.getByCourseId(course.getId());
			ArrayList<Question> allQuestions = new ArrayList<Question>();
			
			for (CourseUnity courseUnity : unities) {
				allQuestions.addAll((ArrayList<Question>) Question.getByUnityId(courseUnity.getId()));
			}
			
			ArrayList<Question> questions = new ArrayList<Question>();
			
			Random generator = new Random();
			for (int i = 0; i < 10; i++) {
				int randomIndex = generator.nextInt(allQuestions.size());
				Question q = allQuestions.get(randomIndex);
				q.setAnswers(Answer.getByQuestionId(q.getId()));
				questions.add(q);
			}
			
			
			
	    	JSONObject jo = new JSONObject();
			try {
				Gson g = new Gson();
				String questionString = g.toJson(questions);
				jo.put("questions", questionString);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return new ServiceResponse(true, "", jo.toString());
	    }
		
		return new ServiceResponse(false, "No se encontro courso con id " + courseId, "");
	}

	
}
