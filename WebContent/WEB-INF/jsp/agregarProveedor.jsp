<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
<!DOCTYPE html">
<html>
<head>
<c:import url='/WEB-INF/jsp/fragmentos/recursos.jsp' />
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						solonumeros($(".cantidad"));

						$("#catalogosForm")
								.submit(
										function() {
											var fiidproveedor = $("#fiidproveedor").val();
											var fcnombre = $("#fcnombre").val();
											var fcdescproveedor = $("#fcdescproveedor").val();

											$(".glerror").remove();
											
											if(fiidproveedor == 1){
											$("#fcnombre")
														.focus()
														.after(
																"<span class='glerror'>Este es un proveedor que <br> no se puede modificar</span>");
												return false;
											}
											
											if ($.trim(fcnombre) == "") {
												$("#fcnombre")
														.focus()
														.after(
																"<span class='glerror'>Ingresa el nombre</span>");
												return false;
											} else if ($.trim(fcdescproveedor) == "") {
												$("#fcdescproveedor")
														.focus()
														.after(
																"<span class='glerror'>Ingresa la descripcion</span>");
												return false;
											} 
										});

					});
</script>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion">Proveedor</div>
			<div class="agregarAccion">

				<c:choose>
					<c:when test="${catalogosForm.salida != null && catalogosForm.salida != ''}">
						<a href="<c:url value="${catalogosForm.salida}"/>">
					</c:when>
					<c:otherwise>
						<a href="<c:url value="listarProveedor.htm"/>">
					</c:otherwise>
				</c:choose>
				<img src="${pageContext.request.contextPath}/css/img/regresar.png" width="30px" height="30px" /> </a> <a class="btnguardar" href="#" onclick="$('#catalogosForm').submit();"><img src="${pageContext.request.contextPath}/css/img/guardar.png" width="30px" height="30px" /> </a>
			</div>

			<form:form method="post" commandName="catalogosForm" id="catalogosForm">
				<form:hidden path="operacion" id="operacion" />
				<form:hidden path="proveedor.fiidproveedor" id="fiidproveedor" />

				<div class="datagrid agregarEditar">
					<table class="agregarEditar">
						<tbody>

							<tr>
								<td><label>Nombre</label></td>
								<td><form:input path="proveedor.fcnomproveedor" id="fcnombre" placeholder="Ingresa el nombre" required="required" autofocus="autofocus" /></td>
							</tr>
							<tr class="alt">
								<td><label>Descripcion</label></td>
								<td><form:input path="proveedor.fcdescproveedor" placeholder="Ingresa la descripcion" id="fcdescproveedor" required="required" /></td>
							</tr>
							<tr>
								<td><label>Telefono</label></td>
								<td><form:input path="proveedor.fctelefono" placeholder="Ingresa el telefono" cssClass="cantidad" type="tel" /></td>
							</tr>
							<tr class="alt">
								<td><label>Celular</label></td>
								<td><form:input path="proveedor.fccelular" placeholder="Ingresa el celular" cssClass="cantidad" type="tel" /></td>
							</tr>
							<tr>
								<td><label>Direccion</label></td>
								<td><form:textarea cols="25" rows="3" path="proveedor.fcdireccion" placeholder="Ingresa la direccion" /></td>
							</tr>

						</tbody>
					</table>
				</div>
			</form:form>
		</article>
	</section>
</body>
</html>