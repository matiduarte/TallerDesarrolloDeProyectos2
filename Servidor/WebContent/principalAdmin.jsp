
<!doctype html>
<%@page import="entities.CourseCategory"%>
<%@page import="controllers.PrincipalAdminController" %>
<%@page import="entities.CourseCategory"%>
<%@page import="entities.Course"%>
<%@page import="java.util.*" %>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="bootstrap/img/icono.ico">

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
    <div class="container">
     <div class="table-responsive">
	  <table class="table">
	   <thead>
	     <tr>
	     	<th>Nombre</th>
	     	<th>Descripción</th>
	     	<th>Categorías</th>
	     	<th>Docentes</th>
	     	<th>Editar</th>
	     	<th>Eliminar</th>
	     </tr>
	   </thead>
	   <tbody>
		<%
		Course cursito = new Course();
		PrincipalAdminController.TableCourse
		for ( TableCourse curso : PrincipalAdminController.getAllTableCourses() ) {
		%><tr>
		  <td> <%=curso.getName()%> </td>
		  <td> <%=curso.getDescription()%> </td>
		  <td> <%=curso.getCategories()%> </td>
		  <td> <%=curso.getDocentes()%> </td>
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

