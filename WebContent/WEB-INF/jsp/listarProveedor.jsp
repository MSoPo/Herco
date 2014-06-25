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
					
					$("#tablasort").tablesorter({sortList:[[0,0]], widgets: ['zebra'],
					   headers: { 
				            4: { sorter: false }, 
				            5: { sorter: false },
				            3: { sorter: false },
				            2: { sorter: false }   
        				} });
					
						autompletar(
								$("#nombre"),
								"${pageContext.request.contextPath}/autocompletarProveedor.htm?campo=fcnomproveedor");
						submitEnter(
								$("#nombre"),
								"${pageContext.request.contextPath}/listarProveedor.htm?nombre=",
								true);
					});
</script>
</head>
<body>

	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion">Lista de Proveedores</div>

			<div class="accionesCatalogo">

				<a href="<c:url value="agregarProveedor.htm"/>"><img src="${pageContext.request.contextPath}/css/img/agregar1.png" width="30px" height="30px" /> </a> <a href="#"><img src="${pageContext.request.contextPath}/css/img/buscar1.png" width="30px" height="30px"
					onclick="mostrar($('.buscarCatalogo'))" /> </a>
				<div class="buscarCatalogo" <c:if test="${empty  nombre}"> hidden="hidden"</c:if>>
					<label>Nombre</label> <input name="nombre" id="nombre" value="${nombre}">
				</div>
			</div>
			<div class="datagrid catalogo">
				<table <c:if test="${fn:length(model.proveedor) > 0}"> id="tablasort" class="tablesorter"</c:if>>
					<thead>
						<tr>
							<th>Nombre</th>
							<th>Descripcion</th>
							<th>Telefono</th>
							<th>Celular</th>
							<th>Activo</th>
							<th>Editar</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach items="${model.proveedor}" var="proveedor" varStatus="status">
							<tr>
								<td><c:out value="${proveedor.fcnomproveedor}" /></td>
								<td><c:out value="${proveedor.fcdescproveedor}" /></td>
								<td><c:out value="${proveedor.fctelefono}" /></td>
								<td><c:out value="${proveedor.fccelular}" /></td>
								<td><a href="listarProveedor.htm?id=${proveedor.fiidproveedor}"> <c:if test="${proveedor.fcactivo == '1'}">
											<img src="${pageContext.request.contextPath}/css/img/activo.png" width="20px" height="20px" />
										</c:if> <c:if test="${proveedor.fcactivo == '0'}">
											<img src="${pageContext.request.contextPath}/css/img/noactivo.png" width="20px" height="20px" />
										</c:if>
								</a></td>
								<td><a href="agregarProveedor.htm?fiidproveedor=${proveedor.fiidproveedor}"> <img src="${pageContext.request.contextPath}/css/img/editar.png" width="20px" height="20px" />
								</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</article>
	</section>
</body>
</html>