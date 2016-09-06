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
    <link href="bootstrap/css/newCourse.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="bootstrap/js/bootstrap-tagsinput.js"></script>
	<script src="bootstrap/js/typeahead.bundle.js"></script>
  </head>

  <body>

    <div class="container">

      <form name="loginForm" method="post" action="newCourse" class="form-signin" enctype="multipart/form-data">
        </br>
        
        <label class="btn btn-primary btn-file addImageButton">
		    Agregar Foto <input required="true" type="file" style="display: none;" id="inputPicture" name="picture" onchange="readURL(this);">
		</label>
		
		<img src="images/photo_upload.jpg" alt="Foto para la categoria" class="newCurseImage img-circle" id="imageHolder">
		
        </br>
        </br>
        </br>
        <input type="text" id="inputName" name="name" required="true" class="form-control" placeholder="Nombre" required>
        </br>
        <input type="text" id="inputDescription" name="description" required="true" class="form-control" placeholder="Descripci&oacute;n" required>
        
        </br>
        <label for="categories">Categorias:</label>
       	<select class="form-control" multiple name="categories" id="categories" required="true">
			<%
			 ArrayList<Category> categories = (java.util.ArrayList)request.getAttribute("categories");
			 for (Category category: categories)
			 { 
				 out.print("<option value='"+category.getId()+"'>"+category.getName()+"</option>");
			 }
			%>
		</select>
       
       
		</br>
		
        <button class="btn-primary newCourseButton btn" type="submit">Crear Curso</button>
      </form>

<input type="text" id="autocomplete" >

    </div> <!-- /container -->
    
    <script>

var data = [
<%
	 for (Category category: categories)
	 { 
		 out.print("'" + category.getName() + "',");
	 }
%>
];

var citynames = new Bloodhound({
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    local: $.map(data, function (city) {
        return {
            name: city
        };
    })
});
citynames.initialize();

var elt = $('#autocomplete');
elt.tagsinput({
    typeaheadjs: [{
          minLength: 3,
          highlight: true,
    },{
        minlength: 3,
        name: 'citynames',
        displayKey: 'name',
        valueKey: 'name',
        source: citynames.ttAdapter()
    }],
    freeInput: true
});
/*elt.tagsinput('add', { "value": 1 , "text": "Amsterdam"   , "continent": "Europe"    });
elt.tagsinput('add', { "value": 4 , "text": "Washington"  , "continent": "America"   });
elt.tagsinput('add', { "value": 7 , "text": "Sydney"      , "continent": "Australia" });
elt.tagsinput('add', { "value": 10, "text": "Beijing"     , "continent": "Asia"      });
elt.tagsinput('add', { "value": 13, "text": "Cairo"       , "continent": "Africa"    });*/
</script>

	<script type="text/javascript">
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
