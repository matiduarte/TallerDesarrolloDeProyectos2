package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.User;

/**
 * Servlet implementation class SignInController
 */
@WebServlet("/activarUsuario")
public class ActivarUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivarUsuarioController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
    	       getServletConfig().getServletContext().getRequestDispatcher("/activarUsuario.jsp").forward(request,response);
    	    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	
    	String mail_a_activar = request.getParameter("mail"); // aca en verdad deberia comparar el token enviado por parametro 
    														  // con el token generador a partir del mail del usuario.
    	User user = User.getByUserEmail( mail_a_activar );
		user.setIsActive(true);
		user.save();
    	
        processRequest(request, response);
    } 
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {   
    	getServletConfig().getServletContext().getRequestDispatcher("/signin.jsp");
    }

}
