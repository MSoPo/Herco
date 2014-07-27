<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<%
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline; filename="
					+ "listaProductos.xls");
		%>
	<table align="left" border="2">
		<thead>
			<tr bgcolor="lightblue">
				<th>Codigo</th>
				<th>Nombre</th>
				<th>Precio Unitario</th>
				<th>Precio Mayoreo</th>
				<th>Precio Compra</th>
				<th>Cantidad</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="prod" items="${model.lstProd}" varStatus="status">
				<tr>
					<td align="center">${prod.fiidproducto}</td>
					<td align="center">${prod.fcnomproducto}</td>
					<td align="center">${prod.fdpreciounitario}</td>
					<td align="center">${prod.fdpreciomayoreo}</td>
					<td align="center">${prod.fdpreciocompra}</td>
					<td align="center">${prod.ficantidad}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
