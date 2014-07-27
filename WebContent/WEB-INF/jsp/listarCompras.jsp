<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/fragmentos/recursos.jsp' />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ordenarTabla/jquery.tablesorter.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/ordenarTabla/themes/blue/style.css">

<script type="text/javascript">
	$(document)
			.ready(
					function() {
					
					var val = 0;
					$('.total').each(function() {
					   val += (parseFloat($(this).html()));
					})
					$("#totalVentas").html( "$ " + val.toFixed(2));
					
					noescribir($(".fecha"));

						$("#pagos").dialog( {
							autoOpen : false,
							modal : true,
							height : 300,
							width : 700,
							show : {
								effect : "blind",
								duration : 800
							},
							hide : {
								effect : "blind",
								duration : 300
							}
						});

						$("#tablasort").tablesorter( {
							sortList : [ [ 1, 1 ] ],
							widgets : [ 'zebra' ],
							headers : {
								6 : {
									sorter : false
								}
							}
						});
						
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

	function doAjaxPost(codigo) {
		$
				.ajax( {
					type : "POST",
					url : "${pageContext.request.contextPath}/buscarCompras.htm",
					data : "codigo=" + codigo, 
					success : function(response) {
						if (response.status == "SUCCESS") {
							var table = "<div class='datagrid detalle catalogo'><table ><thead><tr><th>Producto</th><th>Cantidad</th><th>Precio</th><th>Subtotal</th></tr></thead><tbody>";
							$.each(response.result,
									function(key, value) {
										table += "<tr><td>" + value.producto.fcnomproducto
												+ "</td>";
										table += "<td>" + value.ficantidad
												+ "</td>";
										table += "<td>"
												+ value.fdpreciocompra
												+ "</td>";
										table += "<td>" + value.fdsubtotal
												+ "</td></tr>";
									});
							table += "</tbody></table></div>";
							$("#pagos").html(table);
							$("#pagos").dialog( {
								buttons : {
									"Regresar" : function() {
										$(this).dialog("close");
									}
								}
							});

							$("#pagos").dialog("open");

						}
					}
				});
	}
	
	function buscarVentas(){
		var url = "${pageContext.request.contextPath}/listarCompras.htm?fechai=" + $("#fechai").val() + "&fechaf=";
		editar($("#fechaf").val(), url);
	}
</script>
</head>
<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
<section id="cuerpo">
	<article>
		<div class="accion">Lista de Ventas</div>


		<div class="buscarCatalogo" style="float: center;">
			<label> Total </label> <label id="totalVentas" style="color: blue; background-color: white; padding-left: 1em; padding-right: 1em"></label> <label> Fecha Inicio </label> <input name="fechai" id="fechai" class="fecha" size="10" value="${fechai}" /> <label> Fecha Fin </label> <input name="fechaf"
				id="fechaf" class="fecha" size="10" value="${fechaf}" /> <a href="#" onclick="buscarVentas()" style="margin: 0em; padding: 0em;"><img src="${pageContext.request.contextPath}/css/img/buscarGrafica.png" width="30px" height="30px"
				style="margin-top: 2em; margin-bottom: -10px; padding: 0em; margin-right: 0px" /> </a>

		</div>
		<div class="datagrid catalogo">
			<table <c:if test="${fn:length(model.lstCompra) > 0}">id="tablasort"  class="tablesorter" </c:if>>
				<thead>
					<tr>
						<th>No. Venta</th>
						<th>Fecha Compra</th>
						<th>Usuario</th>
						<th>Cliente</th>
						<th>Total</th>
						<th>Detalles</th>

					</tr>
				</thead>
				<tbody>
					<c:forEach items="${model.lstCompra}" var="venta" varStatus="status">
						<tr>
							<td><c:out value="${venta.fiidcompra}" /></td>
							<td><c:out value="${venta.fdfechacompra}" /></td>
							<td><c:out value="${venta.usuario.fcusuario}" /></td>
							<td><c:out value="${venta.catproveedor.fcnomproveedor}" /></td>
							<td class="total"><c:out value="${venta.fdtotal}" /></td>
							<td><a href="#" onclick="doAjaxPost('${venta.fiidcompra}')"><img src="${pageContext.request.contextPath}/css/img/detalle.png" width="20px" height="20px" /> </a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</article>
</section>
</body>
<div id="pagos" style="text-align: center" title="Pagos">
	<table id="tablePagos">
	</table>
</div>
</html>