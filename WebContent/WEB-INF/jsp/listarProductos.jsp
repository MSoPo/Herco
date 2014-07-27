<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<c:import url='/WEB-INF/jsp/fragmentos/recursos.jsp' />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ordenarTabla/jquery.tablesorter.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/ordenarTabla/themes/blue/style.css">
<script type="text/javascript">
	$(document)
			.ready(
					function() {
					
					  $("#tablasort").tablesorter({sortList:[[1,0]], widgets: ['zebra'],
					   headers: { 
				            0: { sorter: false }, 
				            6: { sorter: false },
				            8: { sorter: false },
				            9: { sorter: false },
				            10: { sorter: false }      
        				} });

						$("#nombre")
								.autocomplete(
										{
											source : "${pageContext.request.contextPath}/autocompletarProd.htm",
											select : function(a, b) {
												$(this).val(b.item.value);
											}
										});

						$("#nombre").keypress(
								function(e) {
									if (e.which == 13) {
										$(location).attr(
												'href',
												"${pageContext.request.contextPath}/listarProductos.htm?nombre="
														+ $("#nombre").val());
									}
								});

						$("#codigo").keypress(
								function(e) {
									if (e.which == 13) {
										$(location).attr(
												'href',
												"${pageContext.request.contextPath}/listarProductos.htm?codigo="
														+ $("#codigo").val());
									}
								});

					});

	function mostrar() {
		if ($('.buscarCatalogo').is(':visible'))
			$('.buscarCatalogo').hide('slow');
		else
			$('.buscarCatalogo').show('slow');
	}
</script>
</head>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion">Lista de Productos</div>

			<div class="accionesCatalogo">

				<a href="<c:url value="agregarProducto.htm"/>"><img src="${pageContext.request.contextPath}/css/img/agregar1.png" width="30px" height="30px" /> </a> <a href="#"><img src="${pageContext.request.contextPath}/css/img/buscar1.png" width="30px" height="30px" onclick="mostrar()" /> </a>
				<div class="buscarCatalogo" <c:if test="${empty  nombre && empty codigo}"> hidden="hidden"</c:if>>
					<label>Codigo</label> <input name="codigo" id="codigo" value="${codigo}"> <label>Nombre</label> <input name="nombre" id="nombre" value="${nombre}">
				</div>
			</div>

			<div class="datagrid catalogo">
				<table <c:if test="${fn:length(model.producto) > 0}"> id="tablasort" class="tablesorter"</c:if>>
					<thead>
						<tr>
							<th>Codigo</th>
							<th>Nombre de Producto</th>
							<!-- <th>Descripcion</th>  -->
							<th>Precio Unitario $</th>
							<th>Precio Mayoreo $</th>
							<th>Precio Proveedor $</th>
							<th>Precio Compra $</th>
							<th>Medida</th>
							<th>Cantidad</th>
							<th>Cantidad Mayoreo</th>
							<th>Activo</th>
							<th>Editar</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${model.producto}" var="producto" varStatus="status">
							<tr>
								<td style="font-size: 0.6em" class="inputtexto"><c:out value="${producto.fiidproducto}" /></td>
								<td style="font-size: 0.6em" class="inputtexto"><c:out value="${producto.fcnomproducto}" /></td>
								<!-- <td style="font-size: 0.6em" class="inputtexto"><c:out
										value="${producto.fcdescproducto}" />  -->
								</td>
								<td class="inputnumero"><c:out value="${producto.fdpreciounitario}" /></td>
								<td class="inputnumero"><c:out value="${producto.fdpreciomayoreo}" /></td>
								<td class="inputnumero"><c:out value="${producto.fdprecioproveedor}" /></td>
								<td class="inputnumero"><c:out value="${producto.fdpreciocompra}" /></td>
								<td><c:out value="${producto.medida.fcdescmedida}" /></td>
								<td class="inputnumero"><c:out value="${producto.ficantidad}" /></td>
								<td class="inputnumero"><c:out value="${producto.ficantidadmayore}" /></td>
								<td><a href="listarProductos.htm?id=${producto.fiidproducto}"> <c:if test="${producto.fcactivo == '1'}">
											<img src="${pageContext.request.contextPath}/css/img/activo.png" width="20px" height="20px" />
										</c:if> <c:if test="${producto.fcactivo == '0'}">
											<img src="${pageContext.request.contextPath}/css/img/noactivo.png" width="20px" height="20px" />
										</c:if>
								</a></td>
								<td><a href="agregarProducto.htm?fiidproducto=${producto.fiidproducto}"> <img src="${pageContext.request.contextPath}/css/img/editar.png" width="20px" height="20px" />
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