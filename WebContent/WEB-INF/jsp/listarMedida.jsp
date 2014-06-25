<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
<!DOCTYPE html">
<html>
<head>
<c:import url='/WEB-INF/jsp/fragmentos/recursos.jsp' />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ordenarTabla/jquery.tablesorter.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/ordenarTabla/themes/blue/style.css">

<script type="text/javascript">
	$(document)
			.ready(
					function() {
					
						$("#tablasort").tablesorter({sortList:[[1,0]], widgets: ['zebra'],
					   headers: { 
				            4: { sorter: false },
				            3: { sorter: false },
				            2: { sorter: false }   
        				} });
					
						autompletar($("#nombre"),
								"${pageContext.request.contextPath}/autocompletarMedida.htm?campo=fcdescmedida");
						submitEnter(
								$("#nombre"),
								"${pageContext.request.contextPath}/listarMedida.htm?nombre=",
								true);
					});
</script>
</head>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion">Lista de Medidas</div>

			<div class="accionesCatalogo">

				<a href="<c:url value="agregarMedida.htm"/>"><img src="${pageContext.request.contextPath}/css/img/agregar1.png" width="30px" height="30px" /> </a> <a href="#"><img src="${pageContext.request.contextPath}/css/img/buscar1.png" width="30px" height="30px" onclick="mostrar($('.buscarCatalogo'))" />
				</a>
				<div class="buscarCatalogo" <c:if test="${empty  nombre}"> hidden="hidden"</c:if>>
					<label>Nombre</label> <input name="nombre" id="nombre" value="${nombre}">
				</div>
			</div>
			<div class="datagrid catalogo">
				<table <c:if test="${fn:length(model.medida) > 0}"> id="tablasort" class="tablesorter"</c:if>>
					<thead>
						<tr>
							<th>No. Medida</th>
							<th>Nombre</th>
							<th>Solo Enteros</th>
							<th>Activo</th>
							<th>Editar</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach items="${model.medida}" var="medida" varStatus="status">
							<tr>
								<td><c:out value="${medida.fiidmedida}" /></td>
								<td><c:out value="${medida.fcdescmedida}" /></td>
								<td><c:if test="${medida.fcvaloresenteros == '1'}">
										<img src="${pageContext.request.contextPath}/css/img/activo.png" width="20px" height="20px" />
									</c:if> <c:if test="${medida.fcvaloresenteros == '0'}">
										<img src="${pageContext.request.contextPath}/css/img/noactivo.png" width="20px" height="20px" />
									</c:if></td>
								<td><a href="listarMedida.htm?id=${medida.fiidmedida}"><c:if test="${medida.fcactivo == '1'}">
											<img src="${pageContext.request.contextPath}/css/img/activo.png" width="20px" height="20px" />
										</c:if> <c:if test="${medida.fcactivo == '0'}">
											<img src="${pageContext.request.contextPath}/css/img/noactivo.png" width="20px" height="20px" />
										</c:if></a></td>
								<td><a href="agregarMedida.htm?fiidmedida=${medida.fiidmedida}"><img src="${pageContext.request.contextPath}/css/img/editar.png" width="20px" height="20px" /> </a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</article>
	</section>
</body>
</html>