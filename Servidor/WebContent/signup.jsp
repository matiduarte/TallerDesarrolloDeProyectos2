
<!doctype html>
<html lang="en-us">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    
    <script src="//cdn.muicss.com/mui-0.7.4/js/mui.js"></script>
    <!-- Bootstrap -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Material Design -->
    <link href="bootstrap/css/bootstrap-material-design.css" rel="stylesheet">
    <link href="bootstrap/css/ripples.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
    <link href="bootstrap/css/signup.css" rel="stylesheet">
 	
 	
 </head>
  <body>
<div class="navbar navbar-inverse">
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
  <input class="form-control" id="focusedInput1" type="email" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="focusedInput1">Contraseña</label>
  <input class="form-control" id="focusedInput1" type="password" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="focusedInput1">Repita Contraseña</label>
  <input class="form-control" id="focusedInput1" type="password" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="focusedInput1">Nombre</label>
  <input class="form-control" id="focusedInput1" type="text" required>
  </div>
  
  <button class="btn btn-raised btn-primary pull-right" name="finalizar" type="submit">Finalizar</button>
</form>

  </body>
</html>