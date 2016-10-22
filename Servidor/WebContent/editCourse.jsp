<!DOCTYPE html>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entities.Category" %>
<%@ page import="entities.Course" %>
<%@ page import="entities.CourseSession" %>
<%@ page import="entities.CourseUnity" %>
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
    <title>Editar curso</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="bootstrap/css/bootstrap-tagsinput.css" rel="stylesheet">
	<link href="bootstrap/css/bootstrap-material-design.min.css" rel="stylesheet">
    <link href="bootstrap/css/ripples.min.css" rel="stylesheet">
    <link href="bootstrap/css/newCourse.css" rel="stylesheet">
    <link href="bootstrap/css/courseDetail.css" rel="stylesheet">
    <link href="bootstrap/css/editCourse.css" rel="stylesheet">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="bootstrap/js/bootstrap-tagsinput.js"></script>
	<script src="bootstrap/js/typeahead.bundle.js"></script>
	
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/ripples.min.js"></script>
	<script src="bootstrap/js/bootbox.min.js"></script>
	<script src="bootstrap/js/material.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
	<script src="bootstrap/js/floating-label.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	
	
  </head>

  <body>
	
	<div class="navbar navbar-default">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
	      </button>
	      <a class="navbar-brand" href="javascript:void(0)">Editar curso</a>
	    </div>
	    <div class="navbar-collapse collapse navbar-inverse-collapse"> 
	    </div>
	  </div>
	</div>
	
    <div class="container">
    	
	   	<div class="alert alert-danger" id="pictureError" style="display:none;">
	   		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		  	La foto elegida supera el tamaño máximo de 5 MB permitido. Seleccione otra e intente nuevamente
		</div>
		
		<div class="alert alert-success" id="saveSucces" style="display:none;">
	   		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		  	Curso modificado satisfactoriamente!
		</div>

		<div class="alert alert-danger" id="saveErrorTeacher" style="display:none;">
	   		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		  	No se puede cambiar/eliminar el docente de un curso con sesion activa!
		</div>

      <form id="editCurseForm" name="editCurseForm" method="post" action="editCourse" class="form-signin" enctype="multipart/form-data">
        <input type="hidden" name="id" value="<% out.print(course.getId()); %>">
        <% 
		String pictureUrl = "images/photo_upload.png";
		if(course.getPictureUrl() != null && course.getPictureUrl() != ""){
			pictureUrl = course.getPictureUrl(); 
		} %>
		<img src="<% out.print(pictureUrl); %>" alt="Foto para la categoria" class="newCurseImage" id="imageHolder">
        
        <label class="btn btn-primary btn-file addImageButton">
		    Agregar Foto <input type="file" style="display: none;" id="picture" name="picture" onchange="if(fileSizeValidated(this))readURL(this);">
		</label>
		
		<span>
        	<label class="maxPictureLabel">Tama&ntilde;o m&aacute;ximo: 5 mb</label>
		</span>
		
		
		
        </br>
        </br>
        </br>
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
        
        
        <div class="row">
         <div class="col-md-8">
	     <label class="detail-label">Sesiones activas:</label>
	     <button class="btn btn-raised btn-primary newCourseButton btnNew" onclick="showPopup();return false">Crear nueva sesión</button>
	     
	     <table class="tg ocupar-espacio" id="tableSession">
			  <tr>
			    <th class="tg-zyzu no-visible">Id</th>
			    <th class="tg-zyzu">Fecha de inicio</th>
			    <th class="tg-zyzu">Acciones</th>
			  </tr>
			   <%ArrayList<CourseSession> courseSessions = (java.util.ArrayList)request.getAttribute("courseSessions");
				 for (CourseSession courseSession: courseSessions)
				 { %>
				 	<tr id="tr_session_<%  out.print(courseSession.getId()); %>">
					    <td class="tg-yw4l no-visible">
					    	<%  out.print(courseSession.getId()); %>
					    </td>
					    <td class="tg-yw4l">
					    	<%  out.print(courseSession.getDate()); %>
					    </td>
					    <td class="tg-yw4l">
					    	<button class="btn btnAction" type="submit" onclick="editSession(<%  out.print(courseSession.getId()); %>);return false">
								<img  src="images/edit_icon.png" class="actionButtonImage" alt="Editar" >
							</button>
							
							<button class="btn btnAction" type="submit" onclick="deleteSession(<%  out.print(courseSession.getId()); %>);return false">
								<img  src="images/delete_icon.png" class="actionButtonImage" alt="Borrar" >
							</button>
					    </td>
				  </tr>
				<%}%>
		  </table>
		 </div>
		 
		 <div class="col-md-4">
	     <label class="detail-label">Unidades:</label>
	     
	     <table class="tg ocupar-espacio" id="tableUnity">
			  <tr>
			    <th class="tg-zyzu no-visible">Id</th>
			    <th class="tg-zyzu">Nombre</th>
			  </tr>
			   <%ArrayList<CourseUnity> courseUnities = (java.util.ArrayList)request.getAttribute("courseUnities");
				 for (CourseUnity courseUnity: courseUnities)
				 { %>
				 	<tr id="tr_unity_<%  out.print(courseUnity.getId()); %>">
					    <td class="tg-yw4l no-visible">
					    	<%  out.print(courseUnity.getId()); %>
					    </td>
					    <td class="tg-yw4l">
					    	<%  out.print(courseUnity.getName()); %>
					    </td>
				  </tr>
				<%}%>
		</table>
		</div>		
		
		</div>

	   	<div class="alert alert-danger" id="sessionError" style="display:none;">
	   		<button onclick="$('#sessionError').hide(); return false;" class="close" aria-label="close">&times;</button>
		  	<span id="sessionErrorMessage">La foto elegida supera el tamaño máximo de 1 MB permitido. Seleccione otra e intente nuevamente</span>
		</div>	
        
		<div class="alert alert-success" id="sessionSucces" style="display:none;">
	   		<button onclick="$('#sessionSucces').hide(); return false;" class="close" aria-label="close">&times;</button>
		  	<span id="sessionSuccesMessage">Session creada satisfactoriamente!</span>
		</div>        
        
        <button class="btn btn-primary btn-file backeButton" onclick="goBack();return false">Volver</button>
        <button class="btn btn-primary btn-raised pull-right" type="submit">Confirmar</button>
      </form>

	<div id="newSessionPopup">
		<input type="hidden" name="sessionId" id="sessionId" value="">
		<label class="labelPopup" id="popupSessionTitle">Crear sesión</label>
		<br/>
		
		<div class="form-group label-floating inputSessionDate">	
			<label class="control-label" id="labelDate" for="sessionDate">Fecha de inicio</label>
			<input type="text" id="sessionDate" class="form-control" required required="true"/>
		</div>
		
		<button class="btn btnCalendar" type="submit" onclick="showCalendar(); return false;">
			<img  src="images/calendar_icon.png" class="calendarIcon" alt="Agregar fecha de inicio" >
		</button>
		
		<br/>
		<br/>
		<hr>
		
		<div class="popupButtonsContainer">
			<button class="btn btnPopup" type="submit" onclick="hidePopup(); return false;">Cancelar</button>
			<button class="btn btnPopup" type="submit" onclick="saveSession(); return false;" id="popupSessionSubmit">Crear</button>
		</div>
						
	</div>

	<input type="hidden" name="courseId" id="courseId" value="<% out.print(course.getId()); %>">

    </div> <!-- /container -->
    
    <script>

