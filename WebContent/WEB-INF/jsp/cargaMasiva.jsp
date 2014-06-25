<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/fragmentos/recursos.jsp' />
<script type="text/javascript">
			
		</script>
</head>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion">Carga Masiva de Inventarios</div>
			<div <c:if test="${message == 'Datos Guardados correctamente'}"> class="correcto" </c:if> <c:if test="${message != 'Datos Guardados correctamente'}"> class="error" </c:if>>
				<c:out value="${message}"></c:out>
			</div>
			<form:form method="post" id="cargaMasivaForm" action="${pageContext.request.contextPath}/cargaMasiva.htm" enctype="multipart/form-data" commandName="cargaMasivaForm">
				<table>
					<tr>
						<td>Selecciona fichero:</td>
						<td><form:input path="fichero" type="file" accept="application/vnd.ms-excel" /></td>
					</tr>
					<tr>

					</tr>
					<tr>
						<td colspan="2" align="center"><input type="button" value="Subir fichero" onclick="cargarSubmit($('#cargaMasivaForm'), '${pageContext.request.contextPath}/css/img/cargando.gif')" class="buttonForm"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>
		</article>
		<article>
			<c:if test="${not empty lstErrores}">
				<div class='datagrid catalogo'>
					<table>
						<thead>
							<tr>
								<th>Codigo/Fila</th>
								<th>Error</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="error" items="${lstErrores}">
								<tr>
									<td>${error.causa}</td>
									<td>${error.error}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</c:if>
			<c:if test="${empty lstErrores}">
		Formato Excel 97 - 2003	<br />
				<img src="${pageContext.request.contextPath}/css/img/formatoexel.JPG" />
				<br />
				<br />
				<br />
			* En el campo de medida se debe de colocar el numero de Medida no el nombre. </br>Prodas localizar el numero en el <a class="linkazul" href="${pageContext.request.contextPath}/listarMedida.htm">Catalogo de Medidas</a>
			</c:if>
		</article>
	</section>
</body>
</html>
