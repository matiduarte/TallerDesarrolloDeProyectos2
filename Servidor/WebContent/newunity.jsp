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
    <link href="bootstrap/css/newunity.css" rel="stylesheet">
 	
 	
 </head>
  <body>
<div class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
      </button>
      <a class="navbar-brand" href="javascript:void(0)">Crear Unidad</a>
    </div>
    <div class="navbar-collapse collapse navbar-inverse-collapse"> 
    </div>
  </div>
</div>
 
<form id="identicalForm" class="register" method="post" action="newunity">
<input type="hidden" name="courseId" value="${courseId}">
<c:if test="${id != NULL}">
  <input type="hidden" name="id" value="${id}">
</c:if>

  <div class="form-group label-floating">
    <label class="control-label" for="name">Nombre</label>
    <c:choose>
    	<c:when test="${name != NULL}">
  <input class="form-control" id="name" name="name" type="text" value="${name}" required>
  </c:when>
          <c:otherwise>
          <input class="form-control" id="name" name="name" type="text" required>
          </c:otherwise>
   </c:choose>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="lastName">Descripción</label>
    <c:choose>
    	<c:when test="${description != NULL}">
  <input class="form-control" id="description" name="description" type="text" value="${description}" required>
  </c:when>
          <c:otherwise>
          <input class="form-control" id="description" name="description" type="text" required>
          </c:otherwise>
   </c:choose>
  </div>
 <c:choose>
    	<c:when test="${id != NULL}">
  		<button class="btn btn-raised btn-primary pull-right" name="create_btn" type="submit">Editar</button>
  </c:when>
          <c:otherwise>
  <button class="btn btn-raised btn-primary pull-right" name="create_btn" type="submit">Crear</button>
          </c:otherwise>
   </c:choose>
   <button class="btn-back btn btn-primary pull-left" onclick="cancelar(${courseId})" type="button">Cancelar</button>
	</form>

	<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/ripples.min.js"></script>
	<script src="bootstrap/js/material.min.js"></script>

	<script src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
	<script src="bootstrap/js/floating-label.js"></script>
	<script type="text/javascript">
		
	function cancelar(id){	
		window.location.href = '/Servidor/courseDetail?id=' + id;
	}	
	
	</script>
  </body>
</html>