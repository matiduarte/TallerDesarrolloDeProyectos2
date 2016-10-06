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
    
    
    <link rel="stylesheet" href="bootstrap/js/trumbowyg/dist/ui/trumbowyg.min.css">
 	
 	<script src="//ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
	<script>window.jQuery || document.write('<script src="js/vendor/jquery-3.1.0.min.js"><\/script>')</script>
 	<script src="bootstrap/js/trumbowyg/dist/trumbowyg.min.js"></script>
 	<script type="text/javascript" src="bootstrap/js/trumbowyg/dist/langs/es.min.js"></script>
 	
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
 
<form id="identicalForm" class="register" method="post" action="newunity" enctype="multipart/form-data">
<input type="hidden" name="courseId" value="${courseId}">
<c:if test="${id != NULL}">
  <input type="hidden" name="id" id="id" value="${id}">
</c:if>
<div class="unityFirstBlock">
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
	    <label class="control-label" for="lastName">Descripci�n</label>
	    <c:choose>
	    	<c:when test="${description != NULL}">
	  <input class="form-control" id="description" name="description" type="text" value="${description}" required>
	  </c:when>
	          <c:otherwise>
	          <input class="form-control" id="description" name="description" type="text" required>
	          </c:otherwise>
	   </c:choose>
	  </div>
	  
	  <div id="htmlEditor" name="htmlEditor"></div>
  </div>
  <div class="unitySecondBlock">
  <label class="detail-label">Videos:</label>
     <label class="btn btn-raised btn-primary addVideoButton btnNew" >
     	Agregar video <input type="file" style="display: none;" id="video" name="video" onchange="if(fileValidated(this))readURL(this);"  accept="video/*">
     </label>
     
     <table class="tg" id="tableVideo">
		  <tr>
		    <th class="tg-zyzu">Video</th>
		    <th class="tg-zyzu">Subt�tulos</th>
		    <th class="tg-zyzu">Tama�o</th>
		    <th class="tg-zyzu">Acciones</th>
		  </tr>

		 	<tr id="tr_video_">
			    <td class="tg-yw4l">
			    	Algun nombre
			    </td>
			    <td class="tg-yw4l">
			    	Sub
			    </td>
			    <td class="tg-yw4l">
			    	10 mb
			    </td>
			    <td class="tg-yw4l">
			    	<button class="btn btnAction" type="submit" onclick="">
						<img  src="images/edit_icon.png" class="actionButtonImage" alt="Editar" >
					</button>
					
					<button class="btn btnAction" type="submit" onclick="">
						<img  src="images/delete_icon.png" class="actionButtonImage" alt="Borrar" >
					</button>
			    </td>
		  </tr>
	</table>
  
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

	<!-- <script src="//code.jquery.com/jquery-1.10.2.min.js"></script> -->
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/ripples.min.js"></script>
	<script src="bootstrap/js/material.min.js"></script>

	<script src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
	<script src="bootstrap/js/floating-label.js"></script>
	<script type="text/javascript">
		
	function cancelar(id){	
		window.location.href = '/Servidor/courseDetail?id=' + id;
	}	
	
	function saveVideo(){
		var unityId = "0";
		if($('#id') != undefined){
			unityId = $('#id').val();
		}
		
		var formData = new FormData();
		formData.append('unityId', unityId);
		formData.append('video', $('input[type=file]')[0].files[0]);
		// Main magic with files here
		
		$.ajax({
		    data: formData,
		    //Cambiar a type: POST si necesario
		    type: "POST",
		    // Formato de datos que se espera en la respuesta
		    dataType: "json",
		    // URL a la que se enviar� la solicitud Ajax
		    url: "SaveUnityVideoActionServlet",
		    cache: false,
            contentType: false,
            processData: false
		})
		 .done(function( data, textStatus, jqXHR ) {
			 //Cargar row
		 })
		 .fail(function( jqXHR, textStatus, errorThrown ) {
		     if ( console && console.log ) {
		         console.log( "La solicitud a fallado: " +  textStatus);
		     }
		});
		
		
	}
	
	function fileValidated(input){
		var ext = $('#video').val().split('.').pop().toLowerCase();
		if($.inArray(ext, ['mp4','mpeg','mpg','mkv']) == -1) {
			/* $("#pictureTypeError").show();
			$('#imageHolder').attr('src', "images/photo_upload.jpg")				 */
			return false;
		}


		if((input.files[0].size / 1024) > 51200){
			/* $("#pictureError").show();	 */		
			return false;
		}
		return true;
	}
	
	function readURL(input) {
		if (input.files && input.files[0]) {
		    var reader = new FileReader();

		    /* reader.onload = function (e) {
		        $('#imageHolder')
		            .attr('src', e.target.result)
		    };
 */

            reader.readAsDataURL(input.files[0]);
 
 			saveVideo();
        }
    }
	
	$( document ).ready(function() {
		$("#htmlEditor").trumbowyg(
		{
		    lang: 'es'
		});
		
		<% 
		if(request.getAttribute("html") != null ){%>
			$("#htmlEditor").trumbowyg("html", '<% out.print(request.getAttribute("html")); %>');
		<%} %>
	});
	
	
	
	</script>
  </body>
</html>