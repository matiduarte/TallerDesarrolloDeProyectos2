<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en-us">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="bootstrap/img/icono.ico">
    
    <!-- Material Design fonts -->
  <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Roboto:300,400,500,700">
  <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/icon?family=Material+Icons">
    
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
	  <div class="form-group label-floating">
	    <label class="control-label" for="questions">Cantidad de Preguntas</label>
	    <input class="form-control" id="questions" name="questions" type="text" required>
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
		    <th class="tg-zyzu">Subtítulos</th>
		    <th class="tg-zyzu">Tamaño</th>
		    <th class="tg-zyzu">Acciones</th>
		  </tr>

		 	<!-- <tr id="tr_video_">
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
		  </tr> -->
	</table>
	<div class="blockQuestions">
  <label class="detail-label">Preguntas:</label>
    <button class="btn btn-raised btn-primary pull-right" onclick="showQuestionsPopUp();" name="addQuestion" type="button">Agregar Pregunta</button>
     
     <table class="tg" id="tableQuestions">
		  <tr>
		    <th class="tg-zyzu col-md-8">Pregunta</th>
		    <th class="tg-zyzu col-md-8">Acciones</th>
		  </tr>

		 	<!-- <tr id="tr_video_">
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
		  </tr> -->
	</table>
	</div>
  </div>
  
 <c:choose>
    	<c:when test="${id != NULL}">
  			<button class="btn btn-raised btn-primary pull-right" name="create_btn" type="submit">Editar</button>
  		</c:when>
		<c:otherwise>
			<button class="btn btn-raised btn-primary pull-right" name="create_btn" style="display: none;" type="submit">Crear</button>
          </c:otherwise>
   </c:choose>
   <button class="btn-back btn btn-primary pull-left" onclick="cancelar(${courseId})" style="display: none;" type="button">Cancelar</button>
	</form>
	
	<!-- div id="subtitlePopup">
		<label class="labelPopup" id="popupSessionTitle">Agregar subtítulo</label>
		<br/>
		
		<div class="form-group label-floating inputSessionDate">	
			<label class="control-label" id="labelDate" for="sessionDate">Idioma:</label>
			<select class="form-control" id="sel1">
			    <option>Español</option>
			    <option>Inglés</option>
			    <option>Portugués</option>
		  </select>
		</div>
		
		<label class="btn btn-primary btn-raised btn-file">
			Seleccionar<input type="file" id="subtitle" style="display:none" name="subtitle"  accept=".srt" onchange="loadSubtitle();">
		</label>
		<span id="subtitleNameContainer">
		</span>
		<br/>
		<br/>
		<hr>
		
		<div class="popupButtonsContainer">
			<button class="btn btnPopup" type="submit" onclick="hideSubtitlePopup();">Cancelar</button>
			<button class="btn btnPopup" type="submit" onclick="saveSubtitle()">Guardar</button>
		</div>
		
	</div-->
	
	<div id="addQuestionPopup" class="modal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" onclick="hideQuestionsPopup()" class="close" data-dismiss="modal" aria-label="close">&times;</button>
        <h4 class="modal-title">Nueva Pregunta</h4>
      </div>
      
      <div class="modal-body">
      
        <div class="form-group label-floating">
    <label class="control-label" for="addQuestion">Ingrese una pregunta</label>
  	<input class="form-control" id="addQuestion" name="addQuestion" type="text" required>
  </div>
	<div id="dynamicInput">
		<div id ="ansChild" class="form-group label-floating">
		  <label class="control-label">Ingrese una respuesta</label>
		  <div class="input-group">
		    <input type="text" name='myInputs[]' class="form-control" required>
		    <span  class="input-group-addon">
		    <label>
		    	<input type="checkbox" name="chk[]">
		    </label>
		    </span>
		    <span class="input-group-btn">
		      <button type="button" class="btn btn-fab-mini btn-primary btn-fab">
		        <i class="material-icons">delete</i>
		      </button>
		    </span>
		  </div>
		</div>
		</div>
		<span class="input-group-btn">
		<button type="button" class="btn btn-fab-mini btn-primary btn-fab" onClick="addInput('dynamicInput');">
		   <i class="material-icons">add</i>
		</button>
		</span>
  </div>
      <div class="modal-footer">
        <button type="button" onclick="saveQuestion();" class="btn btn-primary">Agregar</button>
      </div>
  
      
     
    </div>
     
  </div>
