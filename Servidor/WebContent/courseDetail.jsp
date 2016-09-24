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
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/ripples.min.js"></script>
	<script src="bootstrap/js/material.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="bootstrap/js/floating-label.js"></script>
	
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
    	<div class="staticInfoContainer">
			<input type="hidden" name="id" value="<% out.print(course.getId()); %>">
		       <% 
			String pictureUrl = "images/photo_upload.png";
			if(course.getPictureUrl() != null && course.getPictureUrl() != ""){
				pictureUrl = course.getPictureUrl(); 
			} %>
			<img src="<% out.print(pictureUrl); %>" alt="Foto para la categoria" class="newCurseImage" id="imageHolder">
		
		    <div>
			   	<div>
			    	<label class="detail-label">Nombre:</label>
			    	<label class="detail-value"><% out.print(course.getName()); %></label>
			   	</div>
			    	
			    </br>	
			    <div>	
			    	<label class="detail-label">Descripci&oacute;n:</label>
			    	<label class="detail-value"><% out.print(course.getDescription()); %></label>
				</div>
		     
		     </div>   
	     </div>
	     </br>
	     </br>
	     
	     <div>
	     	<div class="tableSessionContainer">
			     <label class="detail-label">Sesiones activas:</label>
			     <button class="btn btn-raised btn-primary newCourseButton btnNew" onclick="showPopup();">Crear nueva<br/>sesion</button>
			     
			     <table class="tg">
					  <tr>
					    <th class="tg-zyzu">Id</th>
					    <th class="tg-zyzu">Fecha de inicio</th>
					    <th class="tg-zyzu">Acciones</th>
					  </tr>
					  <tr>
					    <td class="tg-yw4l"></td>
					    <td class="tg-yw4l"></td>
					    <td class="tg-yw4l"></td>
					  </tr>
					  <tr>
					    <td class="tg-yw4l"></td>
					    <td class="tg-yw4l"></td>
					    <td class="tg-yw4l"></td>
					  </tr>
				</table>
			</div>
			<div>
				<div class="tableUnityContainer">
				     <label class="detail-label">Unidades:</label>
				     <button class="btn btn-raised btn-primary newCourseButton btnNew">Crear unidad</button>
				     
				     <table class="tg">
						  <tr>
						    <th class="tg-zyzu">Id</th>
						    <th class="tg-zyzu">Fecha de inicio</th>
						    <th class="tg-zyzu">Acciones</th>
						  </tr>
						  <tr>
						    <td class="tg-yw4l"></td>
						    <td class="tg-yw4l"></td>
						    <td class="tg-yw4l"></td>
						  </tr>
						  <tr>
						    <td class="tg-yw4l"></td>
						    <td class="tg-yw4l"></td>
						    <td class="tg-yw4l"></td>
						  </tr>
					</table>
				</div>
			</div>
		</div>
	     
	     <br/>
	     <br>
	     
	    <button class="btn btn-raised btn-primary newCourseButton btnBack" type="submit">Volver</button>

	<div id="newSessionPopup">
		<label class="labelPopup">Crear sesión</label>
		<br/>
		
		<div class="form-group label-floating inputSessionDate">	
			<label class="control-label" id="labelDate" for="sessionDate">Fecha de inicio</label>
			<input type="text" id="sessionDate" class="form-control">
		</div>
		
		<button class="btn btnCalendar" type="submit" onclick="showCalendar()">
			<img  src="images/calendar_icon.png" class="calendarIcon" alt="Agregar fecha de inicio" >
		</button>
		
		<br/>
		<br/>
		<hr>
		
		<div class="popupButtonsContainer">
			<button class="btn btnPopup" type="submit" onclick="showHide();">Cancelar</button>
			<button class="btn btnPopup" type="submit" onclick="">Crear</button>
		</div>
	</div>
	
    </div> <!-- /container -->
    
    <script type="text/javascript">	    
	    $( "#sessionDate" ).datepicker()
    
    
    	function showPopup(){
    		$("#newSessionPopup").show();
    	}
    	
    	function showHide(){
    		$("#newSessionPopup").hide();
    	}
    	
    	function showCalendar(){
    		$( "#sessionDate" ).datepicker("show");
    	}
    
    </script>
  </body>
</html>
