<!DOCTYPE html>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entities.Category" %>
<%@ page import="entities.Course" %>
<%@ page import="entities.User" %>
<%@ page import="entities.CourseSession" %>
<%@ page import="entities.CourseUnity" %>

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
			<input type="hidden" name="courseId" id="courseId" value="<% out.print(course.getId()); %>">
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
				
				</br>	
			    <div>	
			    	<label class="detail-label">Categorias:</label>
			    	<label class="detail-value"><% out.print(request.getAttribute("categories")); %></label>
				</div>
		     
		     </div>   
	     </div>
	     </br>
	     </br>
	     
	     <div>
	     	<div class="tableSessionContainer">
			     <label class="detail-label">Sesiones activas:</label>
			     <button class="btn btn-raised btn-primary newCourseButton btnNew" onclick="showPopup();">Crear nueva<br/>sesion</button>
			     
			     <table class="tg" id="tableSession">
					  <tr>
					    <th class="tg-zyzu">Id</th>
					    <th class="tg-zyzu">Fecha de inicio</th>
					    <th class="tg-zyzu">Acciones</th>
					  </tr>
					   <%ArrayList<CourseSession> courseSessions = (java.util.ArrayList)request.getAttribute("courseSessions");
						 for (CourseSession courseSession: courseSessions)
						 { %>
						 	<tr id="tr_session_<%  out.print(courseSession.getId()); %>">
							    <td class="tg-yw4l">
							    	<%  out.print(courseSession.getId()); %>
							    </td>
							    <td class="tg-yw4l">
							    	<%  out.print(courseSession.getDate()); %>
							    </td>
							    <td class="tg-yw4l">
							    	<button class="btn btnAction" type="submit" onclick="editSession(<%  out.print(courseSession.getId()); %>)">
										<img  src="images/edit_icon.png" class="actionButtonImage" alt="Editar" >
									</button>
									
									<button class="btn btnAction" type="submit" onclick="deleteSession(<%  out.print(courseSession.getId()); %>)">
										<img  src="images/delete_icon.png" class="actionButtonImage" alt="Borrar" >
									</button>
							    </td>
						  </tr>
						<%}%>
				</table>
			</div>
			<div>
				<div class="tableUnityContainer">
				     <label class="detail-label">Unidades:</label>
				     <button class="btn btn-raised btn-primary newCourseButton btnNew" onclick="editUnity()">Crear unidad</button>
				     
				     <table class="tg">
						  <tr>
						    <th class="tg-zyzu">Id</th>
						    <th class="tg-zyzu">Nombre</th>
						    <th class="tg-zyzu">Acciones</th>
						  </tr>
						   <%ArrayList<CourseUnity> courseUnities = (java.util.ArrayList)request.getAttribute("courseUnities");
							 for (CourseUnity courseUnity: courseUnities)
							 { %>
							 	<tr id="tr_unity_<%  out.print(courseUnity.getId()); %>">
								    <td class="tg-yw4l">
								    	<%  out.print(courseUnity.getId()); %>
								    </td>
								    <td class="tg-yw4l">
								    	<%  out.print(courseUnity.getName()); %>
								    </td>
								    <td class="tg-yw4l">
								    	<button class="btn btnAction" type="submit" onclick="editUnity(<%  out.print(courseUnity.getId()); %>)">
											<img  src="images/edit_icon.png" class="actionButtonImage" alt="Editar" >
										</button>
										
										<button class="btn btnAction" type="submit" onclick="deleteUnity(<%  out.print(courseUnity.getId()); %>)">
											<img  src="images/delete_icon.png" class="actionButtonImage" alt="Borrar" >
										</button>
								    </td>
							  </tr>
							<%}%>
					</table>
				</div>
			</div>
		</div>
	     
	     <br/>
	     <br>
	     
	    <button class="btn btn-raised btn-primary newCourseButton btnBack" type="submit" onclick="goBack();" >Volver</button>

	<div id="newSessionPopup">
		<input type="hidden" name="sessionId" id="sessionId" value="">
		<label class="labelPopup" id="popupSessionTitle">Crear sesi�n</label>
		<br/>
		
		<div class="form-group label-floating inputSessionDate">	
			<label class="control-label" id="labelDate" for="sessionDate">Fecha de inicio</label>
			<input type="text" id="sessionDate" class="form-control"/>
		</div>
		
		<button class="btn btnCalendar" type="submit" onclick="showCalendar()">
			<img  src="images/calendar_icon.png" class="calendarIcon" alt="Agregar fecha de inicio" >
		</button>
		
		<br/>
		<br/>
		<hr>
		
		<div class="popupButtonsContainer">
			<button class="btn btnPopup" type="submit" onclick="hidePopup();">Cancelar</button>
			<button class="btn btnPopup" type="submit" onclick="saveSession()" id="popupSessionSubmit">Crear</button>
		</div>
	</div>
	
    </div> <!-- /container -->
    
    <script type="text/javascript">	    
	    $( "#sessionDate" ).datepicker()
    
    
    	function showPopup(){
	    	$("#sessionId").val("")
    		$("#popupSessionTitle")[0].innerHTML = "Crear Sesi�n";
    		$("#popupSessionSubmit")[0].innerHTML = "Crear";
    		$("#sessionDate").val("");
    		$("#newSessionPopup").show();
    	}
    	
    	function hidePopup(){
    		$("#newSessionPopup").hide();
    	}
    	
    	function showCalendar(){
    		$( "#sessionDate" ).datepicker("show");
    	}
    	
    	function saveSession(){
    		var sessionDate = $('#sessionDate').val();
    		var courseId = $('#courseId').val();
    		var sessionId = $('#sessionId').val();
			
			$.ajax({
			    data: {sessionDate: sessionDate, courseId: courseId, sessionId: sessionId},
			    //Cambiar a type: POST si necesario
			    type: "POST",
			    // Formato de datos que se espera en la respuesta
			    dataType: "json",
			    // URL a la que se enviar� la solicitud Ajax
			    url: "SaveCourseSessionActionServlet",
			})
			 .done(function( data, textStatus, jqXHR ) {
				 if(sessionId != ""){
					 editSessionRow(data);
				 }else{
					 addNewSessionRow(data);	 
				 }
				 hidePopup();
			 })
			 .fail(function( jqXHR, textStatus, errorThrown ) {
			     if ( console && console.log ) {
			         console.log( "La solicitud a fallado: " +  textStatus);
			     }
			});
    	}
    	
    	function editSessionRow(session){
    		$("#tr_session_" + session.id).children()[1].innerHTML = session.date;
    	}
    	
    	function addNewSessionRow(session){
    		$('#tableSession tr:last').after("<tr id='tr_session_" + session.id + "'>"+
    				"<td class='tg-yw4l'>" +
	    				session.id +
	    			"</td><td class='tg-yw4l'>" +
	    				session.date +
	    			"</td><td class='tg-yw4l'>"+
	    	"<button class='btn btnAction' type='submit' onclick='editSession(" + session.id + ")'>"+
				"<img  src='images/edit_icon.png' class='actionButtonImage' alt='Agregar fecha de inicio' ></button>"+
			
			"<button class='btn btnAction' type='submit' onclick='deleteSession(" + session.id + ")'>"+
				"<img  src='images/delete_icon.png' class='actionButtonImage' alt='Agregar fecha de inicio' ></button></td></tr>");
    	}
    	
    	function deleteSessionRow(sessionId){
    		$("#tr_session_" + sessionId).remove();
    	}
    	
    	function editSession(sessionId){
    		$("#sessionId").val(sessionId);
    		$("#popupSessionTitle")[0].innerHTML = "Editar Sesi�n";
    		$("#popupSessionSubmit")[0].innerHTML = "Guardar";
    		$("#newSessionPopup").show();
    		//HACK
    		$( "#sessionDate" ).focus();
    		$("#sessionDate").val($("#tr_session_" + sessionId).children()[1].innerHTML.replace(/\s/g, ""));
    	}
    	
    	function deleteSession(sessionId){	
			$.ajax({
			    data: {sessionId: sessionId},
			    //Cambiar a type: POST si necesario
			    type: "POST",
			    // Formato de datos que se espera en la respuesta
			    dataType: "json",
			    // URL a la que se enviar� la solicitud Ajax
			    url: "DeleteCourseSessionActionServlet",
			})
			 .done(function( data, textStatus, jqXHR ) {
				 deleteSessionRow(sessionId);
			 })
			 .fail(function( jqXHR, textStatus, errorThrown ) {
			     if ( console && console.log ) {
			         console.log( "La solicitud a fallado: " +  textStatus);
			     }
			});
    	}
    	
    	function deleteUnity(unityId){	
			$.ajax({
			    data: {unityId: unityId},
			    //Cambiar a type: POST si necesario
			    type: "POST",
			    // Formato de datos que se espera en la respuesta
			    dataType: "json",
			    // URL a la que se enviar� la solicitud Ajax
			    url: "DeleteCourseUnityActionServlet",
			})
			 .done(function( data, textStatus, jqXHR ) {
				 deleteUnityRow(unityId);
			 })
			 .fail(function( jqXHR, textStatus, errorThrown ) {
			     if ( console && console.log ) {
			         console.log( "La solicitud a fallado: " +  textStatus);
			     }
			});
    	}
    	
    	function deleteUnityRow(unityId){
    		$("#tr_unity_" + unityId).remove();
    	}
    	
    	function editUnity(unityId){
    		var courseId = $('#courseId').val();
    		if(unityId == undefined){
    			unityId = "";
    		}
    		
    		window.location.href = "newunity?courseId=" + courseId + "&id=" + unityId;
    	}
    	
    	function goBack(){
			window.location.href = "principalAdmin.jsp";
		}
    
    </script>
  </body>
</html>