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
    <title>Detalles del curso</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="bootstrap/css/bootstrap-tagsinput.css" rel="stylesheet">
	<link href="bootstrap/css/bootstrap-material-design.min.css" rel="stylesheet">
    <link href="bootstrap/css/ripples.min.css" rel="stylesheet">
    <link href="bootstrap/css/newCourse.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="bootstrap/js/bootstrap-tagsinput.js"></script>
	<script src="bootstrap/js/typeahead.bundle.js"></script>
	
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/ripples.min.js"></script>
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
	      <a class="navbar-brand" href="javascript:void(0)">Detalles del curso</a>
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
        
        </br>
        
        <button class="btn btn-primary btn-file backeButton" onclick="goBack();return false">Volver</button>
        <button class="btn btn-raised btn-primary newCourseButton btn" type="submit">Confirmar</button>
      </form>


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
     }
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
			window.location.href = "principalAdmin.jsp";
		}
	
	</script>
  </body>
</html>
