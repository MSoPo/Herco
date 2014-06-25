<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
<!DOCTYPE html">
<html>
<head>
<c:import url='/WEB-INF/jsp/fragmentos/recursos.jsp' />
<script type="text/javascript">
	$(document)
			.ready(
					function() {

						$("#catalogosForm")
								.submit(
										function() {
											var fcdesccaja = $("#fcdesccaja").val();
											var fcubicacioncaja = $(
													"#fcubicacioncaja").val();

											$(".glerror").remove();

											if ($.trim(fcdesccaja) == "") {
												$("#fcdesccaja")
														.focus()
														.after(
																"<span class='glerror'>Ingresa el nombre</span>");
												return false;
											} else if ($.trim(fcubicacioncaja) == "") {
												$("#fcubicacioncaja")
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
			<div class="accion">Caja</div>
			<div class="agregarAccion">
				<a href="<c:url value="listarCaja.htm"/>"><img src="${pageContext.request.contextPath}/css/img/regresar.png" width="30px" height="30px" /> </a> <a class="btnguardar" href="#" onclick="$('#catalogosForm').submit();"><img src="${pageContext.request.contextPath}/css/img/guardar.png"
					width="30px" height="30px" /> </a>
			</div>

			<form:form method="post" commandName="catalogosForm" id="catalogosForm">
				<form:hidden path="operacion" id="operacion" />
				<form:hidden path="caja.fiidcaja" />

				<div class="datagrid agregarEditar">
					<table class="agregarEditar">
						<tbody>

							<tr>
								<td><label>Nombre</label></td>
								<td><form:input path="caja.fcdesccaja" id="fcdesccaja" placeholder="Ejemplo: Caja 1" required="required" autofocus="autofocus" /></td>
							</tr>
							<tr class="alt">
								<td><label>Ubicacion</label></td>
								<td><form:input path="caja.fcubicacioncaja" id="fcubicacioncaja" placeholder="Ejemplo: Principal" required="required" /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</form:form>
		</article>
	</section>
</body>
</html>