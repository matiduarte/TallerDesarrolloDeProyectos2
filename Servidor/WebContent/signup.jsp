
<!doctype html>
<html lang="en-us">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
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


 
<form id="identicalForm" class="register" method="post" action="signup">

  <div class="form-group label-floating">
    <label class="control-label" for="focusedInput1">Email *</label>
  <input class="form-control" name="email" type="email" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="focusedInput1">Contraseña *</label>
  <input class="pepito form-control" id="password" name="password" oninput="checkLenghtPass(this)" type="password" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="focusedInput1">Repita Contraseña *</label>
  <input class="form-control" id="passwordconf" name="passwordconf" oninput="checkSamePass(this)" type="password" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="focusedInput1">Nombre *</label>
  <input class="form-control" name="name" type="text" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="focusedInput1">Apellido *</label>
  <input class="form-control" name="lastName" type="text" required>
  </div>
  
  <button class="btn btn-raised btn-primary pull-right" name="finalizar" type="submit">Finalizar</button>
  <button class="btn btn-primary pull-left" onclick="volver()" type="button">Atras</button>
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

<script>

</script>

  </body>
</html>