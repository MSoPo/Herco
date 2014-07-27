<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
			<tr bgcolor="cadetblue">
				<th>No. Venta</th>
				<th>Fecha</th>
				<th>Usuario</th>
				<th>Cliente</th>
				<th>Caja</th>
				<th>Observaciones</th>
				<th>Subtotal</th>	
				<th>IVA</th>
				<th>Total</th>
				<th>Venta Cancelada</th>
				<th>No. Venta de Cambio</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="venta" items="${model.lstVenta}" varStatus="status">
				<tr bgcolor="lightblue">
					<td align="center">${venta.fiidventa}</td>
					<td align="center">${venta.fdfechaventa}</td>
					<td align="center">${venta.usuario.fcusuario}</td>
					<td align="center">${venta.cliente.fcnombre} ${venta.cliente.fcapepat} ${venta.cliente.fcapemat}</td>
					<td align="center">${venta.caja.fcdesccaja}</td>
					<td align="center">${venta.fcobservaciones}</td>
					<td align="center"><fmt:formatNumber value="${venta.fdtotal / 1.16}" pattern="0.00"/></td>
					<td align="center"><fmt:formatNumber value="${venta.fdtotal - ( venta.fdtotal / 1.16 )}" pattern="0.00"/></td>
					<td align="center">${venta.fdtotal}</td>
					<td align="center"><c:if test="${venta.fcactivo == '0'}">Si</c:if></td>
					<td align="center"><c:if test="${venta.fiidventaanterior > 0}">${venta.fiidventaanterior}</c:if></td>
				</tr>
				<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>Clave Producto</td>
				<td>NombreProducto</td>
				<td>Cantidad</td>
				<td>Precio Iva</td>
				<td>Subtutal Iva</td>
				<td>Precio Venta</td>
				<td>Subtotal</td>
			</tr>
				<c:forEach var="detalleventa" items="${venta.detalleVenta}" varStatus="status">
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td align="center">${detalleventa.producto.fiidproducto}</td>
						<td align="center">${detalleventa.producto.fcnomproducto}</td>
						<td align="center">${detalleventa.ficantidad}</td>
						<td align="center"><fmt:formatNumber value="${detalleventa.fdprecioventa / 1.16}" pattern="0.00"/></td>
						<td align="center"><fmt:formatNumber value="${detalleventa.fdsubtotal / 1.16}" pattern="0.00"/></td>
						<td align="center">${detalleventa.fdprecioventa}</td>
						<td align="center">${detalleventa.fdsubtotal}</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
