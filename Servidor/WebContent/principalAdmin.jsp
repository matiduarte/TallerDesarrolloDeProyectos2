
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
	  <table class="table">
	   <thead>
	     <tr>
	     	<th>Nombre</th>
	     	<th>Descripción</th>
	     	<th>Categorías</th>
	     	<th>Docente</th>
	     	<th>Editar</th>
	     	<th>Borrar</th>
	     </tr>
	   </thead>
	   <tbody>
		<%
		ArrayList<TableCourse> tabla_de_cursos = (java.util.ArrayList)request.getAttribute("table_courses");
		for ( TableCourse item : tabla_de_cursos ) {
		%><tr>
		  <td> <%=item.getCourse().getName()%> </td>
		  <td> <%=item.getCourse().getDescription()%> </td>
		  <td> <%=String.join(", ", item.getCategoriesNames() )%> </td>
		  <td> <%=item.getTeacher().getFirstName()%> </td>
		  <td> edit </td>
		  <td> erase </td>
		<% } %>   	
	   </tbody>
	   </table>
	  </div>
    </div> <!-- /container -->
    


	<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="../bootstrap/js/bootstrap.min.js"></script>
	<script src="../bootstrap/js/ripples.min.js"></script>
	<script src="../bootstrap/js/material.min.js"></script>

	<script src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
	<script src="../bootstrap/js/floating-label.js"></script>
	<script>	
		function irAInicarSesion(){	
			window.location.href = "/Servidor/signin";
	}
	</script>

  </body>
</html>

