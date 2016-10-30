package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import entities.Category;
import entities.Course;
import entities.CourseMessage;
import entities.CourseSession;
import entities.CourseUnity;
import entities.StudentSession;
import entities.User;

@Path("/forum")
public class ForumService {
	
	@Path("")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
    public ServiceResponse saveMessage(@FormParam("studentId")int studentId, @FormParam("sessionId")int sessionId, @FormParam("message")String message) {
		CourseMessage courseMessage = new CourseMessage();
		courseMessage.setMessage(message);
		courseMessage.setSessionId(sessionId);
		courseMessage.setStudentId(studentId);
		courseMessage.setTime(new Date());
		courseMessage.save();
		
		return new ServiceResponse(true, "", "");
    }
	
	
	@Path("{sessionId}")
	@GET
	@Produces("application/json")
	public ServiceResponse getForum(@PathParam("sessionId") int sessionId){
		List<CourseMessage> messages = CourseMessage.getBySessionId(sessionId);
		
		if (messages != null && !messages.isEmpty()){
			List<CourseMessage> messagesFixed = new ArrayList<CourseMessage>();
			
			for (CourseMessage courseMessage : messages) {
				User user = User.getById(courseMessage.getStudentId());
				if(user != null){
					courseMessage.setStudentFirstName(user.getFirstName());
					courseMessage.setStudentLastName(user.getLastName());
					messagesFixed.add(courseMessage);
				}
			}
			
			
	    	JSONObject jo = new JSONObject();
			try {
				Gson g = new Gson();
				String messagesString = g.toJson(messagesFixed);
				jo.put("messages", messagesString);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return new ServiceResponse(true, "", jo.toString());
	    }
		
		return new ServiceResponse(false, "No se encontro el foro para la session con id " + sessionId, "");
	}

	
}
