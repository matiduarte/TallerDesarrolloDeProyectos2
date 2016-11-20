<%@ page import="java.lang.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="entities.Report"%>
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

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">

</head>

<body>
	
	<div class="navbar navbar-default">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
	      </button>
	      <a class="navbar-brand" href="javascript:void(0)">Estadísticas</a>
	    </div>
	    <div class="navbar-collapse collapse navbar-inverse-collapse"> 
	    </div>
	  </div>
	</div>
	
	<div class="container">
		
		<input type="text" id="from" class="fechaInput" placeholder="Desde"/>
		<input type="text" id="until" class="fechaInput" placeholder="Hasta"/>		
    	<button class="btn btn-raised btn-primary searchButton btnNew" onclick="search();"> 
    		<img  src="../images/search_icon.png" class="searchButtonImage" alt="Buscar" >
		</button>		
		
		<table id="tabla" class="display" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Categoria</th>
					<th>Cursos por categoría</th>
					<th>Inscriptos</th>
					<th>Abandonaron</th>
					<th>Desaprobaron</th>
					<th>Aprobaron</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${reportes_cursos}" var="reporte">
				<tr>
					<td>${reporte.getCategory()}</td>
					<td>${reporte.getCourseName()}</td>
					<td>${reporte.getTotalPupils()}</td>
					<td>${reporte.getGiveUp()}</td>
					<td>${reporte.getNoPass()}</td>
					<td>${reporte.getPass()}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		
		<div id="container"></div>
		
		
	</div>
	
	
	
	<!-- /container -->

	<!-- <script src="//code.jquery.com/jquery-1.10.2.min.js"></script> -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/ripples.min.js"></script>
	<script src="bootstrap/js/material.min.js"></script>

	<!-- para las estadísticas -->
	<script type="text/javascript" src="https://code.highcharts.com/highcharts.js"></script>	

	<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>

	<script src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
		
	<script src="bootstrap/js/floating-label.js"></script>
	
	<script type="text/javascript">
		var nombres_categorias = ${ nombres_categorias };
		var aprobados = ${ cantidad_aprobados_por_curso };
		var desaprobados = ${ cantidad_desaprobados_por_curso };
		var abandonaron = ${ cantidad_abandonaron_por_curso };
		var titulo = "Inscriptos / Categoria";

		$('#container')
				.highcharts(
						{
							chart : {
								type : 'column'
							},
							title : {
								text : "Alumnos Inscriptos por Categoria"
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
								color: 'gray',
								data : abandonaron
							}, {
								name : 'Desaprobaron',
								color: '#ff1919',
								data : desaprobados
							}, {
								name : 'Aprobaron',
								color: '#74d600',
								data : aprobados
							} ]
						});
	</script>

	<script type="text/javascript">
	
	$(document).ready(function() {
	    var table = $('#tabla').DataTable({
	        "columnDefs": [
	            { "visible": false, "targets": 0 }
	        ],
	        "order": [[ 0, 'asc' ]],
	        "displayLength": 25,
	        "drawCallback": function ( settings ) {
	            var api = this.api();
	            var rows = api.rows( {page:'current'} ).nodes();
	            var last=null;
	 
	            api.column(0, {page:'current'} ).data().each( function ( group, i ) {
	                if ( last !== group ) {
	                    $(rows).eq( i ).before(
	                        '<tr class="group" style="background-color:lightgray;font-weight:bold"><td colspan="5">'+group+'</td></tr>'
	                    );
	 
	                    last = group;
	                }
	            } );
	        },
		    "language": {
		        "lengthMenu": "Mostrar _MENU_ filas por página",
		        "search": "Buscar",
		        "zeroRecords": "No se encontro registros",
		        "info": "Mostrando página _PAGE_ de _PAGES_",
		        "infoEmpty": "No hay registros disponibles",
		        "infoFiltered": "(filtrado de _MAX_ registros totales)"
	        }
	    } );
	    
	    $("#tabla_previous").html("Anterior");
	    $("#tabla_next").html("Siguiente");
	 
	    // Order by the grouping
	    $('#tabla tbody').on( 'click', 'tr.group', function () {
	        var currentOrder = table.order()[0];
	        if ( currentOrder[0] === 0 && currentOrder[1] === 'asc' ) {
	            table.order( [ 0, 'desc' ] ).draw();
	        }
	        else {
	            table.order( [ 0, 'asc' ] ).draw();
	        }
	    } );
	   
	} );
	
	function search() {
		var url = window.location.href;
		
		var from = $("#from").val().toLowerCase();
		var until = $("#until").val().toLowerCase();
		
		var new_href = url.replace(/(from=).*?(&)/, '$1' + $("#from").val().toLowerCase() + '$2');
		new_href = new_href.replace(/(until=).*/, '$1' + $("#until").val().toLowerCase());
		
		window.location.href = new_href;
	}
	
    function goBack(){

		window.location.href = '/Servidor/courselist';
	}
	
	</script>
</body>
</html>

