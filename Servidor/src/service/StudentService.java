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
import entities.StudentSession;
import entities.User;

@Path("/student")
public class StudentService {
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
    public ServiceResponse saveStudent(@FormParam("email")String email, @FormParam("firstName")String firstName, @FormParam("lastName")String lastName, @FormParam("source")String source) {
		User user = User.getByAndEmailSource(email, source); 
		if(user == null){
			user = new User();
			user.setEmail(email);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setIsStudent(true);
			user.setSource(source);
			user.save();
		}
		
		return new ServiceResponse(true, "", String.valueOf(user.getId()));
    }
	
	@Path("subscribe")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
    public ServiceResponse subscribeStudent(@FormParam("studentId")int studentId, @FormParam("curseId")int curseId) {
		int sessionId = 0;
		StudentSession studentSession = StudentSession.getByStudentIdAndSessionId(studentId, sessionId);
		if(studentSession == null){
			studentSession = new StudentSession();
			studentSession.setSessionId(sessionId);
			studentSession.setStudentId(studentId);
			studentSession.save();
		}
		return new ServiceResponse(true, "", "");
    }
	
	@Path("unsubscribe")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
    public ServiceResponse unsubscribeStudent(@FormParam("studentId")int studentId, @FormParam("curseId")int curseId) {
		int sessionId = 0;
		StudentSession studentSession = StudentSession.getByStudentIdAndSessionId(studentId, sessionId);
		if(studentSession != null){
			studentSession.delete();
		}
		return new ServiceResponse(true, "", "");
    }
	
	@Path("subscriptions/{studentId}")
	@GET
	@Produces("application/json")
    public ServiceResponse getStudentSubcriptions(@PathParam("studentId") int studentId) {
		int sessionId = 0;
		List<Course> courses = StudentSession.getCursesSubscribed(studentId);
		
		JSONObject jo = new JSONObject();
		try {
			Gson g = new Gson();
			String coursesString = g.toJson(courses);
			jo.put("course", coursesString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ServiceResponse(true, "", jo.toString());
    }
	
}
