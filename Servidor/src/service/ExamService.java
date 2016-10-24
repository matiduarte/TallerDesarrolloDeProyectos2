package service;

import java.util.List;
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

import entities.Course;
import entities.StudentExam;
import entities.StudentSession;
import entities.User;

@Path("/exam")
public class ExamService {
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
    public ServiceResponse saveExam(@FormParam("studentId")int studentId, @FormParam("unityId")int unityId, @FormParam("sessionId")int sessionId, @FormParam("result")float result) {
		User user = User.getById(studentId); 
		if(user != null){
			StudentExam studentExam = new StudentExam();
			studentExam.setStudentId(studentId);
			studentExam.setUnityId(unityId);
			studentExam.setSessionId(sessionId);
			studentExam.setResult(result);
			
			studentExam.save();
			
			return new ServiceResponse(true, "", String.valueOf(studentExam.getId()));
		}
		
		return new ServiceResponse(false, "No se encontro el estudiante", "");
    }

	
}
