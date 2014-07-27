<%@page import="ventas.util.Constantes"%>
<%@page import="com.sun.corba.se.impl.orbutil.closure.Constant"%><%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
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
						solonumeros($(".cantidad"));
						var val = 0;
						$('.total').each(function(index, element) {
							$(element).find();
							val += (parseFloat($(this).html()));
						})
						$("#totalVentas").html("$ " + val.toFixed(2));

						noescribir($(".fecha"));

						$("#tablasort").tablesorter( {
							sortList : [ [ 1, 1 ] ],
							widgets : [ 'zebra' ],
							headers : {
								6 : {
									sorter : false
								}
							}
						});

						$(".fecha").datepicker();

						$(function($) {
							$.datepicker.regional['es'] = {
								closeText : 'Cerrar',
								prevText : '<Ant',
								nextText : 'Sig>',
								currentText : 'Hoy',
								monthNames : [ 'Enero', 'Febrero', 'Marzo',
										'Abril', 'Mayo', 'Junio', 'Julio',
										'Agosto', 'Septiembre', 'Octubre',
										'Noviembre', 'Diciembre' ],
								monthNamesShort : [ 'Ene', 'Feb', 'Mar', 'Abr',
										'May', 'Jun', 'Jul', 'Ago', 'Sep',
										'Oct', 'Nov', 'Dic' ],
								dayNames : [ 'Domingo', 'Lunes', 'Martes',
										'Miércoles', 'Jueves', 'Viernes',
										'Sábado' ],
								dayNamesShort : [ 'Dom', 'Lun', 'Mar', 'Mié',
										'Juv', 'Vie', 'Sáb' ],
								dayNamesMin : [ 'Do', 'Lu', 'Ma', 'Mi', 'Ju',
										'Vi', 'Sá' ],
								weekHeader : 'Sm',
								dateFormat : 'dd/mm/yy',
								firstDay : 1,
								isRTL : false,
								showMonthAfterYear : false,
								yearSuffix : ''
							};
							$.datepicker
									.setDefaults($.datepicker.regional['es']);
						});
					});

	function buscarVentas() {
		var url = "${pageContext.request.contextPath}/listarPagosFecha.htm?fechai="
				+ $("#fechai").val() + "&fechaf=" + $("#fechaf").val()
				+ "&noventa=";
		editar($("#noventa").val(), url);
	}

</script>
</head>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion">Lista de Ventas</div>


			<div class="buscarCatalogo" style="float: center; font-size: .8em">
				<label> Total </label> <label id="totalVentas" style="color: blue; background-color: white; padding-left: 1em; padding-right: 1em"></label>
				<label> No. Venta </label> <input name="noventa" id="noventa" type="number" class="cantidad" size="5" value="${noventa}" min="0" />  
				<label> Fecha Inicio </label> <input name="fechai" id="fechai" class="fecha" size="10" value="${fechai}" /> 
				<label> Fecha Fin </label> <input name="fechaf" id="fechaf" class="fecha" size="10" value="${fechaf}" /> 
				<a href="#" onclick=buscarVentas(); style="margin: 0em; padding: 0em;"><img
					src="${pageContext.request.contextPath}/css/img/buscarGrafica.png" width="30px" height="30px" style="margin-top: 2em; margin-bottom: -10px; padding: 0em; margin-right: 0px" /> </a> 
				
			</div>
			<div class="datagrid catalogo">
				<table id="tablaPagos" <c:if test="${fn:length(model.lstPagos) > 0}"> id="tablasort" class="tablesorter"</c:if>>
					<thead>
						<tr>
							<th>No. Venta</th>
							<th>Fecha Pago</th>
							<th>Usuario</th>
							<th>Cliente</th>
							<th>Observaciones</th>
							<th>Total</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${model.lstPagos}" var="pago" varStatus="status">
							<tr>
								<td><c:out value="${pago.venta.fiidventa}" /></td>
								<td><c:out value="${pago.fdfechamodifica}" /></td>
								<td><c:out value="${pago.usuario.fcusuario}" /></td>
								
								<td><c:out value="${pago.venta.cliente.fcnombre}" /> 
								&nbsp; <c:out value="${pago.venta.cliente.fcapepat}" /> 
								&nbsp; <c:out value="${pago.venta.cliente.fcapemat}" /></td>
								
								<td><c:out value="${pago.fcobservacion}" /></td>
								<td class="total"><c:out value="${pago.fdcantidad}" /></td>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</article>
	</section>
</body>
</html>