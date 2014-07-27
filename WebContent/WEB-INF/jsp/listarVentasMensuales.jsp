<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/fragmentos/recursos.jsp' />
<script type="text/javascript" src="js/graficos/highcharts.js"></script>
<!-- Este archivo es para darle un estilo (Este archivo es Opcional) -->
<script type="text/javascript" src="js/graficos/themes/grid.js"></script>
<!-- Este archivo es para poder exportar los datos que obtengamos -->
<script type="text/javascript" src="js/graficos/modules/exporting.js"></script>


<script type="text/javascript">
		
		
			
			function crearGrafica(){
				var url = "${pageContext.request.contextPath}/ventasMensuales.htm?mes=" + $('#mes').val() +  "&ano=" + $('#ano').val();
				$(location).attr('href',url);
			}
			
			function exportarExcel(){
				var url = "${pageContext.request.contextPath}/stock.htm?excel=yes&cantidad=${cantidad}&operacion=${operacion}";
				$(location).attr('href',url);
			}
			
 
            var chart;
            $(document).ready(function() {
            	
            	var anos = "";
                for(var i = 2013; i <= (new Date).getFullYear(); i ++){
               	anos += "<option valur='"+i+"'>"+i+"</option>";
                }
                $('#ano').html(anos);
                
                var mes = ${mes};
                var ano = ${ano};
                
                $('#ano').val(ano);
                $('#mes').val(mes);
            
            var lstVentas = JSON.parse('${model.lstVentas}');
            var lstDias = [];
            var lstcant = [];
            for(var i = 0; i < lstVentas.length; i++){
            	lstDias.push(lstVentas[i][0]);
            	lstcant.push(lstVentas[i][3]);
            }
            
                chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'container',
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text: 'Ventas de ' + $('option[value="' + $('#mes').val() +'"]').html()
                    },
                    tooltip: {
                        formatter: function() {
                            return '<b> Monto </b>: '+ this.y +' ';
                        }
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: false
                            },
                            showInLegend: true
                        }
                    },
                    xAxis: {
                categories: 
                	lstDias, 
                	labels: {
                    rotation: -45,
                    align: 'right',
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            },
             yAxis: {
                min: 0,
                title: {
                    text: 'Cantidad'
                }
            },
                    series: [{
                        type: 'column',
                        name: 'Cantidad diario',
                        data: lstcant,
                        dataLabels: {
		                    enabled: true,
		                    color: '#39a7ff',
		                    style: {
		                        fontSize: '13px',
		                        fontFamily: 'Verdana, sans-serif'
		                    }
		                 }
                    }]
                });
          
            });
 
        </script>
</head>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion" style="font-size: .6em">
				<label> Mes </label> <select name="mes" id="mes">
					<option value="1">Enero</option>
					<option value="2">Febrero</option>
					<option value="3">Marzo</option>
					<option value="4">Abril</option>
					<option value="5">Mayo</option>
					<option value="6">Junio</option>
					<option value="7">Julio</option>
					<option value="8">Agosto</option>
					<option value="9">Septiembre</option>
					<option value="10">Octubre</option>
					<option value="11">Noviembre</option>
					<option value="12">Diciembre</option>
				</select> <label> Año </label> <select name="ano" id="ano">

				</select> <a href="#" onclick="crearGrafica()" style="margin: 0em; padding: 0em;"><img src="${pageContext.request.contextPath}/css/img/buscarGrafica.png" width="30px" height="30px" style="margin-top: 2em; margin-bottom: -10px; padding: 0em; margin-right: 0px" /> </a>
			</div>
		</article>
		<div id="container" style="width: 800px; height: 400px; margin: 0 auto"></div>
	</section>
</body>
</html>

