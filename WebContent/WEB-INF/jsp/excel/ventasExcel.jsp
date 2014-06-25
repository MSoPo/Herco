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
					+ "listaVentas.xls");
		%>
	<table align="left" border="2">
		<thead>
			<tr bgcolor="lightblue">
				<th>No. Venta</th>
				<th>Fecha</th>
				<th>Usuario</th>
				<th>Cliente</th>
				<th>Caja</th>
				<th>Observaciones</th>
				<th>Total</th>
				<th>Venta Cancelada</th>
				<th>No. Venta de Cambio</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="venta" items="${model.lstVenta}" varStatus="status">
				<tr>
					<td align="center">${venta.fiidventa}</td>
					<td align="center">${venta.fdfechaventa}</td>
					<td align="center">${venta.usuario.fcusuario}</td>
					<td align="center">${venta.cliente.fcnombre} ${venta.cliente.fcapepat} ${venta.cliente.fcapemat}</td>
					<td align="center">${venta.caja.fcdesccaja}</td>
					<td align="center">${venta.fcobservaciones}</td>
					<td align="center">${venta.fdtotal}</td>
					<td align="center"><c:if test="${venta.fcactivo == '0'}">Si</c:if></td>
					<td align="center"><c:if test="${venta.fiidventaanterior > 0}">${venta.fiidventaanterior}</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
