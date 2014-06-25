<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
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
					
						 $("#tablasort").tablesorter({sortList:[[1,0]], widgets: ['zebra'],
					   headers: { 
				            4: { sorter: false }, 
				            3: { sorter: false }   
        				} });
					
						autompletar($("#usuario"),
								"${pageContext.request.contextPath}/autocompletarUsuario.htm?campo=fcusuario");
						autompletar($("#nombre"),
								"${pageContext.request.contextPath}/autocompletarUsuario.htm?campo=fcnombre");
						submitEnter(
								$("#nombre"),
								"${pageContext.request.contextPath}/listarUsuario.htm?nombre=",
								true);
						submitEnter(
								$("#usuario"),
								"${pageContext.request.contextPath}/listarUsuario.htm?usuarioCat=",
								true);

					});
</script>
</head>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion">Lista de Usuarios</div>

			<div class="accionesCatalogo">

				<a href="<c:url value="agregarUsuario.htm"/>"><img src="${pageContext.request.contextPath}/css/img/agregar1.png" width="30px" height="30px" /> </a> <a href="#"><img src="${pageContext.request.contextPath}/css/img/buscar1.png" width="30px" height="30px"
					onclick="mostrar($('.buscarCatalogo'))" /> </a>
				<div class="buscarCatalogo" <c:if test="${empty  nombre && empty usuarioCat}"> hidden="hidden"</c:if>>
					<label>Usuario</label> <input name="usuario" id="usuario" value="${usuarioCat}"> <label>Nombre</label> <input name="nombre" id="nombre" value="${nombre}">
				</div>
			</div>
			<div class="datagrid catalogo">
				<table <c:if test="${fn:length(model.usuario) > 0}"> id="tablasort" class="tablesorter"</c:if>>
					<thead>
						<tr>
							<th>Usuario</th>
							<th>Nombre</th>
							<th>Apellidos</th>
							<th>Activo</th>
							<th>Editar</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${model.usuario}" var="usuario" varStatus="status">
							<tr>
								<td><c:out value="${usuario.fcusuario}" /></td>
								<td><c:out value="${usuario.fcnombre}" /></td>
								<td><c:out value="${usuario.fcapepat}" /> &nbsp; <c:out value="${usuario.fcapemat}" /></td>
								<td><a href="listarUsuario.htm?id=${usuario.fcusuario}"> <c:if test="${usuario.fcactivo == '1'}">
											<img src="${pageContext.request.contextPath}/css/img/activo.png" width="20px" height="20px" />
										</c:if> <c:if test="${usuario.fcactivo == '0'}">
											<img src="${pageContext.request.contextPath}/css/img/noactivo.png" width="20px" height="20px" />
										</c:if>
								</a></td>
								<td><a href="agregarUsuario.htm?fcusuario=${usuario.fcusuario}"> <img src="${pageContext.request.contextPath}/css/img/editar.png" width="20px" height="20px" />
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