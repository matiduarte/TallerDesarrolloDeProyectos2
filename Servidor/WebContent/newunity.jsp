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
 
<form id="identicalForm" class="register" method="post" action="newunity">
<input type="hidden" name="id" value="${id}">
  <div class="form-group label-floating">
    <label class="control-label" for="name">Nombre</label>
  <input class="form-control" id="name" name="name" type="text">
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="lastName">Descripción</label>
  <input class="form-control" id="description" name="description" type="text">
  </div>
 
  <button class="btn btn-raised btn-primary pull-right" name="create_btn" type="submit">Crear</button>
	</form>

	<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/ripples.min.js"></script>
	<script src="bootstrap/js/material.min.js"></script>

	<script src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
	<script src="bootstrap/js/floating-label.js"></script>



  </body>
</html>