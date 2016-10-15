<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html lang="en-us">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="bootstrap/img/icono.ico">
    
    <!-- Bootstrap -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Material Design -->
    <link href="bootstrap/css/bootstrap-material-design.css" rel="stylesheet">
    <link href="bootstrap/css/ripples.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
    <link href="bootstrap/css/courseList.css" rel="stylesheet">
    
 	<title>Cursos</title>
 	
 </head>
  <body data-spy="scroll" data-target=".navbar" data-offset="50">  
  
<div class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
      </button>
     
      <img class="navbar-brand" src="bootstrap/img/icono.ico" alt="Logo" style="width:60px;height:60px;">
     
      <a class="navbar-brand">FIUBA Cursos</a>
    </div>
    <div class="navbar-collapse collapse navbar-inverse-collapse"> 
    </div>
  </div>
</div>



<div class="courses_list container">

<%

		if(request.getAttribute("noCourse") != null){
%>

	<div class="alerta alert alert-info">
  		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
  		<strong>No posee cursos asignados por el momento!</strong>
	</div>
 
 <%

		}	

%>


  <div id="lista_cursos" class="row">
  <c:forEach items="${list}" var="courses">
    <div class="${col}">
    	<div id="img_container" class="img_container">
        
        <h4 class="fondo-verde position-absolute ancho-completo espaciado-chico borde-redondeado"><strong>${courses.getName()}</strong>
        
	   	<c:if test="${courses.hasActiveSession()}">
	   		<span class="badge progress-bar-success borde-fino">Activo</span>
	   	</c:if>
	   	<c:if test="${!courses.hasActiveSession()}">
	   		<span class="badge progress-bar-danger borde-fino">No activo</span>
	   	</c:if>
        
        </h4>
              	
     	<c:choose>
    	<c:when test="${courses.getPictureUrl() != NULL}">
          <img src="${courses.getPictureUrl()}" class="img-rounded img-responsive" style="${max}">
          </c:when>
          <c:otherwise>
            	<img src="bootstrap/img/nodisponible.jpg" class="img-rounded img-responsive" style="${max}">
        </c:otherwise>
        </c:choose>

          <button class="btn btn-sm ${btn_ver} btn-raised btn-primary" onclick="getCourseId(${courses.getId()});">Ver más</button>
   		</div>
    </div>
      </c:forEach>
  </div>

</div>



	<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/ripples.min.js"></script>
	<script src="bootstrap/js/material.min.js"></script>

	<script src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
	<script src="bootstrap/js/floating-label.js"></script>
	<script type="text/javascript">
	
	
		function getCourseId(id){
			window.location.href = '/Servidor/courseDetail?id=' + id;
		}
	
		function ordenarLista() {
	    	var tbody = $('#lista_cursos');
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
	
	</script>
	


  </body>
</html>