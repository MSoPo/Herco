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
				var url = "${pageContext.request.contextPath}/ventasAnuales.htm?ano=" + $('#ano').val();
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

                var ano = ${ano};
                $('#ano').val(ano);
            
            var lstVentas = JSON.parse('${model.lstVentas}');
            var lstDias = [];
            var lstcant = [];
            for(var i = 0; i < lstVentas.length; i++){
            	lstDias.push(obtenerMes(lstVentas[i][0]));
            	lstcant.push(lstVentas[i][2]);
            }
            
                chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'container',
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text: 'Ventas del ' + $('#ano').val()
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
            
            function obtenerMes(numMes){
            	var mes = "";
            	switch(numMes){
            	case 1:
            		mes = "Enero";
            		break;
            	case 2:
            		mes = "Febrero";
            		break;
            	case 3:
            		mes = "Marzo";
            		break;
            	case 4:
            		mes = "Abril";
            		break;
            	case 5:
            		mes = "Mayo";
            		break;
            	case 6:
            		mes = "Junio";
            		break;
            	case 7:
            		mes = "Julio";
            		break;
            	case 8:
            		mes = "Agosto";
            		break;
            	case 9:
            		mes = "Septiembre";
            		break;
            	case 10:
            		mes = "Octubre";
            		break;
            	case 11:
            		mes = "Noviembre";
            		break;
            	case 12:
            		mes = "Diciembre";
            		break;
            	
            	}
            	return mes;
            };
 
        </script>
</head>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion" style="font-size: .6em">

				<label> Año </label> <select name="ano" id="ano">

				</select> <a href="#" onclick="crearGrafica()" style="margin: 0em; padding: 0em;"><img src="${pageContext.request.contextPath}/css/img/buscarGrafica.png" width="30px" height="30px" style="margin-top: 2em; margin-bottom: -10px; padding: 0em; margin-right: 0px" /> </a>
			</div>
		</article>
		<div id="container" style="width: 800px; height: 400px; margin: 0 auto"></div>
	</section>
</body>
</html>