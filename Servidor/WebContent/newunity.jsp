<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<%@ page import="java.util.ArrayList"%>
<%@ page import="entities.Question"%>
<html lang="en-us">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="bootstrap/img/icono.ico">
<title>Unidad</title>
<!-- Material Design fonts -->
<link rel="stylesheet" type="text/css"
	href="//fonts.googleapis.com/css?family=Roboto:300,400,500,700">
<link rel="stylesheet" type="text/css"
	href="//fonts.googleapis.com/icon?family=Material+Icons">

<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap Material Design -->
<link href="bootstrap/css/bootstrap-material-design.css"
	rel="stylesheet">
<link href="bootstrap/css/ripples.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="bootstrap/css/newunity.css" rel="stylesheet">


<link rel="stylesheet"
	href="bootstrap/js/trumbowyg/dist/ui/trumbowyg.min.css">

<script src="//ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="js/vendor/jquery-3.1.0.min.js"><\/script>')</script>
<script src="bootstrap/js/trumbowyg/dist/trumbowyg.min.js"></script>
<script type="text/javascript"
	src="bootstrap/js/trumbowyg/dist/langs/es.min.js"></script>

</head>
<body>
	<div class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-inverse-collapse"></button>
				<a id="barra_superior" class="navbar-brand"
					href="javascript:void(0)">Crear Unidad</a>
			</div>
			<div class="navbar-collapse collapse navbar-inverse-collapse">
			</div>
		</div>
	</div>



	<%
		if(request.getSession(false).getAttribute("moreQuestions") != null){
	%>


	<div class="alerta-more alerta alert alert-danger">
		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		<strong>Necesita agregar mas preguntas</strong>
	</div>

	<%
		request.getSession(true).setAttribute("moreQuestions", null);
			}
	%>

	<div id="questionError" class="alerta-ques alert alert-danger fade in"
		style="display: none;">
		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		<strong>Debe agregar una pregunta. </strong>
	</div>

	<div id="answerError" class="alerta-ans alert alert-danger fade in"
		style="display: none;">
		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		<strong>Debe agregar por lo menos dos respuestas.</strong>
	</div>

	<div id="answerCorrect"
		class="alerta-correct alert alert-danger fade in"
		style="display: none;">
		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		<strong>Debe seleccionar por lo menos una respuesta correcta.</strong>
	</div>

	<form id="identicalForm" class="register" method="post"
		action="newunity" enctype="multipart/form-data">
		<input type="hidden" name="courseId" id="courseId" value="${courseId}">
		<%
			if(request.getAttribute("id") != null) {
		%>
		<input type="hidden" name="id" id="id" value="${id}">
		<%
			} else{
		%>
		<input type="hidden" name="id" id="id" value="">
		<%
			}
		%>
		<div class="blocksContainer row">

			<div class="unityFirstBlock col-md-6">
				<div class="form-group label-floating margen-chico sin-padding">
					<label class="control-label sin-padding" for="name">Nombre</label>
					<c:choose>
						<c:when test="${name != NULL}">
							<input class="form-control" id="name" name="name" type="text"
								value="${name}" required>
						</c:when>
						<c:otherwise>
							<input class="form-control" id="name" name="name" type="text"
								required>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="form-group label-floating margen-chico sin-padding">
					<label class="control-label sin-padding" for="lastName">Descripci&oacute;n</label>
					<c:choose>
						<c:when test="${description != NULL}">
							<input class="form-control" id="description" name="description"
								type="text" value="${description}" required>
						</c:when>
						<c:otherwise>
							<input class="form-control" id="description" name="description"
								type="text" required>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="form-group label-floating margen-chico sin-padding" id="label-questions">
					<label class="control-label sin-padding" for="questions">Cantidad
						de Preguntas</label>
					<c:choose>
						<c:when test="${questionSize != NULL}">
							<input class="form-control" id="questions" name="questions"
								type="text" value="${questionSize}">
						</c:when>
						<c:otherwise>
							<input class="form-control" id="questions" name="questions"
								type="text">
						</c:otherwise>
					</c:choose>
				</div>
				<div id="htmlEditor" name="htmlEditor" class="expand-height"></div>
			</div>
			<div class="unitySecondBlock col-md-6">
				<label class="detail-label">Videos:</label> <label id="btnAddVideo"
					class="btn btn-raised btn-primary addVideoButton btnNew"
					<%if(request.getAttribute("videUrl") != null) {out.print("style='display:none;'");}%>>
					Agregar video <input type="file" style="display: none;" id="video"
					name="video" onchange="if(fileValidated(this))readURL(this);"
					accept="video/*">
				</label>

				<div class="alert alert-danger" id="videoError"
					style="display: none; clear: both;">
					<button onclick="$('#videoError').hide()" class="close"
						aria-label="close" type="button">&times;</button>
					<span id="videoErrorMessage">El video no puede exceder los
						100 MB</span>
				</div>

				<div class="alert alert-success" id="videoSuccess"
					style="display: none; clear: both;">
					<button onclick="$('#videoSuccess').hide()" class="close"
						aria-label="close" type="button">&times;</button>
					<span id="videoSuccessMessage">Video agregado
						satisfactoriamente!</span>
				</div>

				<table class="tg" id="tableVideo">
					<colgroup>
						<col span="1" style="width: 60%;">
						<col span="1" style="width: 10%;">
						<col span="1" style="width: 15%;">
						<col span="1" style="width: 15%;">
					</colgroup>
					<thead>
						<tr>
							<th class="tg-zyzu">Video</th>
							<th class="tg-zyzu">Subt&iacute;tulos</th>
							<th class="tg-zyzu">Tama&ntilde;o</th>
							<th class="tg-zyzu">Acciones</th>
						</tr>
					</thead>
					<tbody>
						<%
							if(request.getAttribute("videUrl") != null) {
						%>
						<tr id="tr_video_">
							<td class="tg-yw4l contenido-truncado"
								title="<%out.print(request.getAttribute("videUrl"));%>">
								<%
									out.print(request.getAttribute("videoName"));
								%>
							</td>
							<td class="tg-yw4l">
								<div id="subtitleLabelsContainers">
									<%
										ArrayList<String> subtitles = (java.util.ArrayList)request.getAttribute("subtitles");
														 for (String subtitle: subtitles)
														 {
									%>
									<a class="subtitleLabel"
										onclick="showSubtitlePopup('<%out.print(subtitle);%>')">
										<%
											out.print(subtitle + "</a><br/>");
															 }
										%>
									
								</div>
								<button class="btn btnAddSubtitle" type="button"
									onclick="showSubtitlePopup()">
									<img src="images/icon_plus.png" class="addSubtitleButtonImage"
										alt="Agregar subtitulo">
							</td>
							<td class="tg-yw4l">
								<%
									out.print(request.getAttribute("videoSize"));
								%> mb
							</td>
							<td class="tg-yw4l">
								<button class="btn btnAction" type="button"
									onclick="$('#video').click()">
									<img src="images/edit_icon.png" class="actionButtonImage"
										alt="Editar">
								</button>

								<button class="btn btnAction" type="button"
									onclick="deleteVideo();">
									<img src="images/delete_icon.png" class="actionButtonImage"
										alt="Borrar">
								</button>
							</td>
						</tr>
					</tbody>
					<%
						}
					%>
				</table>

				<div class="blockQuestions">

					<div>
						<label class="detail-label">Preguntas:</label>
						<button class="btn btn-raised btn-primary pull-right"
							onclick="showQuestionsPopUp();" name="addQuestion" type="button">Agregar
							Pregunta</button>
					</div>

					<div id="wrapper-tabla-preg">
						<table class="tg" id="tableQuestions">
							<colgroup>
								<col span="1" style="width: 80%;">
								<col span="1" style="width: 20%;">
							</colgroup>
							<thead>
								<tr class="header-fijo">
									<th class="tg-zyzu col-md-8">Pregunta</th>
									<th class="tg-zyzu col-md-8">Acciones</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${questionsList != NULL}">
									<c:forEach items="${questionsList}" var="questions">
										<tr id="tr_question_${questions.getId()}">
											<td style="width: 70%">${questions.getQuestion()}</td>
											<td class="tg-yw4l" style="width: 30%">
												<button class="btn btnAction" type="button"
													onclick="editQuestion(${questions.getId()});">
													<img src="images/edit_icon.png" class="actionButtonImage"
														alt="Editar">
												</button>

												<button class="btn btnAction" type="button"
													onclick="deleteQuestion(${questions.getId()});">
													<img src="images/delete_icon.png" class="actionButtonImage"
														alt="Borrar">
												</button>
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>

				</div>
			</div>
		</div>
		<div class="buttonsContainer">
			<c:choose>
				<c:when test="${id != NULL}">
					<button class="btn btn-raised btn-primary pull-right"
						name="create_btn" type="submit">Editar</button>
				</c:when>
				<c:otherwise>
					<button class="btn btn-raised btn-primary pull-right"
						name="create_btn" type="submit">Crear</button>
				</c:otherwise>
			</c:choose>
			<button class="btn-back btn btn-primary pull-left"
				onclick="cancelar(${courseId})" type="button">Volver</button>
		</div>
	</form>

	<div id="subtitlePopup">
		<label class="labelPopup" id="popupSessionTitle">Agregar
			subt&iacute;tulo</label> <br />
		<button type="button" onclick="hideSubtitlePopup()" class="close close-btn"
						data-dismiss="modal" aria-label="close">&times;</button>

		<div class="form-group label-floating inputSessionDate">
			<label class="control-label" id="labelLanguage" for="language">Idioma:</label>
			<select class="form-control" id="language">
				<option>Espanol</option>
				<option>Ingles</option>
				<option>Portugues</option>
			</select>
		</div>

		<label class="btn-select-file btn-file">
			Seleccionar<input type="file" id="subtitle" style="display: none"
			name="subtitle" accept=".vtt" onchange="loadSubtitle();">
		</label>
		<div id="subtitleNameContainer"></div>
		<div class="popupButtonsContainer">
			<button class="btn btnPopup" type="submit"
				onclick="hideSubtitlePopup();">Cancelar</button>
			<button class="btn btnPopup" type="submit" onclick="saveSubtitle()">Guardar</button>
		</div>

	</div>

	<div id="addQuestionPopup" class="modal">
		<div class="modal-dialog borde-fino">
			<div class="modal-content">
				<div class="modal-header" style="padding: 10px;padding-bottom:0">
					<button type="button" onclick="hideQuestionsPopup()" class="close"
						data-dismiss="modal" aria-label="close">&times;</button>
					<h4 class="modal-title" style="text-align: center;font-weight: bold;
    color: #009688;">Nueva Pregunta</h4>
				</div>

				<div class="qmb modal-body">
					<input type="hidden" id="qId" name="qId" value="0">
					<div class="form-group label-floating" style="margin-top:0">
						<label class="control-label" for="addQuestion">Ingrese una
							pregunta</label> <input class="form-control" id="addQuestion"
							name="addQuestion" type="text" required>
					</div>
					<div id="dynamicInput"></div>
					<span class="input-group-btn">
						<button id="addBtn" type="button"
							class="btn btn-fab-mini btn-primary btn-fab addAnswer">
							<i class="material-icons">add</i>
						</button>
					</span>
				</div>
				<div class="modal-footer" style="padding: 0;">
					<button type="button" onclick="hideQuestionsPopup();"
						class="btn btn-primary">Cancelar</button>
					<button type="button" onclick="saveQuestion();"
						class="btn btn-primary">Agregar</button>
				</div>
			</div>

		</div>
	</div>


	<!-- <script src="//code.jquery.com/jquery-1.10.2.min.js"></script> -->
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/ripples.min.js"></script>
	<script src="bootstrap/js/material.min.js"></script>

	<script
		src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
	<script src="bootstrap/js/floating-label.js"></script>
	<script type="text/javascript">
	
	$.urlParam = function(name){
	    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	    if (results==null){
	       return null;
	    }
	    else{
	       return results[1] || 0;
	    }
	}	
	
	if ( $.urlParam('id') ) {
		var barra_titulo = $( "#barra_superior" );
		barra_titulo.text( "Editar unidad" );
	}
		
	var count = 0;
	
	function cancelar(id){	
		window.location.href = '/Servidor/courseDetail?id=' + id;
	}		
	
	function checkLenghtQuestion(input) {
	    if (input.value.length < 1) {
	        input.setCustomValidity('Debe ingresar una respuesta');
	    } else {
	        input.setCustomValidity('');
	   }
	}
	

	function saveQuestion(){
		
		
		var questionId = document.getElementById("qId").value;
		var unityId = "0";
		if($('#id').val() != ""){
			unityId = $('#id').val();
		}
		var courseId = $('#courseId').val();
		
		var question = document.getElementById("addQuestion").value;
		var answersOb = $('input[name="myInputs[]"]');
		
		var atLeastOneCorrect = false;
		var checkBoxes = document.getElementsByName('chk[]');
            var selectedRows = [];
            for (var i = 0, l = checkBoxes.length; i < l; i++) {
                if (checkBoxes[i].checked) {
                	atLeastOneCorrect = true;
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
		
		if(($("#addQuestion").val() != "") && (answersArray.length >= 2) && (atLeastOneCorrect)){
			
			$.ajax({
			    data: {courseId: courseId, unityId: unityId, question: question, answersArray: answersArray, selectedRows: selectedRows, questionId: questionId},
			    //Cambiar a type: POST si necesario
			    type: "POST",
			    // Formato de datos que se espera en la respuesta
			    dataType: "json",
			    // URL a la que se enviará la solicitud Ajax
			    url: "SaveQAActionServlet",
			})
			 .done(function( data, textStatus, jqXHR ) {
				 hideQuestionsPopup();
				
				
				 $(".qmb #qId").attr({
		               value: data.questionId,
		             });
				
				 loadQuestionRow(data);
				 if($('#id').val() == ""){
						$('#id').val(data.unityId);
						window.location= "newunity?courseId=" + $('#courseId').val() + "&id=" + data.unityId;
				 }
				 
			 })
			 .fail(function( jqXHR, textStatus, errorThrown ) {
			     if ( console && console.log ) {
			         console.log( "La solicitud a fallado: " +  textStatus);
			     }
			});
		}else{
			
			var showAlertQuestion = false;
			var showAlertAnswer = false;
			if ($('input[name="addQuestion"]').val() == ""){
				$('#addQuestion').show().focus();
				showAlertQuestion = true;
				$('#questionError').show();
				setTimeout(function() {$('#questionError').hide();},4000);
			}
			if ((answersArray.length < 2) && !(showAlertQuestion)){
				$('#questionError').hide();
				$('#answerError').show();
				showAlertQuestion = true;
				setTimeout(function() {$('#answerError').hide();},4000);
			}
			if (!(atLeastOneCorrect) && !(showAlertQuestion) && !(showAlertAnswer)) {
				$('#questionError').hide();
				$('#answerError').hide();
				$('#answerCorrect').show();
				setTimeout(function() {$('#answerCorrect').hide();},4000);
			}
		}
	}
	
	function editQuestion(questionId){
		
		$.ajax({
		    data: {questionId: questionId},
		    //Cambiar a type: POST si necesario
		    type: "POST",
		    // Formato de datos que se espera en la respuesta
		    dataType: "json",
		    // URL a la que se enviará la solicitud Ajax
		    url: "EditQAActionServlet",
		})
		 .done(function( data, textStatus, jqXHR ) {
			 
			 var addbtn = document.getElementById("addBtn");
			 $(".qmb #addQuestion").val(data.question).trigger('change');
			 $(".qmb #qId").attr({
	               value: data.questionId,
	             });
			 for (var i = 0; i < data.answerList.length; i++){
				 addbtn.click();
				 $(".qmb #inp"+i).val(data.answerList[i]["answer"]);
				 if (data.answerList[i]["isCorrect"] == true){
					 $(".qmb #chk"+i).prop("checked", true);
				 }
			 }
			 $("#addQuestionPopup").show();
		 })
		 .fail(function( jqXHR, textStatus, errorThrown ) {
		     if ( console && console.log ) {
		         console.log( "La solicitud a fallado: " +  textStatus);
		     }
		});
	}
	
	
	function deleteQuestion(id){
		var unityId = "0";
		if($('#id') != undefined){
			unityId = $('#id').val();
		}
		
		$.ajax({
		    data: {unityId:unityId, questionId: id},
		    //Cambiar a type: POST si necesario
		    type: "POST",
		    // Formato de datos que se espera en la respuesta
		    dataType: "json",
		    // URL a la que se enviará la solicitud Ajax
		    url: "DeleteQAActionServlet",
		})
		 .done(function( data, textStatus, jqXHR ) {
			 $("#tr_question_" + data.questionId).remove();
		 })
		 .fail(function( jqXHR, textStatus, errorThrown ) {
		     if ( console && console.log ) {
		         console.log( "La solicitud a fallado: " +  textStatus);
		     }
		});
	}
	
	function saveVideo(){
		var unityId = "0";
		if($('#id').val() != ""){
			unityId = $('#id').val();
		}
		
		var formData = new FormData();
		formData.append('unityId', unityId);
		formData.append('courseId', $('#courseId').val());
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
			 if($('#id').val() == ""){
				$('#id').val(data.id);
				window.location= "newunity?courseId=" + $('#courseId').val() + "&id=" + data.id;
			}
		 })
		 .fail(function( jqXHR, textStatus, errorThrown ) {
		     if ( console && console.log ) {
		         console.log( "La solicitud a fallado: " +  textStatus);
		     }
		});
		
	}
	
	function deleteVideo(){
		var unityId = "0";
		if($('#id') != undefined){
			unityId = $('#id').val();
		}
		
		var formData = new FormData();
		formData.append('unityId', unityId);
		// Main magic with files here
		
		$.ajax({
		    data: {unityId:unityId},
		    //Cambiar a type: POST si necesario
		    type: "POST",
		    // Formato de datos que se espera en la respuesta
		    dataType: "json",
		    // URL a la que se enviará la solicitud Ajax
		    url: "DeleteUnityVideoActionServlet",
		})
		 .done(function( data, textStatus, jqXHR ) {
			 $("#tr_video_").remove();
			 $("#btnAddVideo").show();
		 })
		 .fail(function( jqXHR, textStatus, errorThrown ) {
		     if ( console && console.log ) {
		         console.log( "La solicitud a fallado: " +  textStatus);
		     }
		});
	}
	
	function loadQuestionRow(data){
		
		row = '<tr id="tr_question_'+data.questionId+'">'
	    + '<td>'
    	+ data.question
    	+ '</td>'
		+ '<td class="tg-yw4l">'
    	+ '<button class="btn btnAction" type="button" onclick="editQuestion('+data.questionId+');">'
		+	'<img  src="images/edit_icon.png" class="actionButtonImage" alt="Editar" >'
		+ '</button>'
		+ '<button class="btn btnAction" type="button" onclick="deleteQuestion('+data.questionId+');">'
		+	'<img  src="images/delete_icon.png" class="actionButtonImage" alt="Borrar" >'
		+ '</button>'
    	+  '</td>'
    	+ '</tr>';
    	
    	
		if(data.edit){
			var rowIndex = $('#tr_question_'+data.questionId).index();
			$('#tr_question_'+data.questionId).remove();
			$('#tableQuestions tr').eq(rowIndex - 1).after(row);
		} else {
			$('#tableQuestions tr:last').after(row);
		}
    	
	}
	
	function loadVideoRow(data){
		row = '<tr id="tr_video_"><td class="tg-yw4l">'
    	+ data.videoUrl
   	 	+'</td><td class="tg-yw4l">'
    	+ "<div id='subtitleLabelsContainers'></div>"
    	+ '<button class="btn btnAddSubtitle" type="button" onclick="showSubtitlePopup()"><img  src="images/icon_plus.png" class="addSubtitleButtonImage" alt="Agregar subtitulo">'
    	+ '</td><td class="tg-yw4l">'
    	+ getFileSizeFromBytes(data.videoSize) + '</td><td class="tg-yw4l"><button class="btn btnAction" type="button" onclick="$(\'#video\').click()"><img  src="images/edit_icon.png" class="actionButtonImage" alt="Editar" ></button>'
    	+ '<button class="btn btnAction" type="button" onclick="deleteVideo();"><img  src="images/delete_icon.png" class="actionButtonImage" alt="Borrar" >'
		+ '</button></td></tr>';
		
		
		if($('#tr_video_').length){
			$('#tr_video_').empty();	
		}

		$('#tableVideo tr:last').after(row);
		$("#btnAddVideo").hide();

		$('#videoSuccess').show();
		$('#videoSuccessMessage')[0].innerHTML = "Video agregado satisfactoriamente!";
	}
	
	function getFileSizeFromBytes(size){
		return Math.round((size*100) / (1024*1024)) / 100 + " mb";
	}
	
	function showSubtitlePopup(value){
		if(value != undefined){
			$("#language").val(value);
		}
		$("#subtitlePopup").show();
	}
	
	function hideSubtitlePopup(){
		$("#subtitlePopup").hide();
	}
	
	function loadSubtitle(){
		$("#subtitleNameContainer").html($("#subtitle")[0].files[0].name);
	}
	
	function saveSubtitle(){
		if($("#subtitle")[0].files[0] != undefined){
			var ext = $("#subtitle")[0].files[0].name.split('.').pop().toLowerCase();
			if($.inArray(ext, ['vtt']) == -1) {
				$('#videoError').show();
				$('#videoErrorMessage')[0].innerHTML = "El formato del subtitulo debe ser .vvt";
				hideSubtitlePopup();
				return false;
			}

			var unityId = "0";
			if($('#id') != undefined){
				unityId = $('#id').val();
			}
			
			var formData = new FormData();
			formData.append('unityId', unityId);
			formData.append('subtitle', $("#subtitle")[0].files[0]);
			formData.append('language', encodeURIComponent($("#language").val()));
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
				 $("#subtitle").val("");
				 $("#subtitleNameContainer").html("");
				 loadSubtitleLabel($("#language").val());
			 })
			 .fail(function( jqXHR, textStatus, errorThrown ) {
			     if ( console && console.log ) {
			         console.log( "La solicitud a fallado: " +  textStatus);
			     }
			});	
		}else{
			$("#subtitleNameContainer").html("Seleccione un archivo");
		}
		
		
	}
	
	function loadSubtitleLabel(value){
		if($("#subtitleLabelsContainers").text().indexOf(value) < 0){
			var label = '<a class="subtitleLabel" onclick="showSubtitlePopup(\''+ value +'\')">' + value + '</a><br/>';
			$("#subtitleLabelsContainers").append(label);	
		}
		
		$("#subtitleNameContainer").html("");
		hideSubtitlePopup();
		$('#videoSuccess').show();
		$('#videoSuccessMessage')[0].innerHTML = "Subtitulos agregados satisfactoriamente!";
	}
	
	function fileValidated(input){
		var ext = $('#video').val().split('.').pop().toLowerCase();
		if($.inArray(ext, ['mp4','mpeg','mpg','mkv']) == -1) {
			$('#videoError').show();
			$('#videoSuccessMessage')[0].innerHTML = "Debe ingresar un video";
			return false;
		}


		if((input.files[0].size / 1024) > 101200){
			$('#videoError').show();
			$('#videoErrorMessage')[0].innerHTML = "El video no puede exceder los 100 MB";
			return false;
		}
		return true;
	}
	
	
	function showQuestionsPopUp() {
		$("#addQuestionPopup").show();
		$(".qmb #qId").attr({
            value: "0",
          });
	}
	
	function hideQuestionsPopup(){
		
		$('.qmb').find('input').val('').end();
		
		for (var i = 0; i < count; i++){
				$(".qmb #ans"+i).remove();
		}
		
		$("#addQuestionPopup").hide();
		count = 0;
		
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
		
		$("#htmlEditor").trumbowyg({
		    prefix: 'sin-margen'
		});		
		
		<%if(request.getAttribute("html") != null ){%>
			$("#htmlEditor").trumbowyg("html", '<%out.print(request.getAttribute("html"));%>');
		<%}%>
		
		
		
		$('.addAnswer').click(function(e){
			
			var newdiv = document.createElement('div');
			newdiv.innerHTML ="<div id=ans"+count+" class='form-group label-floating is-empty' style='margin-top: 15px'>"
	    	+  "<label class='control-label'>Ingrese una respuesta</label>"
	    	+  "<div class='input-group'>"
	        +  "<input id=inp"+count+ " class='form-control' type='text' name='myInputs[]' required>"
	        +  	"<span class='input-group-addon'>"
	        +  		"<label>"
	        +           "<input id=chk"+count+ " type='checkbox' name='chk[]'>"
	        +         "</label>"
	        +     "</span>"
	        +  	"<span class='input-group-btn'>"
	        +  		"<button id='rmvBtn' type='button' class='removeAnswer btn btn-fab-mini btn-primary btn-fab'>"
	        +           "<i class='material-icons'>delete</i>"
	        +         "</button>"
	        +     "</span>"
	        +    "</div>"
	    	+ "</div>";
	    	document.getElementById('dynamicInput').appendChild(newdiv);
	    	count++;
	    	
	    	
	    	$('.removeAnswer').click(function(){
	    		document.getElementById(this.parentNode.parentNode.parentNode.id).remove();
	    	});
		});
		
		
	});
	
	
	$("#questions").focusout(function(){
	      var valor_ingresado = $("#questions").val();
	      if ( valor_ingresado == "" ) {
	    	  $("#label-questions").removeClass( "has-error" );   	  
	      }
	    });
	
	</script>
</body>
</html>