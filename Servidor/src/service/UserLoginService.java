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

@Path("/prueba")
public class UserLoginService {
	
	@POST
    @Path("")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
    public ServiceResponse prueba(@FormParam("userName")String userName) {
		return new ServiceResponse(false, "Usuario o contraseña incorrecto", "");

    }
	
}
