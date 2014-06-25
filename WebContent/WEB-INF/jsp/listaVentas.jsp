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
			
				var fechai = $("#fechai").val();
				var fechaf = $("#fechaf").val();

				$(".glerror").remove();
											
				if($.trim(fechai) == ""){
						$("#fechai")
							.focus()
							.after(
							"<span class='glerror'>Selecciona la fecha de inicio</span>");
							return false;
				}
				if($.trim(fechaf) == ""){
						$("#fechaf")
							.focus()
							.after(
							"<span class='glerror'>Selecciona la fecha de fin</span>");
							return false;
				}
				
				var url = "${pageContext.request.contextPath}/productosVentas.htm?cantidad=" + $("#cantidad").val() + "&operacion=" + $("#operacion").val() +
				"&fechai="+$("#fechai").val()+"&fechaf="+$("#fechaf").val();
				$(location).attr('href',url);
			}
			
			function exportarExcel(){
				var url = "${pageContext.request.contextPath}/productosVentas.htm?excel=yes&cantidad=${cantidad}&operacion=${operacion}&fechai=${fechai}&fechaf=${fechaf}";
				$(location).attr('href',url);
			}
			
 
            var chart;
            $(document).ready(function() {
            
            $(".fecha").change(function() {
				$(".glerror").remove();
			});
            
                      $(function() {
    $( ".fecha" ).datepicker();
    
    $(function($){
    $.datepicker.regional['es'] = {
        closeText: 'Cerrar',
        prevText: '<Ant',
        nextText: 'Sig>',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene','Feb','Mar','Abr', 'May','Jun','Jul','Ago','Sep', 'Oct','Nov','Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
        dayNamesShort: ['Dom','Lun','Mar','Mié','Juv','Vie','Sáb'],
        dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','Sá'],
        weekHeader: 'Sm',
        dateFormat: 'dd/mm/yy',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: ''
    };
    $.datepicker.setDefaults($.datepicker.regional['es']);
});
  });
            
            solonumeros($(".cantidad"));
            noescribir($(".fecha"));
                chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'container',
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text: 'Venta de productos'
                    },
                    tooltip: {
                        formatter: function() {
                            return '<b>'+ this.point.name +'</b>: '+ this.y +' ';
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
                categories: [
                    <c:forEach var="prod" items="${model.lstProd}" varStatus="status">
                       		'${prod.fcnomproducto}',
                       </c:forEach>
                ]
                    , labels: {
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
                    text: 'Cantidad Vendida'
                }
            },
                    series: [{
                        type: 'column',
                        name: 'Cantidad de productos vendidos',
                        data: [
                       <c:forEach var="prod" items="${model.lstProd}" varStatus="status">
                       		['${prod.fcnomproducto}',   ${prod.ficantidad}],
                       </c:forEach>
                        ],
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
				<label> Fecha Inicio </label> <input name="fechai" id="fechai" class="fecha" size="10" value="${fechai}" /> <label> Fecha Fin </label> <input name="fechaf" id="fechaf" class="fecha" size="10" value="${fechaf}" /> <label> Cantidad a mostrar </label> <input name="cantidad" id="cantidad"
					type="number" class="cantidad" <c:if test="${not empty cantidad}">
						value="${cantidad}"
						</c:if> <c:if test="${empty cantidad}">
						value="10"
						</c:if>> <label> Operacion </label> <select name="operacion" id="operacion">
					<option value="1">Mas Vendidos</option>
					<option value="2" <c:if test="${operacion == '2'}"> selected </c:if>>Menos Vendidos</option>
				</select> <a href="#" onclick="crearGrafica()" style="margin: 0em; padding: 0em;"><img src="${pageContext.request.contextPath}/css/img/buscarGrafica.png" width="30px" height="30px" style="margin-top: 2em; margin-bottom: -10px; padding: 0em; margin-right: 0px" /> </a> <a href="#"
					onclick="exportarExcel()" style="margin: 0em; padding: 0em;"><img src="${pageContext.request.contextPath}/css/img/tabla.png" width="30px" height="30px" style="margin-top: 2em; margin-bottom: -10px; padding: 0em; margin-left: 10px" /> </a>
			</div>
		</article>
		<div id="container" style="width: 800px; height: 400px; margin: 0 auto"></div>
	</section>
</body>
</html>
