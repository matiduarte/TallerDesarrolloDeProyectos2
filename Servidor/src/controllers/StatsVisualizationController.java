package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;

import entities.Report;
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
		
		JSONArray nombres_categorias_json = new JSONArray();
		JSONArray nombres_cursos_json = new JSONArray();
		JSONArray cantidad_aprobados_por_categoria_json = new JSONArray();
		JSONArray cantidad_desaprobados_por_categoria_json = new JSONArray();
		JSONArray cantidad_abandonaron_por_categoria_json = new JSONArray();		
		
		// el número de posición en los arrays corresponde a un mismo curso.
		// por ej:
		//		  nombres_curso[3] = Algoritmos y Programacion I
		//        nombres_categorias[3] = COMPUTACION
		//        cantidad_aprobados_por_categoria[3] = cant de aprobados en el curso
		//        cantidad_desaprobados_por_categoria[3] = cant de desaprobados en el curso
		//        cantidad_abandonaron_por_categoria[3] = cant de abandonos en el curso
		
		ArrayList<Report> reportes_cursos = Report.getReportList();
		
		for( Report reporte : reportes_cursos ) {
			nombres_categorias_json.put( reporte.getCategory() );
			nombres_cursos_json.put( reporte.getCourseName() );
			cantidad_aprobados_por_categoria_json.put( reporte.getPass() );
			cantidad_desaprobados_por_categoria_json.put( reporte.getNoPass() );
			cantidad_abandonaron_por_categoria_json.put( reporte.getGiveUp() );
		}
		
		request.setAttribute("nombres_cursos", nombres_cursos_json.toString());
		request.setAttribute("nombres_categorias", nombres_categorias_json.toString());
		request.setAttribute("cantidad_aprobados_por_curso", cantidad_aprobados_por_categoria_json.toString());
		request.setAttribute("cantidad_desaprobados_por_curso", cantidad_desaprobados_por_categoria_json.toString());
		request.setAttribute("cantidad_abandonaron_por_curso", cantidad_abandonaron_por_categoria_json.toString());
		
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
