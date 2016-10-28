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
    <link href="bootstrap/css/forum.css" rel="stylesheet">
 
  <!-- Material Design fonts -->
  <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Roboto:300,400,500,700">
  <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/icon?family=Material+Icons">
 	
 </head>
  <body>
<div class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
      </button>
      <a class="navbar-brand" href="javascript:void(0)">Foro</a>
    </div>
    <div class="navbar-collapse collapse navbar-inverse-collapse"> 
    </div>
  </div>
</div>

<form id="identicalForm" class="register" method="post" action="forummessage">
<%if(request.getAttribute("sessionId") != null) {%>
  <input type="hidden" name="sessionId" id="sessionId" value="${sessionId}">
<%} %>

<%if(request.getAttribute("courseId") != null) {%>
  <input type="hidden" name="courseId" id="courseId" value="${courseId}">
<%} %>
<c:set var="count" value="0" scope="page" />
<c:forEach var="forum" items="${forumList}">
<div class="message list-group">
  <div class="list-group-item">
    <div class="row-content">
    <div class="form-group">
    
  
	<c:choose>
    	<c:when test="${moderate == 1}">
    	<div style="float:right ; margin-right:40px ;margin-top:16px">${forum.getTime()}</div>
      <div class="action-secondary" onclick="changeLabel('${count}')" >
      	<i class="material-icons">highlight_off</i>
      </div>
       </c:when>
          <c:otherwise>
          <div style="float:right ;margin-top:16px">${forum.getTime()}</div>
          </c:otherwise>
   </c:choose>
  <label class="control-label" for="inputDefault">${forum.getName()}</label>
  <c:choose>
    	<c:when test="${forum.getIsModerate()}">
  <input type="text" class="form-control" style="color:red;" name="message[]" id="message${count}" value="${forum.getMessage()}">
  </c:when>
          <c:otherwise>
          <input type="text" class="form-control" name="message[]" id="message${count}" value="${forum.getMessage()}">
          </c:otherwise>
   </c:choose>
</div>
    </div>
  </div>
  </div>
  <c:set var="count" value="${count + 1}" scope="page"/>
 </c:forEach>
<button class="btn btn-raised btn-primary pull-right" name="finalizar" type="submit">Guardar</button>
  <button class="btn-back btn btn-primary pull-left" onclick="volver(${courseId})" type="button">Volver</button>
</form>

	<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/ripples.min.js"></script>
	<script src="bootstrap/js/material.min.js"></script>

	<script src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
	<script src="bootstrap/js/floating-label.js"></script>
	
	<script type="text/javascript">
	
	function volver(courseId){
		window.location.href = "/Servidor/courseDetail?id=" + courseId;
	}
	
	function changeLabel(id){
		$("#message"+id).val("Este mensaje ha sido borrado debido a que el contenido era inapropiado.");
		$("#message"+id).css("color","red");
	}
	</script>
	
  </body>
</html>