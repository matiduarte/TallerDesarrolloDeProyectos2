
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
    <link href="bootstrap/css/signup.css" rel="stylesheet">
 	
 	
 </head>
  <body>
<div class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
      </button>
      <a class="navbar-brand" href="javascript:void(0)">Registación</a>
    </div>
    <div class="navbar-collapse collapse navbar-inverse-collapse"> 
    </div>
  </div>
</div>

<%

		if(request.getAttribute("errormsg") != null){
%>

	<div class="alerta alert alert-danger">
  		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
  		<strong>Error!</strong> Este email ya se ha utilizado. Por favor, introduzca otro.
	</div>
 
 <%

		}
%>
 
<form id="identicalForm" class="register" method="post" action="signup">

  <div class="form-group label-floating">
    <label class="control-label" for="email">Email *</label>
  <input class="form-control" id="email" name="email" type="email" pattern="[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{1,5}" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="password">Contraseña *</label>
  <input class="pepito form-control" id="password" name="password" oninput="checkLenghtPass(this)" type="password" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="passwordconf">Repita Contraseña *</label>
  <input class="form-control" id="passwordconf" name="passwordconf" oninput="checkSamePass(this)" type="password" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="name">Nombre *</label>
  <input class="form-control" id="name" name="name" type="text" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="lastName">Apellido *</label>
  <input class="form-control" id="lastName" name="lastName" type="text" required>
  </div>
  <button class="btn btn-raised btn-primary pull-right" name="finalizar" type="submit">Registrar</button>
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
	
	function checkLenghtPass(input) {
	    if (input.value.length < 6) {
	        input.setCustomValidity('La contraseña debe tener al menos 6 caracteres.');
	    } else {
	        input.setCustomValidity('');
	   }
	}
	
	function checkSamePass(input) {
	    if (input.value != document.getElementById('password').value) {
	        input.setCustomValidity('Las contraseñas deben coincidir.');
	    } else {
	        input.setCustomValidity('');
	   }
	}
	
	
	</script>


  </body>
</html>