</div>

	

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
	
	
	function addInput(divName){
		var newdiv = document.createElement('div');
			newdiv.innerHTML ="<div class='form-group label-floating'>"
	    	+  "<label class='control-label'>Ingrese una respuesta</label>"
	    	+  "<div class='input-group'>"
	        +  "<input class='form-control' type='text' name='myInputs[]' required>"
	        +  	"<span class='input-group-addon'>"
	        +  		"<label>"
	        +           "<input type='checkbox' name='chk[]'>"
	        +         "</label>"
	        +     "</span>"
	        +  	"<span class='input-group-btn'>"
	        +  		"<button type='button' class='btn btn-fab-mini btn-primary btn-fab'>"
	        +           "<i class='material-icons'>delete</i>"
	        +         "</button>"
	        +     "</span>"
	        +    "</div>"
	    	+ "</div>";
	    	document.getElementById(divName).appendChild(newdiv);
	}
	

	function saveQuestion(){
		
		var question = document.getElementById("addQuestion").value;
		var answersOb = $('input[name="myInputs[]"]');
		
		var checkBoxes = document.getElementsByName('chk[]');
            var selectedRows = [];
            for (var i = 0, l = checkBoxes.length; i < l; i++) {
                if (checkBoxes[i].checked) {
                    selectedRows.push(1);
                } else {
                	selectedRows.push(0);
                }
            }
        
		var answersArray = [];
		for (i = 0; i < answersOb.length; i++){
			if (answersOb[i].value != "")
				answersArray[i] = answersOb[i].value;
		}	
		
		if(($("#addQuestion").val() != "") && (answersArray.length > 0)){
			$.ajax({
			    data: {question: question, answersArray: answersArray, selectedRows: selectedRows},
			    //Cambiar a type: POST si necesario
			    type: "POST",
			    // Formato de datos que se espera en la respuesta
			    dataType: "json",
			    // URL a la que se enviará la solicitud Ajax
			    url: "SaveQAActionServlet",
			})
			 .done(function( data, textStatus, jqXHR ) {
				 hideQuestionsPopup();
			 })
			 .fail(function( jqXHR, textStatus, errorThrown ) {
			     if ( console && console.log ) {
			         console.log( "La solicitud a fallado: " +  textStatus);
			     }
			});
		}else{
			
		}
	}
	
	function saveVideo(){
		var unityId = "0";
		if($('#id') != undefined){
			unityId = $('#id').val();
		}
		
		var formData = new FormData();
		formData.append('unityId', unityId);
		formData.append('video', $("#video")[0].files[0]);
		// Main magic with files here
		
		$.ajax({
		    data: formData,
		    //Cambiar a type: POST si necesario
		    type: "POST",
		    // Formato de datos que se espera en la respuesta
		    dataType: "json",
		    // URL a la que se enviará la solicitud Ajax
		    url: "SaveUnityVideoActionServlet",
		    cache: false,
            contentType: false,
            processData: false
		})
		 .done(function( data, textStatus, jqXHR ) {
			 loadVideoRow(data);
		 })
		 .fail(function( jqXHR, textStatus, errorThrown ) {
		     if ( console && console.log ) {
		         console.log( "La solicitud a fallado: " +  textStatus);
		     }
		});
		
	}
	
	function loadVideoRow(data){
		row = '<tr id="tr_video_"><td class="tg-yw4l">'
    	+ data.videoUrl
   	 	+'</td><td class="tg-yw4l">'
    	+ ""
    	+ '<button class="btn btnAddSubtitle" type="button" onclick="showSubtitlePopup()"><img  src="images/icon_plus.png" class="addSubtitleButtonImage" alt="Agregar subtitulo">'
    	+ '</td><td class="tg-yw4l">'
    	+ getFileSizeFromBytes(data.videoSize) + '</td><td class="tg-yw4l"><button class="btn btnAction" type="button" onclick="$(\'#video\').click()"><img  src="images/edit_icon.png" class="actionButtonImage" alt="Editar" ></button>'
    	+ '<button class="btn btnAction" type="button" onclick=""><img  src="images/delete_icon.png" class="actionButtonImage" alt="Borrar" >'
		+ '</button></td></tr>';
		
		if($('#tr_video_').length){
			$('#tr_video_').empty();	
		}
		
		$('#tableVideo tr:last').after(row);
	}
	
	function getFileSizeFromBytes(size){
		return Math.round((size*100) / (1024*1024)) / 100 + " mb";
	}
	
	function showSubtitlePopup(){
		$("#subtitlePopup").show();
	}
	
	function hideSubtitlePopup(){
		$("#subtitlePopup").hide();
	}
	
	function loadSubtitle(){
		$("#subtitleNameContainer").html($("#subtitle")[0].files[0].name);
	}
	
	function saveSubtitle(){
		var unityId = "0";
		if($('#id') != undefined){
			unityId = $('#id').val();
		}
		
		var formData = new FormData();
		formData.append('unityId', unityId);
		formData.append('subtitle', $("#subtitle")[0].files[0]);
		formData.append('language', $("#subtitle")[0].files[0]);
		// Main magic with files here
		
		$.ajax({
		    data: formData,
		    //Cambiar a type: POST si necesario
		    type: "POST",
		    // Formato de datos que se espera en la respuesta
		    dataType: "json",
		    // URL a la que se enviará la solicitud Ajax
		    url: "SaveVideoSubtitleActionServlet",
		    cache: false,
            contentType: false,
            processData: false
		})
		 .done(function( data, textStatus, jqXHR ) {
			 loadVideoRow(data);
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
	
	function showQuestionsPopUp() {
		$("#addQuestionPopup").show();
	}
	
	function hideQuestionsPopup(){
		
		$("#addQuestionPopup").hide();
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