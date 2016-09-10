package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.mailing.Mailer;
import entities.User;

/**
 * Servlet implementation class SendMailController
 */
@WebServlet("/recuperarPass")
public class RecuperarPassController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecuperarPassController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
    	       getServletConfig().getServletContext().getRequestDispatcher("/recuperarPass.jsp").forward(request,response);
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
		User user = User.getByUserEmail(email);
		
		if (user != null){
			Mailer.getInstancia().mandarMailRecuperarPass(user.getEmail(), user.getFirstName(), user.getPassword());
		}
		
		String finalizar_btn = request.getParameter("finalizar");
		
		if (finalizar_btn != null){
			response.sendRedirect(request.getContextPath() + "/signin.jsp");
		}
		
	}

}
