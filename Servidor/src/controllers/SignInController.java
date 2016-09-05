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
@WebServlet("/signin")
public class SignInController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignInController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
    	       getServletConfig().getServletContext().getRequestDispatcher("/signin.jsp").forward(request,response);
    	    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	
    	String email = request.getParameter("email");
    	String password = request.getParameter("password");
    	
    	User user = User.getByUserEmail(email);
    	boolean mismoPass = false;
    	
    	System.out.println("PASS " + user.getPassword());
    	
    	if (user != null){
    		if (user.getPassword().equals(password))
    			mismoPass = true;
    	}
    	
    	if (mismoPass){
    		System.out.println("ENTRE MISMO");
    		request.setAttribute("error", "false");
    	} else {
    		System.out.println("ENTRE MAL");
    		request.setAttribute("error", "true");
    	}
    	
        processRequest(request, response);
    }

}