//Categories
var data = [
<%
    ArrayList<Category> categories = (java.util.ArrayList)request.getAttribute("categories");
	 for (Category category: categories)
	 { 
		 out.print("{ 'value': " + category.getId() + " , 'text': '" + category.getName() + "'} ,");
	 }
%>
];

var citynames = new Bloodhound({
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    local: $.map(data, function (city) {
        return {
            name: city.text,
            id: city.value,
        };
    })
});
citynames.initialize();
var elt = $('#categories');
elt.tagsinput({
	itemValue: 'id',
  	itemText: 'name',
    typeaheadjs: [{
          minLength: 1,
          highlight: true,
    },{
        minlength: 1,
        name: 'citynames',
        displayKey: 'name',
        //valueKey: 'name',
        source: citynames.ttAdapter()
    }],
    freeInput: true
});

<%
ArrayList<Category> currentCategories = (java.util.ArrayList)request.getAttribute("currentCategories");
 for (Category currentCategory: currentCategories)
 { 
	 out.print("elt.tagsinput('add',  { 'id': " + currentCategory.getId() + " , 'name': '" + currentCategory.getName() + "'});\n");
 }
%>


$(".bootstrap-tagsinput").addClass("form-control");

$(".tt-input").blur(function(){
	if($('#categories').val() != ""){
		$("#labelCategories").hide()
	}else{
		$("#labelCategories").show()
	}
});

