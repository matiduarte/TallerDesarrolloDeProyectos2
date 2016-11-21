package controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;

import utils.ComparadorPorTotalInscriptosMapaInscriptos;
import utils.ComparadorReportePorInscriptos;
import entities.Course;
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
		
		System.out.println( "from: " + request.getParameter("from") );
		System.out.println( "until: " + request.getParameter("until") );
		
		// ****** PROBAR ESTO
		boolean con_rango = true;
		Date from = new Date();
		Date until = new Date();
		try {
			from = new SimpleDateFormat("yyyy/MM/dd").parse( request.getParameter("from") );
			until = new SimpleDateFormat("yyyy/MM/dd").parse( request.getParameter("until") );
		} catch(Exception e) {
			System.out.println( "rango de fechas no valido: " + e );
			con_rango = false;
		}
		
		// ****** PROBAR ESTO
		DateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
		
		System.out.println( "Desde: " + formato.format(from) );
		System.out.println( "Hasta: " + formato.format(until) );
		
		ArrayList<Report> reportes_cursos = Report.getReportList( from, until );
		
		if ( con_rango ) {
			System.out.println( "CON rango de fechas" );
			reportes_cursos = Report.getReportList( from, until );
		} else {
			System.out.println( "SIN rango de fechas" );
			reportes_cursos = Report.getReportList();
		}
		
		if ( false == this.esAdmin( request.getParameter("viewer") ) ) {
			// si no es admin, entonces es docente, entonces filtro para que me queden solo los reportes de sus cursos.
			Integer id_docente = Integer.parseInt( request.getParameter("id") );
			reportes_cursos = this.filtrarPorIdReportesPertenecientesADocente( reportes_cursos, id_docente );
		}
		
		// paso los reportes asi nomas para armar la tabla html, el resto lo hace el plugin "DataTable".
		request.setAttribute("reportes_cursos", reportes_cursos );
		
		// ahora viene la logica para armar las listas que va a usar el plugin "highcharts".
		
		// creo un mapa que va a tener: clave=categoria, valor=array con: [0]=aprobados, [1]=desaprobados y [2]=abandonaron.
		Map<String, ArrayList<Integer>> inscriptos_por_categoria = new HashMap<String, ArrayList<Integer>>();
		
		for ( Report reporte : reportes_cursos ) {
			
			String categoria = reporte.getCategory();
			
			if ( false == inscriptos_por_categoria.containsKey( categoria ) ) {
				// si no tiene la categoria agregada como key del mapa, entonces la agrego
				// esto lo hago primero para dsp operar sabiendo que ya esta creada la entrada
				// en el map para esa categoria.
				
				// creo el nuevo array:
				// 					 nuevo_array[0] = suma parcial de aprobados
				// 					 nuevo_array[1] = suma parcial de desaprobados
				// 					 nuevo_array[2] = suma parcial de abandonaron
				ArrayList<Integer> nuevo_array = new ArrayList<Integer>(3);
				nuevo_array.add(0);
				nuevo_array.add(0);
				nuevo_array.add(0);
				inscriptos_por_categoria.put( categoria, nuevo_array );
			}
			
			Integer suma_parcial_aprobados_actualizada = inscriptos_por_categoria.get( categoria ).get(0) + reporte.getPass();
			inscriptos_por_categoria.get( categoria ).set(0, suma_parcial_aprobados_actualizada);

			Integer suma_parcial_desaprobados_actualizada = inscriptos_por_categoria.get( categoria ).get(1) + reporte.getNoPass();
			inscriptos_por_categoria.get( categoria ).set(1, suma_parcial_desaprobados_actualizada);			

			Integer suma_parcial_abandonaron_actualizada = inscriptos_por_categoria.get( categoria ).get(2) + reporte.getGiveUp();
			inscriptos_por_categoria.get( categoria ).set(2, suma_parcial_abandonaron_actualizada);
		}
		
		// el número de posición en los arrays corresponde a una misma categoria.
		// por ej:
		//        nombres_categorias_json[3] = COMPUTACION
		//        cantidad_aprobados_por_categoria_json[3] = cant de aprobados en la categoria.
		//        cantidad_desaprobados_por_categoria_json[3] = cant de desaprobados en la categoria.
		//        cantidad_abandonaron_por_categoria_json[3] = cant de abandonos en la categoria.		
		
		JSONArray nombres_categorias_json = new JSONArray();
		JSONArray cantidad_aprobados_por_categoria_json = new JSONArray();
		JSONArray cantidad_desaprobados_por_categoria_json = new JSONArray();
		JSONArray cantidad_abandonaron_por_categoria_json = new JSONArray();
		
		List<Map.Entry<String, ArrayList<Integer>>> ordenado = new LinkedList<Map.Entry<String, ArrayList<Integer>>>(inscriptos_por_categoria.entrySet());
		
		Collections.sort(ordenado, new ComparadorPorTotalInscriptosMapaInscriptos() );
		
		for (Map.Entry<String, ArrayList<Integer>> entry : ordenado) {
		    String categoria = entry.getKey();
		    ArrayList<Integer> cantidad_aprobados_desaprobados_abandonaron = entry.getValue();
		    
			nombres_categorias_json.put( categoria );
			cantidad_aprobados_por_categoria_json.put( cantidad_aprobados_desaprobados_abandonaron.get(0) );
			cantidad_desaprobados_por_categoria_json.put( cantidad_aprobados_desaprobados_abandonaron.get(1) );
			cantidad_abandonaron_por_categoria_json.put( cantidad_aprobados_desaprobados_abandonaron.get(2) );
		}
		
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
	
	private ArrayList<Report> filtrarPorNombreReportesPertenecientesADocente( ArrayList<Report> reportes_cursos, Integer teacher_id ) {
		
		List<Course> cursos_profesor = (ArrayList<Course>) Course.getByTeacherId( teacher_id );

		ArrayList<Report> filtro = new ArrayList<Report>();
		
		for ( Report reporte_curso : reportes_cursos ) {
			
			for ( Course curso_profesor : cursos_profesor ) {
				
				if ( 0 == reporte_curso.getCourseName().compareTo( curso_profesor.getName() ) ) {
					// si matchean, entonces lo agrego al array que voy a devolver.
					filtro.add( reporte_curso );
				}
			}
		}
		
		return filtro;		
	}
	
	private ArrayList<Report> filtrarPorIdReportesPertenecientesADocente( ArrayList<Report> reportes_cursos, Integer teacher_id ) {
		
		List<Course> cursos_profesor = (ArrayList<Course>) Course.getByTeacherId( teacher_id );

		ArrayList<Report> filtro = new ArrayList<Report>();
		
		for ( Report reporte_curso : reportes_cursos ) {
			
			for ( Course curso_profesor : cursos_profesor ) {
				
				if ( reporte_curso.getCourseId() == curso_profesor.getId() ) {
					// si matchean, entonces lo agrego al array que voy a devolver.
					filtro.add( reporte_curso );
				}
			}
		}
		
		return filtro;		
	}	
	
	private boolean esAdmin( String viewer ) {
		if ( 0 == viewer.compareTo( "admin" ) ) {
			return true;
		}
		else {
			return false;
		}	
	}
	
}
