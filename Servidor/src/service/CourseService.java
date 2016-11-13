package service;

import java.util.ArrayList;
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
import entities.CourseSession;
import entities.CourseUnity;

@Path("/course")
public class CourseService {
	
	@Path("search/{search}")
	@GET
	@Produces("application/json")
	public ServiceResponse searchCourses(@PathParam("search") String search){
		List<Course> courses = Course.search(search);	 
		if (courses != null && !courses.isEmpty()){
	    	JSONObject jo = new JSONObject();
			try {
				Gson g = new Gson();
				String coursesString = g.toJson(courses);
				jo.put("courses", coursesString);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return new ServiceResponse(true, "", jo.toString());
	    }
		
		return new ServiceResponse(false, "", "");
	}
	
	@Path("{id}")
	@GET
	@Produces("application/json")
	public ServiceResponse getCourse(@PathParam("id") int id, @DefaultValue("0") @QueryParam("studentId") int studentId){
		Course course = Course.getById(id,studentId);	
		if (course != null){
	    	JSONObject jo = new JSONObject();
			try {
				Gson g = new Gson();
				String courseString = g.toJson(course);
				jo.put("course", courseString);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return new ServiceResponse(true, "", jo.toString());
	    }
		
		return new ServiceResponse(false, "No se encontro curso con id " + id, "");
	}
	
}
