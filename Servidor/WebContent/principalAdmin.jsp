
<!doctype html>
<%@page import="com.mysql.cj.core.util.StringUtils"%>
<%@page import="entities.CourseCategory"%>
<%@page import="controllers.PrincipalAdminController" %>
<%@page import="entities.Category" %>
<%@page import="utils.TableCourse"%>
<%@page import="java.util.*" %>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="../bootstrap/img/icono.ico">

    <title>Cursos</title>

    <!-- Bootstrap -->
    <link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Material Design -->
    <link href="../bootstrap/css/bootstrap-material-design.css" rel="stylesheet">
    <link href="../bootstrap/css/ripples.min.css" rel="stylesheet">

	<link href="//fezvrasta.github.io/snackbarjs/dist/snackbar.min.css" rel="stylesheet">
 	<meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Custom styles for this template -->
    <link href="../bootstrap/css/principalAdmin.css" rel="stylesheet">

	<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="../bootstrap/js/bootstrap.min.js"></script>
	<script src="../bootstrap/js/ripples.min.js"></script>
	<script src="../bootstrap/js/material.min.js"></script>

	<script src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
	<script src="../bootstrap/js/floating-label.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>



  <body>
  
  	<div class="navbar navbar-default">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
	      </button>
	      <a class="navbar-brand" href="javascript:void(0)">Cursos - Admin</a>
	    </div>
	    <div class="navbar-collapse collapse navbar-inverse-collapse"> 
	    </div>
	  </div>
	</div>
  
    <div class="container">
    
     <label class="btn btn-primary btn-file newCourseButton">
   	   Nuevo curso
     </label>
    <br>
    <br>
    <br>
     <div class="table-responsive">
	  <table class="table tabla">
	   <thead>
	     <tr>
	     	<th class="hide-col">id</th>
	     	<th>Nombre</th>
	     	<th>Descripción</th>
	     	<th>Categorías</th>
	     	<th>Docente</th>
	     	<th>Acciones</th>
	     </tr>
	   </thead>
	   <tbody>
		<%
		ArrayList<TableCourse> tabla_de_cursos = (java.util.ArrayList)request.getAttribute("table_courses");
		for ( TableCourse item : tabla_de_cursos ) {
		%><tr class="clickable-row">
		  <td class="hide-col"><%=Integer.toString(item.getCourse().getId())%></td>
		  <td><%=item.getCourse().getName()%></td>
		  <td><%=item.getCourse().getDescription()%></td>
		  <td><%=String.join(", ", item.getCategoriesNames() )%></td>
		  <td><%=item.getTeacher().getFirstName()%></td>
		  <td>
		  <button id="btn_borrar" name="btn_borrar" class="btn btnBorrar" onclick="editar(this)"></button>
		  <button id="btn_editar" name="btn_editar" class="btn btnEditar" data-toggle="modal" data-target="#myModal" onclick="setIdABorrar(this)"></button>
		  </td>
		  </tr>
		<% } %>
	   </tbody>
	   </table>
	  </div>
    </div> <!-- /container -->
    
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">¿Confirma que desea eliminar curso?</h4>
	      </div>
	      <div class="modal-body">
	      Una vez eliminado el curso no podrá crear sesiones del mismo. Para restaurarlo deberá crearlo de nuevo.
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">No, mantener curso</button>
	        <button id="confirmar_borrar" type="button" class="btn btn-primary" data-dismiss="modal">Si, quiero borrarlo</button>
	      </div>
	    </div>
	  </div>
	</div>

	<div id="id_a_borrar" class="hide"></div>

	<script>
	
	function editar(btn) {
		var curso_id = btn.parentElement.parentElement.getElementsByTagName("td")[0].textContent;
		window.location.href = "/Servidor/editCourse?id=" + curso_id;
	}
    
	function setIdABorrar(btn) {
		var curso_id = btn.parentElement.parentElement.getElementsByTagName("td")[0].textContent;
		$('#id_a_borrar').text(curso_id);
	}
	
	function borrarCurso() {
		var curso_id_a_borrar = document.getElementById("id_a_borrar").textContent;
		$('#id_a_borrar').text("FUCK");
	}
	
	$("#confirmar_borrar").click(function(btn) {
		var curso_id_a_borrar = document.getElementById("id_a_borrar").textContent;
		
		$.post('../cursos/admin', { id : curso_id_a_borrar });
		
		});
	
	</script>

  </body>
</html>

