
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


 
<form class="register" method="post" action="signup">

  <div class="form-group label-floating">
    <label class="control-label" for="focusedInput1">Email</label>
  <input class="form-control" id="focusedInput1" name="email" type="email" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="focusedInput1">Contraseña</label>
  <input class="form-control" id="focusedInput1" name="password" type="password" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="focusedInput1">Repita Contraseña</label>
  <input class="form-control" id="focusedInput1" name="password_confirmada" type="password" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="focusedInput1">Nombre</label>
  <input class="form-control" id="focusedInput1" name="nombre" type="text" required>
  </div>
  
  <button class="btn btn-raised btn-primary pull-right" name="finalizar" type="submit">Finalizar</button>
</form>

	<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="bootstrap/js/ripples.min.js"></script>
<script src="bootstrap/js/material.min.js"></script>

<script src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
<script>
  $(function () {
    $.material.init();
    $(".shor").noUiSlider({
      start: 40,
      connect: "lower",
      range: {
        min: 0,
        max: 100
      }
    });

    $(".svert").noUiSlider({
      orientation: "vertical",
      start: 40,
      connect: "lower",
      range: {
        min: 0,
        max: 100
      }
    });
  });
</script>

  </body>
</html>