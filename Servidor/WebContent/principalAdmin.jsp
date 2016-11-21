
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
	      <img class="navbar-logo" src="../bootstrap/img/icono.ico">
	      <a class="navbar-brand" href="javascript:void(0)">FIUBA Cursos</a>
	    </div>
	    <div class="navbar-collapse collapse navbar-inverse-collapse">
	      <button class="btn btn-raised pull-right" onclick="goToStats();" >Ver estad�sticas</button>
	    </div>
	  </div>
	</div>
  
    <div class="container">
	<input type="text" id="searchName" class="searchInput" placeholder="Nombre"/>
	<input type="text" id="searchCategory" class="searchInput" placeholder="Categor�as"/>
	<input type="text" id="searchTeacher" class="searchInput" placeholder="Docente"/>
    	<button class="btn btn-raised btn-primary searchButton btnNew" onclick="search();"> 
    		<img  src="../images/search_icon.png" class="searchButtonImage" alt="Buscar" >
		</button>
    	<button class="btn btn-raised btn-primary newCourseButton btnNew" onclick="createCourse();">Nuevo Curso</button>
    	<br>
	<br>
	<div class="alert alert-success" id="deleteSucces" style="display:none;">
		<button onclick="$('#deleteSucces').hide()" class="close" aria-label="close">&times;</button>
  		<span id="deleteSuccesMessage">Curso borrado satisfactoriamente!</span>
	</div>
	<div class="tableCourseContainer">
		<table class="tg" id="tableCourse">
			<tr>
			<th class="tg-zyzu">Nombre</th>
			<th class="tg-zyzu">Descripci�n</th>
			<th class="tg-zyzu">Categor�as</th>
			<th class="tg-zyzu">Docente</th>
			<th class="tg-zyzu">Acciones</th>
			</tr>
			<%ArrayList<TableCourse> tabla_de_cursos = (java.util.ArrayList)request.getAttribute("table_courses");
				for ( TableCourse item : tabla_de_cursos ) { %>
					<tr id="tr_course_<%  out.print(item.getCourse().getId()); %>">
						<td class="tg-yw4l">
							<%  out.print(item.getCourse().getName()); %>
						</td>
						<td class="tg-yw4l contenido-largo">
							<span >
						  	<%  out.print(item.getCourse().getDescription()); %>
						  	</span>
						</td>
						<td class="tg-yw4l">
						  	<%  out.print(String.join(", ", item.getCategoriesNames() )); %>
						</td>
						<td class="tg-yw4l contenido-medio">
						  	<%  out.print(item.getTeacher().getFirstName() + " " + item.getTeacher().getLastName()); %>
						</td>
						<td class="tg-yw4l contenido-corto">
							<button class="btn btnAction" type="submit" onclick="editeCourse(<%out.print(item.getCourse().getId());%>)">
								<img  src="../images/edit_icon.png" class="actionButtonImage" alt="Editar" >
							</button>
							<button class="btn btnAction" onclick="showPopup(<%out.print(item.getCourse().getId());%>)">
								<img  src="../images/delete_icon.png" class="actionButtonImage" alt="Borrar" >
							</button>
						</td>
					</tr>
				<%}%>
		</table>
		<div class="noResultsMsg hide">No se obtuvieron resultados</div>
	</div>

	<div id="deleteCoursePopup">
		<label class="labelPopup" id="popupDeleteCourseTitle">�Est� seguro que quiere eliminar el curso?</label>
		<br/>
		<hr>
		
		<div class="popupButtonsContainer">
			<button class="btn btnPopup" type="submit" onclick="hidePopup();">Cancelar</button>
			<button class="btn btnPopup" type="submit" onclick="deleteCourse()" id="popupDeleteCourseSubmit">Aceptar</button>
		</div>
	</div>

    </div> <!-- /container -->
    
	<div id="deleteCourseId" class="hide"></div>

	<script>
	
	$(document).load( ordenarTabla() );
	
	function editeCourse(courseId) {
		window.location.href = "../editCourse?id=" + courseId;
	}
    
	function showPopup(courseId){
		$('#deleteCourseId').text(courseId);
    		$("#deleteCoursePopup").show();
    	}
    	
    	function hidePopup(){
    		$("#deleteCoursePopup").hide();
		$("#deleteSucces").show();
    	}
	
	function deleteCourse(){
        	var courseId = document.getElementById("deleteCourseId").textContent;
				
		$.ajax({
		    data: {id : courseId},
		    type: "POST",
		    url: "../cursos/admin",
		})
		 .done(function( data, textStatus, jqXHR ) {
			 deleteCourseRow(courseId);
			hidePopup();
		 })
		 .fail(function( jqXHR, textStatus, errorThrown ) {
		     if ( console && console.log ) {
		         console.log( "La solicitud a fallado: " +  textStatus);
		     }
		});
	}
    	
	function deleteCourseRow(courseId){
      	    $("#tr_course_" + courseId).remove();
        }

	function createCourse(){
            window.location.href = "../newCourse";
	}
	
	function search(){
		
		// recupero todos los cursos. me qedo con todas las filas de la tabla excepto la 1era que es el encabezado.
		var rows = $("#tableCourse").find("tr:not(:first)").hide();
		
		// recupero nombre, categorias y docente a filtrar
		var nombre_filtro = $("#searchName").val().toLowerCase();
		var categorias_filtro = $("#searchCategory").val().toLowerCase();
		var docente_filtro = $("#searchTeacher").val().toLowerCase();
    	var match_nombre = false;
    	var match_categorias = false;
    	var match_docente = false;
    	var hay_resultados = false;
		// aplico filtro a cada uno de los cursos (cada fila de la tabla)
		rows.filter( function() {
			// recupero nombre, categorias y docente del curso
			var nombre_curso = $(this).find("td:nth-child(1)").text().toLowerCase();
			var categorias_curso = $(this).find("td:nth-child(3)").text().toLowerCase();
        	var docente_curso = $(this).find("td:nth-child(4)").text().toLowerCase();
        	
        	if ( nombre_curso.indexOf( nombre_filtro ) > -1 || nombre_filtro.length == 0 ) {
        		match_nombre = true;
        	} else match_nombre = false;

        	if ( categorias_curso.indexOf( categorias_filtro ) > -1 || categorias_filtro.length == 0 ) {
        		match_categorias = true;
        	} else match_categorias = false
        	
        	
        	if ( docente_curso.indexOf( docente_filtro ) > -1 || docente_filtro.length == 0 ) {
        		match_docente = true;
        	} else match_docente = false
        	
        	if ( match_nombre && match_categorias && match_docente ) {
        		hay_resultados = true;
        		return true;
        	} else return false;
		}).show();
		debugger
		if( hay_resultados ){
			$(".noResultsMsg").addClass("hide");
		} else {
			$(".noResultsMsg").removeClass("hide");
		}
	}
	
	function ordenarTabla() {
    	var tbody = $('#tableCourse');
    	tbody.find('tr:not(:first)').sort(function(a,b){ 
    	    var nombre1_con_espacios = $(a).find('td:eq(0)').text();
    	    var nombre2_con_espacios = $(b).find('td:eq(0)').text();
    	    
    	    var nombre1 = $.trim(nombre1_con_espacios);
    	    var nombre2 = $.trim(nombre2_con_espacios);
			
    	            // if a < b return 1
    	    return nombre1 > nombre2 ? 1 
    	           // else if a > b return -1
    	           : nombre1 < nombre2 ? -1 
    	           // else they are equal - return 0    
    	           : 0;           
    	}).appendTo(tbody);
	} 	
	
	
	function goToStats(){
		window.location.href = '/Servidor/stats?viewer=admin';
	}
	</script>

  </body>
</html>

