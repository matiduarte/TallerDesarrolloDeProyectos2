package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import entities.Answer;
import entities.Category;
import entities.Course;
import entities.CourseSession;
import entities.CourseUnity;
import entities.Question;
import utils.FileUtil;

@Path("/unity")
public class UnityService {
	
	@Path("{id}")
	@GET
	@Produces("application/json")
	public ServiceResponse getUnity(@PathParam("id") int id, @DefaultValue("0") @QueryParam("studentId") int studentId){
		CourseUnity unity = CourseUnity.getById(id);
		
		if (unity != null){
			if(studentId > 0){
				unity.checkStudentExam(studentId);
			}
			
			ArrayList<String> subtitles = unity.getSubtitlesUrl();
	    	JSONObject jo = new JSONObject();
			try {
				Gson g = new Gson();
				String unityString = g.toJson(unity);
				String subtitlesString = g.toJson(subtitles);
				jo.put("unity", unityString);
				jo.put("subtitles", subtitlesString);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return new ServiceResponse(true, "", jo.toString());
	    }
		
		return new ServiceResponse(false, "No se encontro unidad con id " + id, "");
	}
	
	@Path("test/{id}")
	@GET
	@Produces("application/json")
	public ServiceResponse getTest(@PathParam("id") int id){
		CourseUnity unity = CourseUnity.getById(id);	 
		if (unity != null){
			ArrayList<Question> allQuestions = (ArrayList<Question>) Question.getByUnityId(id);
			ArrayList<Question> questions = new ArrayList<Question>();
			
			Random generator = new Random();
			for (int i = 0; i < unity.getQuestionSize(); i++) {
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
		
		return new ServiceResponse(false, "No se encontro unidad con id " + id, "");
	}
	
}
