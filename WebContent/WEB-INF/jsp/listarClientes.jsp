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
				            5: { sorter: false },
				            4: { sorter: false },
				            6: { sorter: false }  
        				} });
					
						autompletar($("#nombre"),
								"${pageContext.request.contextPath}/autocompletarCliente.htm?campo=fcnombre");
						autompletar($("#apepat"),
								"${pageContext.request.contextPath}/autocompletarCliente.htm?campo=fcapepat");
						submitEnter(
								$("#apepat"),
								"${pageContext.request.contextPath}/listarClientes.htm?apepat=",
								true);
						submitEnter(
								$("#nombre"),
								"${pageContext.request.contextPath}/listarClientes.htm?nombre=",
								true);
					});
</script>
</head>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion">Lista de Clientes</div>

			<div class="accionesCatalogo">

				<a href="<c:url value="agregarCliente.htm"/>"><img src="${pageContext.request.contextPath}/css/img/agregar1.png" width="30px" height="30px" /> </a> <a href="#"><img src="${pageContext.request.contextPath}/css/img/buscar1.png" width="30px" height="30px"
					onclick="mostrar($('.buscarCatalogo'))" /> </a>
				<div class="buscarCatalogo" <c:if test="${empty  nombre && empty apepat}"> hidden="hidden"</c:if>>
					<label>Nombre</label> <input name="nombre" id="nombre" value="${nombre}"> <label>Apellido Paterno</label> <input name="apepat" id="apepat" value="${apepat}">
				</div>
			</div>
			<div class="datagrid catalogo">
				<table <c:if test="${fn:length(model.cliente) > 0}"> id="tablasort" class="tablesorter"</c:if>>
					<thead>
						<tr>
							<th>No. Cliente</th>
							<th>Nombre</th>
							<th>Apellidos</th>
							<th>Descuento</th>
							<th>Precio</th>
							<th>Activo</th>
							<th>Editar</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach items="${model.cliente}" var="cliente" varStatus="status">
							<tr>
								<td><c:out value="${cliente.fiidcliente}" /></td>
								<td><c:out value="${cliente.fcnombre}" /></td>
								<td><c:out value="${cliente.fcapepat}" /> &nbsp; <c:out value="${cliente.fcapemat}" /></td>
								<td><c:out value="${cliente.fddescuento}" /></td>
								<td><c:if test="${cliente.fcmayoreo == '1'}">
										Mayoreo
									</c:if> <c:if test="${cliente.fcmayoreo == '0'}">
										Publico
									</c:if> <c:if test="${cliente.fcmayoreo == '2'}">
										Proveedor
									</c:if></td>
								<td><a href="listarClientes.htm?id=${cliente.fiidcliente}"> <c:if test="${cliente.fcactivo == '1'}">
											<img src="${pageContext.request.contextPath}/css/img/activo.png" width="20px" height="20px" />
										</c:if> <c:if test="${cliente.fcactivo == '0'}">
											<img src="${pageContext.request.contextPath}/css/img/noactivo.png" width="20px" height="20px" />
										</c:if>
								</a></td>
								<td><a href="agregarCliente.htm?fiidcliente=${cliente.fiidcliente}"> <img src="${pageContext.request.contextPath}/css/img/editar.png" width="20px" height="20px" />
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