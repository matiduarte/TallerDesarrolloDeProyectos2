<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

  <div class="row">
  <c:forEach items="${list}" var="courses">
    <div class="col-md-4">
    	<div id="img_container">
    	<span class="my_badge btn_course badge">${courses.getName()}</span>
    	<c:choose>
    	<c:when test="${courses.getPictureUrl() != NULL}">
          <img src="${courses.getPictureUrl()}" class="img-rounded" style="width:180px;height:180px">
          
          </c:when>
          <c:otherwise>
            	<img src="bootstrap/img/no-img.png" class="img-rounded" style="width:180px;height:180px">
          </c:otherwise>
          </c:choose>
          <input type="button" class="btn_ver btn btn-sm btn-raised btn-primary" value="Ver más" />
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
	
	


  </body>
</html>