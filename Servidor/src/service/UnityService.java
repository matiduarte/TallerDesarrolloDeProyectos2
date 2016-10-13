package service;

import java.util.ArrayList;
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
import entities.CourseSession;
import entities.CourseUnity;
import utils.FileUtil;

@Path("/unity")
public class UnityService {
	
	@Path("{id}")
	@GET
	@Produces("application/json")
	public ServiceResponse getUnity(@PathParam("id") int id){
		CourseUnity unity = CourseUnity.getById(id);	 
		if (unity != null){
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
	
}
