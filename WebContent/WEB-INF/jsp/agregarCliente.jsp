<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/fragmentos/recursos.jsp' />
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						solonumeros($(".cantidad"));
						solodecimales($(".decimal"));

						$("#catalogosForm")
								.submit(
										function() {
											var idcliente = $("#idcliente").val();
											var fcnombre = $("#fcnombre").val();
											var fcapepat = $("#fcapepat").val();
											var fddescuento = $("#fddescuento")
													.val();

											$(".glerror").remove();
											
											if(idcliente == 1){
												$("#fcnombre")
														.focus()
														.after(
																"<span class='glerror'>Este es un cliente que no <br> se puede modificar</span>");
												return false;
											}

											if ($.trim(fcnombre) == "") {
												$("#fcnombre")
														.focus()
														.after(
																"<span class='glerror'>Ingresa el nombre</span>");
												return false;
											} else if ($.trim(fcapepat) == "") {
												$("#fcapepat")
														.focus()
														.after(
																"<span class='glerror'>Ingresa el apellido paterno</span>");
												return false;
											} else if ($.trim(fddescuento) == "") {
												$("#fddescuento")
														.focus()
														.after(
																"<span class='glerror'>Ingresa el Descuento</span>");
												return false;
											}
										});

					});
</script>
</head>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion">Cliente</div>
			<div class="agregarAccion">


				<c:choose>
					<c:when test="${catalogosForm.salida != null && catalogosForm.salida != ''}">
						<a href="<c:url value="${catalogosForm.salida}"/>">
					</c:when>
					<c:otherwise>
						<a href="<c:url value="listarClientes.htm"/>">
					</c:otherwise>
				</c:choose>
				<img src="${pageContext.request.contextPath}/css/img/regresar.png" width="30px" height="30px" /> </a> <a class="btnguardar" href="#" onclick="$('#catalogosForm').submit();"><img src="${pageContext.request.contextPath}/css/img/guardar.png" width="30px" height="30px" /> </a>
			</div>

			<form:form method="post" commandName="catalogosForm" id="catalogosForm">
				<form:hidden path="operacion" id="operacion" />
				<form:hidden path="cliente.fiidcliente" id="idcliente" />
				<div class="datagrid agregarEditar">
					<table class="agregarEditar">
						<tbody>

							<tr>
								<td><label>Nombre</label></td>
								<td><form:input path="cliente.fcnombre" id="fcnombre" placeholder="Ingresa el nombre" required="required" autofocus="autofocus" /></td>
							</tr>
							<tr class="alt">
								<td><label>Apellido Paterno</label></td>
								<td><form:input path="cliente.fcapepat" id="fcapepat" placeholder="Ingresa el apellido paterno" required="required" /></td>
							</tr>
							<tr>
								<td><label>Apellido Materno</label></td>
								<td><form:input path="cliente.fcapemat" id="fcapemat" placeholder="Ingresa el apellido materno" /></td>
							</tr>
							<tr class="alt">
								<td><label>Telefono</label></td>
								<td><form:input path="cliente.fctelefono" id="fctelefono" placeholder="Ingresa el telefono" type="tel" cssClass="cantidad" /></td>
							</tr>
							<tr>
								<td><label>Celular</label></td>
								<td><form:input path="cliente.fccelular" id="fccelular" placeholder="Ingresa el celular" type="tel" cssClass="cantidad" /></td>
							</tr>
							<tr class="alt">
								<td><label>Direccion</label></td>
								<td><form:textarea cols="25" rows="3" path="cliente.fcdireccion" id="fcdireccion" placeholder="Ingresa la direccion" /></td>
							</tr>
							<tr>
								<td><label>Descuento</label></td>
								<td><form:input path="cliente.fddescuento" id="fddescuento" placeholder="Procentaje de descuento" type="number" step="0.01" min="0" max="100" required="required" cssClass="decimal" /></td>
							</tr>
							<tr class="alt">
								<td><label>¿Que precio aplicar?</label></td>
								<td><form:radiobutton path="cliente.fcmayoreo" value="0" />Publico<br> <form:radiobutton path="cliente.fcmayoreo" value="1" />Mayoreo<br> <form:radiobutton path="cliente.fcmayoreo" value="2" />Proveedor</td>
							</tr>
					</table>
				</div>
			</form:form>
		</article>
	</section>
</body>
</html>