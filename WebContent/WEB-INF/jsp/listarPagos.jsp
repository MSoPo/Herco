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
					$("#totalVentas").html( "$ " +  val.toFixed(2));
							
					
					$("#pagos").dialog({
							autoOpen : false,
							modal : true,
							height: 300,
      						width: 700,
							show: {
					        effect: "blind",
					        duration: 800
					      },
					      hide: {
					        effect: "blind",
					        duration: 300
					      }
						});
					
					
					
					$("#tablasort").tablesorter({sortList:[[5,1]], widgets: ['zebra'],
					   headers: { 
				            7: { sorter: false },
				            6: { sorter: false }  
        				} });
						autompletar($("#nombre"),
								"${pageContext.request.contextPath}/autocompletarCliente.htm?campo=fcnombre");
						autompletar($("#apepat"),
								"${pageContext.request.contextPath}/autocompletarCliente.htm?campo=fcapepat");
						submitEnter(
								$("#apepat"),
								"${pageContext.request.contextPath}/listarPagos.htm?apepat=",
								true);
						submitEnter(
								$("#nombre"),
								"${pageContext.request.contextPath}/listarPagos.htm?nombre=",
								true);
					});
					
					
		function doAjaxPost(codigo) {
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/buscarPagos.htm",
			data : "codigo=" + codigo,
			success : function(response) {
				if (response.status == "SUCCESS") {
				var table = "<div class='datagrid detalle catalogo'><table ><thead><tr><th>Cantidad</th><th>Nota</th><th>Usuario</th><th>Fecha</th></tr></thead><tbody>";
					$.each( response.result, function( key, value ) {
					  table +=  "<tr><td>" + value.fdcantidad + "</td>";
					  table += "<td>" + value.fcobservacion + "</td>";
					  table += "<td>" + value.fcusuariomodifica + "</td>";
					  table += "<td>" + value.fcactivo + "</td></tr>";
					});
					table += "</tbody></table></div>";
					$("#pagos").html(table);
				$("#pagos").dialog({
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
</script>
</head>
<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
<section id="cuerpo">
	<article>
		<div class="accion">Lista de Pagos Pendientes</div>

		<div class="accionesCatalogo">

			<a href="#"><img src="${pageContext.request.contextPath}/css/img/buscar1.png" width="30px" height="30px" onclick=mostrar($( '.buscarCatalogo'));
/> </a>
			<div class="buscarCatalogo" <c:if test="${empty  nombre && empty apepat}"> hidden="hidden"</c:if>>
				<label> Nombre </label> <input name="nombre" id="nombre" value="${nombre}"> <label> Apellido Paterno </label> <input name="apepat" id="apepat" value="${apepat}">
			</div>
		</div>
		<div>
			<label> Total </label> <label id="totalVentas" style="color: blue; background-color: white; padding-left: 1em; padding-right: 1em"></label>
		</div>
		<div class="datagrid catalogo">
			<table <c:if test="${fn:length(model.pagos) > 0}"> id="tablasort" class="tablesorter"</c:if>>
				<thead>
					<tr>
						<th>Nombre</th>
						<th>Apellidos</th>
						<th>Fecha Venta</th>
						<th>Total Pagado</th>
						<th>Total Venta</th>
						<th>Pendiente</th>
						<th>Pagar</th>
						<th>Detalles</th>

					</tr>
				</thead>
				<tbody>
					<c:forEach items="${model.pagos}" var="pago" varStatus="status">
						<tr>
							<td><c:out value="${pago.venta.cliente.fcnombre}" /></td>
							<td><c:out value="${pago.venta.cliente.fcapepat}" /> &nbsp; <c:out value="${pago.venta.cliente.fcapemat}" /></td>
							<td><c:out value="${pago.venta.fdfechaventa}" /></td>
							<td><c:out value="${pago.fdcantidad}" /></td>
							<td><c:out value="${pago.venta.fdtotal}" /></td>
							<td class="total"><c:out value="${pago.venta.fdtotal - pago.fdcantidad}" /></td>
							<td><a href="#" onclick="editar('${pago.venta.fiidventa}', 'agregarPago.htm?fiidventa=')"><img src="${pageContext.request.contextPath}/css/img/pesos.png" width="20px" height="20px" /> </a></td>
							<td><a href="#" onclick="doAjaxPost('${pago.venta.fiidventa}')"><img src="${pageContext.request.contextPath}/css/img/detalle.png" width="20px" height="20px" /> </a></td>
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