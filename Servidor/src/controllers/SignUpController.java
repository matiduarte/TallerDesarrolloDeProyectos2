package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.mailing.IMail;
import service.mailing.Mailer;

import entities.User;

/**
 * Servlet implementation class SignUpController
 */
@WebServlet("/signup")
public class SignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
    	       getServletConfig().getServletContext().getRequestDispatcher("/signup.jsp").forward(request,response);
    	    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
    	String password = request.getParameter("password");
    	String name = request.getParameter("name");
    	String lastName = request.getParameter("lastName");
    	User user = User.getByUserEmail(email);
    	boolean existe = true;
    	
    	
    	if (user == null) {
    		existe = false;
    		user = new User();
    		user.setEmail(email);
    		user.setPassword(password);
    		user.setFirstName(name);
    		user.setLastName(lastName);
    		user.setIsActive(false);
    	
    		user.save();
    	}
    	
		String finalizar_btn = request.getParameter("finalizar");
		
		if (finalizar_btn != null){
			if (!existe){
				
				Mailer.getInstancia().mandarMailConfirmarRegistracion( user.getEmail(), user.getFirstName() );
				
				response.sendRedirect(request.getContextPath() + "/signin.jsp");
				
			}else{
				request.setAttribute("errormsg", "Email existente.");
				getServletConfig().getServletContext().getRequestDispatcher("/signup.jsp").forward(request,response);
			}
		}
	}

}
