<!DOCTYPE html>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entities.Category" %>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="bootstrap/img/icono.ico">
    <title>Nuevo Curso</title>
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
	
	
  </head>

  <body>
	
	<div class="navbar navbar-default">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
	      </button>
	      <a class="navbar-brand" href="javascript:void(0)">Nuevo Curso</a>
	    </div>
	    <div class="navbar-collapse collapse navbar-inverse-collapse"> 
	    </div>
	  </div>
	</div>
	
    <div class="container">
    	
	   	<div class="alert alert-danger" id="pictureError" style="display:none;">
	   		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		  	La foto elegida supera el tamaño máximo de 1 MB permitido. Seleccione otra e intente nuevamente
		</div>
		
		<div class="alert alert-success" id="saveSucces" style="display:none;">
	   		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		  	Curso creado satisfactoriamente!
		</div>

      <form id="loginForm" name="loginForm" method="post" action="newCourse" class="form-signin" enctype="multipart/form-data">
        </br>
        
        <label class="btn btn-primary btn-file addImageButton">
		    Agregar Foto <input type="file" style="display: none;" id="picture" name="picture" onchange="if(fileSizeValidated(this))readURL(this);">
		</label>
		
		<span>
        	<label class="maxPictureLabel">Tama&ntilde;o m&aacute;ximo: 1 mb</label>
		</span>
		<img src="images/photo_upload.jpg" alt="Foto para la categoria" class="newCurseImage img-circle" id="imageHolder">
		
        </br>
        </br>
        </br>
        <div class="form-group label-floating">	
        	<label class="control-label" for="inputName">Nombre</label>
        	<input type="text" id="inputName" name="name" required="true"  class="form-control" required>
		</div>
        </br>
        
        <div class="form-group label-floating">	
        	<label class="control-label" for="inputName">Descripci&oacute;n</label>
        	<input type="text" id="inputDescription" name="description" required="true" class="form-control" required>
        </div>
        </br>
        
        <div class="form-group label-floating">	
        	<label class="control-label" id="labelCategories" for="categories">Categor&iacute;as</label>
        	<input type="text" id="categories" name="categories" class="form-control">
       </div>
        </br>
        
        <button class="btn btn-primary btn-file backeButton" onclick="return false;">Volver</button>
        <button class="btn btn-raised btn-primary newCourseButton btn" type="submit">Crear Curso</button>
      </form>


    </div> <!-- /container -->
    
    <script>

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

</script>

	<script type="text/javascript">
	
		function validateCategories(e){
			debugger;
			if($("#categories").val() == ""){
				e.setCustomValidity('Campo requerido');	
				return true;
			}
			//SUPER HACK
			$(".tt-input").val(".")
			$("#loginForm").submit();
			return true
			return false;
		}
	
		function fileSizeValidated(input){
			$("#pictureError").hide();
			if((input.files[0].size / 1024) > 1024){
				$("#pictureError").show();
				$('#imageHolder').attr('src', "images/photo_upload.jpg")
				
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
	
	</script>
  </body>
</html>
