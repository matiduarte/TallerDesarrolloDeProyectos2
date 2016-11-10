package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;

/**
 * Servlet implementation class StatsVisualizationController
 */
@WebServlet("/stats")
public class StatsVisualizationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StatsVisualizationController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		getServletConfig().getServletContext()
				.getRequestDispatcher("/stats.jsp").forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// el número de posición en los arrays corresponde a una misma
		// categoria.
		// por ej:
		//        nombres_categorias[3] = COMPUTACION
		//        cantidad_aprobados_por_categoria[3] = cant de aprobados en la categoria COMPUTACION
		//        cantidad_desaprobados_por_categoria[3] = cant de desaprobados en la categoria COMPUTACION
		//        cantidad_abandonaron_por_categoria[3] = cant de abandonos en la categoria COMPUTACION		

		ArrayList<String> nombres_categorias = new ArrayList<String>();
		ArrayList<Integer> cantidad_aprobados_por_categoria = new ArrayList<Integer>();
		ArrayList<Integer> cantidad_desaprobados_por_categoria = new ArrayList<Integer>();
		ArrayList<Integer> cantidad_abandonaron_por_categoria = new ArrayList<Integer>();
		
		// ...aca va la lógica para completar las listas... por ahora van hardcodeadas...
		nombres_categorias.add("FISICA");
		nombres_categorias.add("MATEMATICAS");
		nombres_categorias.add("ELECTRONICA");
		nombres_categorias.add("COMPUTACION");
		
		cantidad_aprobados_por_categoria.add(7);
		cantidad_aprobados_por_categoria.add(9);
		cantidad_aprobados_por_categoria.add(8);
		cantidad_aprobados_por_categoria.add(15);
		
		cantidad_desaprobados_por_categoria.add(7);
		cantidad_desaprobados_por_categoria.add(6);
		cantidad_desaprobados_por_categoria.add(6);
		cantidad_desaprobados_por_categoria.add(4);
		
		cantidad_abandonaron_por_categoria.add(5);
		cantidad_abandonaron_por_categoria.add(4);
		cantidad_abandonaron_por_categoria.add(5);
		cantidad_abandonaron_por_categoria.add(1);		
		// ...

		request.setAttribute("nombres_categorias", nombres_categorias);
		request.setAttribute("cantidad_aprobados_por_categoria", cantidad_aprobados_por_categoria);
		request.setAttribute("cantidad_desaprobados_por_categoria", cantidad_desaprobados_por_categoria);
		request.setAttribute("cantidad_abandonaron_por_categoria", cantidad_abandonaron_por_categoria);
		
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
