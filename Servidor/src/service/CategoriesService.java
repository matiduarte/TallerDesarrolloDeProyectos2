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

import entities.Category;
import entities.Course;

@Path("/categories")
public class CategoriesService {
	
	@Path("")
	@GET
	@Produces("application/json")
	public ServiceResponse getCategories(){
		
		List<Category> listOfCategories = Category.getAllWithCourses();	 
		List<Course> soonCourses = Course.getAll();	 
		if (!listOfCategories.isEmpty()){
	    	JSONObject jo = new JSONObject();
			try {
				Gson g = new Gson();
				String categoriesString = g.toJson(listOfCategories);
				String soonCoursesString = g.toJson(soonCourses);
				jo.put("allCategories", categoriesString);
				jo.put("soonCourses", soonCoursesString);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return new ServiceResponse(true, "", jo.toString());
	    }
		
		return new ServiceResponse(false, "", "");
	}
	
}
