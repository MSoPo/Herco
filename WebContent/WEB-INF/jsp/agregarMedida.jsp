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
											var fcdescmedida = $(
													"#fcdescmedida").val();

											$(".glerror").remove();

											if ($.trim(fcdescmedida) == "") {
												$("#fcdescmedida")
														.focus()
														.after(
																"<span class='glerror'>Ingresa el nombre</span>");
												return false;
											}
										});

					}); 
</script>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion">Medida</div>
			<div class="agregarAccion">
				<a href="<c:url value="listarMedida.htm"/>"><img src="${pageContext.request.contextPath}/css/img/regresar.png" width="30px" height="30px" /> </a> <a class="btnguardar" href="#" onclick="$('#catalogosForm').submit();"><img src="${pageContext.request.contextPath}/css/img/guardar.png"
					width="30px" height="30px" /> </a>
			</div>

			<form:form method="post" commandName="catalogosForm" id="catalogosForm">
				<form:hidden path="operacion" id="operacion" />
				<form:hidden path="medida.fiidmedida" />

				<div class="datagrid agregarEditar">
					<table class="agregarEditar">
						<tbody>
							<tr>
								<td>Nombre de la medida</td>
								<td><form:input path="medida.fcdescmedida" id="fcdescmedida" placeholder="Ejemplo: Litro(s)" required="required" autofocus="autofocus" /></td>
							<tr class="alt">
								<td><label>¿Solo usar enteros?</label></td>
								<td><form:checkbox path="chk" /></td>
							</tr>
							</tr>

						</tbody>
					</table>
				</div>
			</form:form>
		</article>
	</section>
</body>
</html>