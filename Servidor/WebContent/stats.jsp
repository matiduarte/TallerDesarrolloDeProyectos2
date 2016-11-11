<%@ page import="java.lang.*"%>
<%@ page import="java.util.ArrayList"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<title>Estadísticas</title>

<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap Material Design -->
<link href="bootstrap/css/bootstrap-material-design.css"
	rel="stylesheet">
<link href="bootstrap/css/ripples.min.css" rel="stylesheet">

<link href="//fezvrasta.github.io/snackbarjs/dist/snackbar.min.css"
	rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Custom styles for this template -->
<link href="bootstrap/css/stats.css" rel="stylesheet">

</head>

<body>

	<div id="container" class="container">

		<h1>Acá van las estadísticas</h1>

	</div>
	<!-- /container -->
	
	<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/ripples.min.js"></script>
	<script src="bootstrap/js/material.min.js"></script>

	<!-- para las estadísticas -->
	<script type="text/javascript"
		src="https://code.highcharts.com/highcharts.js"></script>

	<script
		src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
	<script src="bootstrap/js/floating-label.js"></script>
	<script type="text/javascript">
		
		var nombres_cursos = ${nombres_cursos};
		var nombres_categorias = ${nombres_categorias};
		var aprobados = ${cantidad_aprobados_por_curso};
		var desaprobados = ${cantidad_desaprobados_por_curso};
		var abandonaron = ${cantidad_abandonaron_por_curso};
		var titulo = "Inscriptos / Categoria";

		$('#container')
				.highcharts(
						{
							chart : {
								type : 'column'
							},
							title : {
								text : "Inscriptos / Categoria"
							},
							xAxis : {
								categories : nombres_cursos
							},
							yAxis : {
								min : 0,
								title : {
									text : 'Total de inscriptos'
								},
								stackLabels : {
									enabled : true,
									style : {
										fontWeight : 'bold',
										color : (Highcharts.theme && Highcharts.theme.textColor)
												|| 'gray'
									}
								}
							},
							legend : {
								align : 'right',
								x : -30,
								verticalAlign : 'top',
								y : 25,
								floating : true,
								backgroundColor : (Highcharts.theme && Highcharts.theme.background2)
										|| 'white',
								borderColor : '#CCC',
								borderWidth : 1,
								shadow : false
							},
							tooltip : {
								headerFormat : '<b>{point.x}</b><br/>',
								pointFormat : '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
							},
							plotOptions : {
								column : {
									stacking : 'normal',
									dataLabels : {
										enabled : true,
										color : (Highcharts.theme && Highcharts.theme.dataLabelsColor)
												|| 'white'
									}
								}
							},
							series : [ {
								name : 'Abandonaron',
								data : abandonaron
							}, {
								name : 'Desaprobaron',
								data : desaprobados
							}, {
								name : 'Aprobaron',
								data : aprobados
							} ]
						});
	</script>

</body>
</html>

