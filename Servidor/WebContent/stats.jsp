<%@ page import="java.lang.*"%>
<%@ page import="java.util.ArrayList;"%>
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

	<div id="nombres_categorias">
		<c:forEach items="${nombres_categorias}" var="nombre_categoria"> '${nombre_categoria}',</c:forEach>
		'holanda'
	</div>


	<div id="lista_abandonaron">
		<c:forEach items="${cantidad_abandonaron_por_categoria}"
			var="abandono"> ${abandono}, </c:forEach>
		0
	</div>

	<div id="lista_desaprobados">
		<c:forEach items="${cantidad_desaprobados_por_categoria}"
			var="desaprobo"> ${desaprobo}, </c:forEach>
		0
	</div>

	<div id="lista_aprobados">
		<c:forEach items="${cantidad_aprobados_por_categoria}" var="aprobo"> ${aprobo}, </c:forEach>
		0
	</div>





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
		var cat = '\'hola\', \'si\', \'chau\'';

		// var categorias = [ $.trim($('#nombres_categorias').text()) ];
		//var categorias = ${nombres_categorias};
		
		var nombres_categorias;
<%-- 		<%
		ArrayList<String> nombres_categorias = (ArrayList<String>) request.getAttribute("nombre_categorias");
		for (int i=0; i < nombres_categorias.size(); i++) {
		%>
		nombres_categorias[<%= i %>] = "<%= nombres_categorias.get(i) %>"; 
		<% } %>	 --%>	
		
		var aprobados = [ $.trim($('#lista_aprobados').text()) ];
		var desaprobados = [ $.trim($('#lista_desaprobados').text()) ];
		var abandonaron = [ $.trim($('#lista_abandonaron').text()) ];
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
								categories : nombres_categorias
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
							/* data : [ $('#lista_abandonaron').innerText ] */
							}, {
								name : 'Desaprobaron',
								data : desaprobados
							/* data : [ $('#lista_desaprobaron').innerText ] */
							}, {
								name : 'Aprobaron',
								data : aprobados
							/* data : [ $('#lista_aprobaron').innerText ] */
							} ]
						});
	</script>

</body>
</html>

