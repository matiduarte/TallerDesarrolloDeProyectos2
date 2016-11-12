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
import entities.CourseComment;
import entities.CourseMessage;
import entities.CourseSession;
import entities.CourseUnity;
import entities.StudentSession;
import entities.User;

@Path("/comment")
public class CourseCommentService {
	
	@Path("")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
    public ServiceResponse saveComment(@FormParam("studentId")int studentId, @FormParam("courseId")int courseId, @FormParam("message")String message) {
		CourseComment courseComment = new CourseComment();
		courseComment.setMessage(message);
		courseComment.setCourseId(courseId);
		courseComment.setStudentId(studentId);
		courseComment.setTime(new Date());
		courseComment.save();
		
		return new ServiceResponse(true, "", "");
    }
	
}