$(".tt-input").focus(function(){
	$("#labelCategories").show()
});

$(".tt-input").attr("required",true);

$(".tt-input").attr("oninvalid", "return validateCategories(this)");



<%
		if(request.getAttribute("saveSucces") != null){
%>
			$("#saveSucces").show();
<%
		}
%>

<%
		if(request.getAttribute("saveErrorTeacher") != null){
%>
			$("#saveErrorTeacher").show();
<%
		}
%>



//Teachers
var availableTeachers = [
<%
ArrayList<User> teachers = (java.util.ArrayList)request.getAttribute("teachers");
 for (User teacher: teachers)
 { 
	 out.print("{label:'" + teacher.getFirstName() + " " + teacher.getLastName() + "', id:" + teacher.getId() + "},");
 }
%>
];

$("#inputTeacher").autocomplete({
 	source: availableTeachers,
 	 select: function (event, ui) {
         $("#teacherSelectedId").val(ui.item.id);
     	},
	change: function (event, ui) {
		if($("#inputTeacher").val() == ""){
         		$("#teacherSelectedId").val(0);
		}
     	},
});

</script>

	<script type="text/javascript">
	
		function validateCategories(e){
			debugger;
			if($("#categories").val() == ""){
				e.setCustomValidity('Complete este campo');	
				return true;
			}
			//SUPER HACK
			$(".tt-input").val(".")
			$("#editCurseForm").submit();
			return true
			return false;
		}
	
		function fileSizeValidated(input){
			$("#pictureError").hide();
			if((input.files[0].size / 1024) > 5120){
				$("#pictureError").show();
				$('#imageHolder').attr('src', "images/photo_upload.png")
				
				return false;
			}
			return true;
		}
		
		function readURL(input) {
	        if (input.files && input.files[0]) {
	            var reader = new FileReader();
	
	            reader.onload = function (e) {
	                $('#imageHolder')
	                    .attr('src', e.target.result)
	            };
	
	            reader.readAsDataURL(input.files[0]);
	        }
	    }
		
		function goBack(){
			window.location.href = "cursos/admin";
		}
	
		function goToDetails(){
			window.location.href = '/Servidor/courseDetail?id=' + <% out.print(course.getId()); %> + '&user=admin';
		}
		
	</script>
	
	<script type="text/javascript">
	
	$('#courseId').val(<% out.print(course.getId()); %>);
	
    $(document).ready( ordenarTabla() );
    
	$.datepicker.regional.es = {
		closeText: "Cerrar",
		prevText: "&#x3C;Ant",
		nextText: "Sig&#x3E;",
		currentText: "Hoy",
		monthNames: [ "Enero","Febrero","Marzo","Abril","Mayo","Junio",
		"Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre" ],
		monthNamesShort: [ "Ene","Feb","Mar","Abr","May","Jun",
		"Jul","Ago","Sep","Oct","Nov","Dic" ],
		dayNames: [ "Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado" ],
		dayNamesShort: [ "Dom","Lun","Mar","Mié","Jue","Vie","Sáb" ],
		dayNamesMin: [ "D","L","M","X","J","V","S" ],
		weekHeader: "Sm",
		dateFormat: "dd/mm/yy",
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: "" };
	$.datepicker.setDefaults( $.datepicker.regional[ "es" ] );

    $( "#sessionDate" ).datepicker();


	function showPopup(){
    	$("#sessionId").val("")
		$("#popupSessionTitle")[0].innerHTML = "Crear Sesión";
		$("#popupSessionSubmit")[0].innerHTML = "Crear";
		$("#sessionDate").val("");
		$("#newSessionPopup").show();
	}
	
	function hidePopup(){
		$("#newSessionPopup").hide();
	}
	
	function showCalendar(){
		$.datepicker.setDefaults( $.datepicker.regional[ "fr" ] );
		$( "#sessionDate" ).datepicker("show");
	}
	
	function saveSession(){
		if($("#sessionDate").val() != ""){
			var sessionDate = $('#sessionDate').val();
    		var courseId = $('#courseId').val();
    		var sessionId = $('#sessionId').val();
			
			$.ajax({
			    data: {sessionDate: sessionDate, courseId: courseId, sessionId: sessionId},
			    //Cambiar a type: POST si necesario
			    type: "POST",
			    // Formato de datos que se espera en la respuesta
			    dataType: "json",
			    // URL a la que se enviará la solicitud Ajax
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
		}else{
			
		}
		
		
	}
	
	function editSessionRow(session){
		$("#tr_session_" + session.id).children()[1].innerHTML = session.date;
		
		$("#sessionSuccesMessage")[0].innerHTML = "Sesion modificada satisfactoriamente!";
		$("#sessionSucces").show();
	}   	
	
	function addNewSessionRow(session){
		$('#tableSession tr:last').after("<tr id='tr_session_" + session.id + "'>"+
				"<td class='tg-yw4l no-visible'>" +
    				session.id +
    			"</td><td class='tg-yw4l'>" +
    				session.date +
    			"</td><td class='tg-yw4l'>"+
    	"<button class='btn btnAction' type='submit' onclick='editSession(" + session.id + "); return false;'>"+
			"<img  src='images/edit_icon.png' class='actionButtonImage' alt='Agregar fecha de inicio' ></button>"+
		
		"<button class='btn btnAction' type='submit' onclick='deleteSession(" + session.id + "); return false;'>"+
			"<img  src='images/delete_icon.png' class='actionButtonImage' alt='Agregar fecha de inicio' ></button></td></tr>");
	
		$("#sessionSuccesMessage")[0].innerHTML = "Sesion creada satisfactoriamente!";
		$("#sessionSucces").show();
		
		ordenarTabla();
	}
	
	function ordenarTabla() {
    	var tbody = $('#tableSession');
    	tbody.find('tr:not(:first)').sort(function(a,b){ 
    	    var fecha1_con_espacios = $(a).find('td:eq(1)').text();
    	    var fecha2_con_espacios = $(b).find('td:eq(1)').text();
    	    
    	    var fecha1 = $.trim(fecha1_con_espacios);
    	    var fecha2 = $.trim(fecha2_con_espacios);
			
    	    return fecha1 > fecha2 ? 1 
    	           : fecha1 < fecha2 ? -1 
    	           : 0;           
    	}).appendTo(tbody);
	}     	
	
	function deleteSessionRow(sessionId){
		$("#tr_session_" + sessionId).remove();
		$("#sessionSuccesMessage")[0].innerHTML = "Sesion borrada satisfactoriamente!";
		$("#sessionSucces").show();
	}
	
	function editSession(sessionId){
		var dateValue = $("#tr_session_" + sessionId).children()[1].innerHTML.replace(/\s/g, "");
		var sessionDate = new Date(dateValue)
		var currentDate = new Date();
		if(sessionDate > currentDate){
			$("#sessionId").val(sessionId);
    		$("#popupSessionTitle")[0].innerHTML = "Editar Sesión";
    		$("#popupSessionSubmit")[0].innerHTML = "Guardar";
    		$("#newSessionPopup").show();
    		//HACK
    		$( "#sessionDate" ).focus();
    		$("#sessionDate").val(dateValue);	
		}else{
			$("#sessionErrorMessage")[0].innerHTML = "No se puede modificar una sesion activa!";
	    	$("#sessionError").show();	
		}
		
	}
	
	function deleteSessionAfterConfirm(sessionId){
		$.ajax({
		    data: {sessionId: sessionId},
		    //Cambiar a type: POST si necesario
		    type: "POST",
		    // Formato de datos que se espera en la respuesta
		    dataType: "json",
		    // URL a la que se enviará la solicitud Ajax
		    url: "DeleteCourseSessionActionServlet",
		})
		 .done(function( data, textStatus, jqXHR ) {
			 if(data.success == true){
				 deleteSessionRow(sessionId);	 
			 }else{
				$("#sessionErrorMessage")[0].innerHTML = data.message;
		    	$("#sessionError").show();
			 }
			 
		 })
		 .fail(function( jqXHR, textStatus, errorThrown ) {
		     if ( console && console.log ) {
		         console.log( "La solicitud a fallado: " +  textStatus);
		     }
		});
	}
	
	function deleteSession(sessionId){	
		bootbox.confirm({ 
		    size: 'small',
		    message: "Estas seguro que queres eliminar la sesion?", 
		    callback: function(result){if(result){deleteSessionAfterConfirm(sessionId);} },
		    buttons: {
		        confirm: {
		            label: 'Aceptar',
		        },
		        cancel: {
		            label: 'Cancelar',
		        }
		    },
		})
	}
	</script>
  </body>
</html>
