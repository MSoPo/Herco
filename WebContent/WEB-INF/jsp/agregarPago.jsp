<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
<!DOCTYPE html">
<html>
<head>
<c:import url='/WEB-INF/jsp/fragmentos/recursos.jsp' />
<script type="text/javascript">
	$(document)
			.ready(
					function() {

						solodecimales($(".decimal"));

						$("#catalogosForm")
								.submit(
										function() {
											var fdcantidad = $("#fdcantidad")
													.val();

											$(".glerror").remove();

											
											 if (isNaN(fdcantidad) || fdcantidad == "" || fdcantidad > <c:out value="${catalogosForm.pago.fdcantidad}"/>
											 || fdcantidad <= 0) {
												$("#fdcantidad")
														.focus()
														.after(
																"<span class='glerror'>Cantidad no valida</span>");
												$.unblockUI();
												return false;
											}
										});

					});
</script>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion">Pago</div>
			<div class="agregarAccion">
				<a href="<c:url value="listarPagos.htm"/>"><img src="${pageContext.request.contextPath}/css/img/regresar.png" width="30px" height="30px" /> </a> <a class="btnguardar" href="#" onclick="cargarSubmit($('#catalogosForm'), '${pageContext.request.contextPath}/css/img/cargando.gif');"><img
					src="${pageContext.request.contextPath}/css/img/guardar.png" width="30px" height="30px" /> </a>
			</div>

			<form:form method="post" commandName="catalogosForm" id="catalogosForm">
				<form:hidden path="operacion" id="operacion" />
				<form:hidden path="pago.venta.fiidventa" />
				<div class="datagrid agregarEditar">
					<table class="agregarEditar">
						<tbody>
							<tr>
								<td style="text-align: center" colspan="2">${catalogosForm.pago.venta.cliente.fcnombre}</td>
							</tr>
							<tr class="alt">
								<td style="text-align: center" colspan="2">${catalogosForm.pago.venta.cliente.fcapepat} &nbsp; ${catalogosForm.pago.venta.cliente.fcapemat}</td>
							</tr>
							<tr>
								<td><label>Detalles del pago</label></td>
								<td><form:input path="pago.fcobservacion" placeholder="Ingresa las observaciones" /></td>
							</tr>
							<tr class=alt>
								<td><label>Cantidad Pagada</label></td>
								<td><form:input path="pago.fdcantidad" id="fdcantidad" cssClass="decimal" type="number" step="0.1" min="0.1" max="${catalogosForm.pago.fdcantidad}" required="required" /></td>
							</tr>
					</table>
				</div>
			</form:form>
		</article>
	</section>
</body>
</html>