package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Course;

/**
 * Servlet implementation class PrincipalAdminController
 */
@WebServlet("/cursos/admin")
public class PrincipalAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrincipalAdminController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
    	       getServletConfig().getServletContext().getRequestDispatcher("/principalAdmin.jsp").forward(request,response);
    	    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	
    	// de aca tengo q recuperar todos los cursos: nombre, descrip, categorias, docente
    	// IDEA: recuperar y poner los datos en:
    	// 								- poner todo en la clase TableCourse y llamar desde el jsp.
    	
    	for ( Course curso : Course.getAll() ) {
    		// 1: recupero los docentes del curso.
    		// 2: recupero las categorias del curso.
    	}
    	
        processRequest(request, response);
    } 
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {   
    	getServletConfig().getServletContext().getRequestDispatcher("/signin.jsp");
    }

}
