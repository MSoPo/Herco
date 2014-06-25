<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/fragmentos/recursos.jsp' />
<script type="text/javascript">
		$(document)
			.ready(
					function() {
						solonumeros($(".cantidad"));
						
						$("#dialog").dialog({
							autoOpen : false,
							modal : true,
							show: {
					        effect: "blind",
					        duration: 800
					      },
					      hide: {
					        effect: "blind",
					        duration: 300
					      }
						});
						
						$("#cancelar").click(function(e) {
							e.preventDefault();

							$("#dialog").dialog({
								buttons : {
									"Si" : function() {
										editar($('#idventa').val(), 'cambiarVenta.htm?idventa=');
									},
									"No" : function() {
										$(this).dialog("close");
									}
								}
							});

							$("#dialog").dialog("open");
						});
						
						}
						);
						
		
						
		</script>
</head>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion">Cancelar Venta</div>
			<div class="error">
				<c:out value="${model.message}"></c:out>
			</div>
			<table>
				<tr>
					<td>Ingresa el numero de venta:</td>
					<td><input id="idventa" type="number" min="1" class="cantidad" value="${model.idventa}" /></td>
				</tr>
				<tr>

				</tr>
				<tr>
					<td colspan="2" align="center"><input type="button" value="Cancelar Venta" class="buttonForm" id="cancelar"></td>
				</tr>
			</table>
			<br /> <br />

		</article>
		<article>

			El numero de la venta se encuentra en el ticket <br /> <br /> <img src="${pageContext.request.contextPath}/css/img/ticket.JPG" /><br> <br /> * Para poder realizar el cambio en la venta esta no debe de ser mayor a 10 dias <br /> y se debe de presentar el ticket <br /> <br /> <br />
		</article>
	</section>

	<div id="dialog" title="Cancelar">
		¿Seguro que deseas cancelar la venta? <br /> <br /> Una vez cancelada los productos son <br> regresados al inventario.
	</div>

</body>
</html>
