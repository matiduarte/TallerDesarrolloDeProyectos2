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
import entities.Certification;
import entities.Course;
import entities.CourseSession;
import entities.CourseUnity;
import entities.Question;
import entities.User;
import utils.FileUtil;

@Path("/certification")
public class CertificationService {
	
	@Path("{id}")
	@GET
	@Produces("application/json")
	public ServiceResponse getCertifications(@PathParam("id") int id){
		User user = User.getById(id);
		List<Certification> certifications = user.getCertificacions();
		
		if (certifications != null){
	    	JSONObject jo = new JSONObject();
			try {
				Gson g = new Gson();
				String certificationsString = g.toJson(certifications);
				jo.put("certifications", certificationsString);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return new ServiceResponse(true, "", jo.toString());
	    }
		
		return new ServiceResponse(false, "No hay certificaciones para el usuario con id " + id, "");
	}
	
}
