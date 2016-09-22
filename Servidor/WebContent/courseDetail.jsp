<!DOCTYPE html>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entities.Category" %>
<%@ page import="entities.Course" %>
<%@ page import="entities.User" %>

<% Course course = (Course)request.getAttribute("course"); 
%> 
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="bootstrap/img/icono.ico">
    <title>Detalle del curso</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="bootstrap/css/bootstrap-tagsinput.css" rel="stylesheet">
	<link href="bootstrap/css/bootstrap-material-design.min.css" rel="stylesheet">
    <link href="bootstrap/css/ripples.min.css" rel="stylesheet">
    <link href="bootstrap/css/courseDetail.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/ripples.min.js"></script>
	<script src="bootstrap/js/material.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
	
	
  </head>

  <body>
	
	<div class="navbar navbar-default">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
	      </button>
	      <a class="navbar-brand" href="javascript:void(0)">Detalle del curso</a>
	    </div>
	    <div class="navbar-collapse collapse navbar-inverse-collapse"> 
	    </div>
	  </div>
	</div>
	
    <div class="container">
    	
	<input type="hidden" name="id" value="<% out.print(course.getId()); %>">
       <% 
	String pictureUrl = "images/photo_upload.png";
	if(course.getPictureUrl() != null && course.getPictureUrl() != ""){
		pictureUrl = course.getPictureUrl(); 
	} %>
	<img src="<% out.print(pictureUrl); %>" alt="Foto para la categoria" class="newCurseImage" id="imageHolder">

       <div class="form-group label-floating">	
       	<label class="control-label" for="inputName">Nombre(*)</label>
       	<input type="text" id="inputName" name="name" required="true"  class="form-control" value="<% out.print(course.getName()); %>" 
       			required>
	</div>
       
       <div class="form-group label-floating">	
       	<label class="control-label" for="inputDescription">Descripci&oacute;n(*)</label>
       	<input type="text" id="inputDescription" name="description" required="true" class="form-control" value="<% out.print(course.getDescription()); %>" required>
       </div>
       
       <div class="form-group label-floating">	
       	<label class="control-label" id="labelCategories" for="categories">Categor&iacute;as(*)</label>
       	<input type="text" id="categories" name="categories" class="form-control">
      </div>
       
      <div class="form-group label-floating ui-widget">	
       	<label class="control-label" for="inputTeacher">Docente</label>
       	<input type="text" id="inputTeacher" name="teacher" required="false" class="form-control" value="<% out.print(request.getAttribute("currentTeacherName")); %>" >
       	
       	<input type="hidden" id="teacherSelectedId" name="teacherSelectedId" value="<% out.print(course.getTeacherId()); %>">
       </div>
       
       </br>
       
       <button class="btn btn-primary btn-file backeButton" onclick="goBack();return false">Volver</button>
       <button class="btn btn-raised btn-primary newCourseButton btn" type="submit">Volver</button>


    </div> <!-- /container -->
  </body>
</html>
