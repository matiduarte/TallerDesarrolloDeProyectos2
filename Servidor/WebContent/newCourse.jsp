<!DOCTYPE html>
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

    <!-- Bootstrap core CSS -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <div class="container">

      <form name="loginForm" method="post" action="newCourse" class="form-signin">
        <label for="inputName" class="sr-only">Nombre</label>
        <input type="text" id="inputName" name="name" class="form-control" placeholder="Nombre" required>
        <label for="inputDescription" class="sr-only">Descripci&oacute;n</label>
        <input type="text" id="inputDescription" name="description" class="form-control" placeholder="Descripci&oacute;n" required>
        <label class="control-label">Seleccione una imagen</label>
		<input id="inputPicture" name="picture" type="file" class="form-control-file">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Guardar</button>
      </form>

    </div> <!-- /container -->

  </body>
</html>