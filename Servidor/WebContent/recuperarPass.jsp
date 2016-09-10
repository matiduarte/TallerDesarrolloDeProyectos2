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
    <link href="bootstrap/css/recuperarPass.css" rel="stylesheet">
    <style type="text/css">
	h4 { color: #009688; 
		text-align: center;
		padding-top: 80px; }
	</style>
    
</head>

<body>
	<div class="navbar navbar-default">
  		<div class="container-fluid">
    	<div class="navbar-header">
      	<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
      	</button>
      <a class="navbar-brand" href="javascript:void(0)">Recuperación de Contraseña</a>
    </div>
    <div class="navbar-collapse collapse navbar-inverse-collapse"> 
    </div>
  </div>
</div>


<h4><ins>Ingrese el email con el que se registro y le enviaremos los pasos a seguir
						para recuperar su contraseña.</ins></h4>

<form  class="register" method="post" action="recuperarPass">



  <div class="form-group label-floating">
    <label class="control-label" for="focusedInput1">Email</label>
  <input class="form-control" name="email" type="email" pattern="[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{1,5}" required>
  </div>
  
<button class="btn btn-raised btn-primary pull-right" name="finalizar" type="submit">Enviar</button>
  <button class="btn-back btn btn-primary pull-left" onclick="volver()" type="button">Volver</button>
	</form>
<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/ripples.min.js"></script>
	<script src="bootstrap/js/material.min.js"></script>

	<script src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
	<script src="bootstrap/js/floating-label.js"></script>

	<script type='text/javascript'>

	function volver(){	
			window.location.href = "/Servidor/signin";
	}
	
	</script>


</body>
</html>