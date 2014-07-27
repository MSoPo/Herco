<%@page import="ventas.util.Constantes"%>
<%@page import="com.sun.corba.se.impl.orbutil.closure.Constant"%><%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
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
						solonumeros($(".cantidad"));
						var val = 0;
						$('.total').each(function() {
							val += (parseFloat($(this).html()));
						})
						$("#totalVentas").html("$ " + val.toFixed(2));

						noescribir($(".fecha"));

						$("#pagos").dialog( {
							autoOpen : false,
							modal : true,
							height : 300,
							width : 700,
							show : {
								effect : "blind",
								duration : 800
							},
							hide : {
								effect : "blind",
								duration : 300
							}
						});

						$("#tablasort").tablesorter( {
							sortList : [ [ 1, 1 ] ],
							widgets : [ 'zebra' ],
							headers : {
								6 : {
									sorter : false
								}
							}
						});

						$(".fecha").datepicker();

						$(function($) {
							$.datepicker.regional['es'] = {
								closeText : 'Cerrar',
								prevText : '<Ant',
								nextText : 'Sig>',
								currentText : 'Hoy',
								monthNames : [ 'Enero', 'Febrero', 'Marzo',
										'Abril', 'Mayo', 'Junio', 'Julio',
										'Agosto', 'Septiembre', 'Octubre',
										'Noviembre', 'Diciembre' ],
								monthNamesShort : [ 'Ene', 'Feb', 'Mar', 'Abr',
										'May', 'Jun', 'Jul', 'Ago', 'Sep',
										'Oct', 'Nov', 'Dic' ],
								dayNames : [ 'Domingo', 'Lunes', 'Martes',
										'Miércoles', 'Jueves', 'Viernes',
										'Sábado' ],
								dayNamesShort : [ 'Dom', 'Lun', 'Mar', 'Mié',
										'Juv', 'Vie', 'Sáb' ],
								dayNamesMin : [ 'Do', 'Lu', 'Ma', 'Mi', 'Ju',
										'Vi', 'Sá' ],
								weekHeader : 'Sm',
								dateFormat : 'dd/mm/yy',
								firstDay : 1,
								isRTL : false,
								showMonthAfterYear : false,
								yearSuffix : ''
							};
							$.datepicker
									.setDefaults($.datepicker.regional['es']);
						});
					});

	function doAjaxPost(codigo, tipo) {
		$
				.ajax( {
					type : "POST",
					url : "${pageContext.request.contextPath}/buscarVentas.htm",
					data : "codigo=" + codigo,
					success : function(response) {
						if (response.status == "SUCCESS") {
							var table = "";
							if(tipo == 2){
							table += "<div class='datagrid detalle catalogo'><table ><thead><tr><th>No.</th><th>Cantidad</th><th>Producto</th><th>Precio Sin iva</th><th>Importe</th></tr></thead><tbody>";
							}else{
							table += "<div class='datagrid detalle catalogo'><table ><thead><tr><th>No.</th><th>Cantidad</th><th>Producto</th><th>Precio</th><th>Subtotal</th></tr></thead><tbody>";
							}
							var subtotal = 0;
							var iva = 0;
							$.each(response.result,
									function(key, value) {
										console.log(value.producto.fcnomproducto);
										table += "<tr>";
										table += "<td>" + (key + 1) + "</td>";
											table += "<td>" + value.ficantidad
											+ "</td><td>"
												+ value.producto.fcnomproducto
												+ "</td>";
										if(tipo == 2){
											table += "<td>" + dosdecimales((value.fdprecioventa / <%out.print(Constantes.IVA);%>))
												+ "</td>";
											table += "<td>" + dosdecimales((value.fdsubtotal / <%out.print(Constantes.IVA);%>))
												+ "</td></tr>";
										}else{
										table += "<td>" + dosdecimales(value.fdprecioventa)
												+ "</td>";
									    table += "<td>" + dosdecimales(value.fdsubtotal)
												+ "</td></tr>";
												}
									subtotal = subtotal + (value.fdsubtotal / <%out.print(Constantes.IVA);%>);
									iva = iva + ((value.fdsubtotal / <%out.print(Constantes.IVA);%>) * <%out.print(Constantes.IVA2);%>);
									});
							if(tipo == 2){
								table += "<tr><td>Subtotal: " + dosdecimales(subtotal) + "</td><td>Iva: "+ dosdecimales(iva) +"</td><td>Total: "+ dosdecimales((subtotal + iva)) +"</td></tr>";
							}else{
								table += "<tr><td>Total: "+ dosdecimales((subtotal + iva)) +"</td></tr>";
							}
							table += "</tbody></table></div>";
							$("#pagos").html(table);
							$("#pagos").dialog( {
								buttons : {
									"Regresar" : function() {
										$(this).dialog("close");
									}
								}
							});

							$("#pagos").dialog("open");

						}
					}
				});
	}

	function doAjaxImprimir(codigo) {

		$
				.blockUI( {
					message : '<h2><img width="30px" height="30px" src="${pageContext.request.contextPath}/css/img/cargando.gif"> &nbsp;&nbsp;Imprimiendo ticket... </h2>',
					css : {
						border : 'none',
						backgroundColor : '#000',
						'-webkit-border-radius' : '10px',
						'-moz-border-radius' : '10px',
						opacity : .5,
						color : '#fff'
					}
				});
		setTimeout($.unblockUI, 2000);

		$.ajax( {
			type : "POST",
			url : "${pageContext.request.contextPath}/imprimirticket.htm",
			data : "codigo=" + codigo,
			success : function(response) {
				if (response.status == "SUCCESS") {

				}
			}
		});
	}
	
	function doAjaxImprimirIVA(codigo) {

		$
				.blockUI( {
					message : '<h2><img width="30px" height="30px" src="${pageContext.request.contextPath}/css/img/cargando.gif"> &nbsp;&nbsp;Imprimiendo ticket con IVA... </h2>',
					css : {
						border : 'none',
						backgroundColor : '#000',
						'-webkit-border-radius' : '10px',
						'-moz-border-radius' : '10px',
						opacity : .5,
						color : '#fff'
					}
				});
		setTimeout($.unblockUI, 2000);

		$.ajax( {
			type : "POST",
			url : "${pageContext.request.contextPath}/imprimirticketIVA.htm",
			data : "codigo=" + codigo,
			success : function(response) {
				if (response.status == "SUCCESS") {

				}
			}
		});
	}

	function buscarVentas() {
		var url = "${pageContext.request.contextPath}/listarVentas.htm?fechai="
				+ $("#fechai").val() + "&fechaf=" + $("#fechaf").val()
				+ "&noventa=";
		editar($("#noventa").val(), url);
	}

	function exportarExcel() {
		var url = "${pageContext.request.contextPath}/listarVentas.htm?excel=yes&fechai=${fechai}&fechaf=${fechaf}";
		$(location).attr('href', url);
	}
</script>
</head>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion">Lista de Ventas</div>


			<div style="float: center; font-size: .8em">
				<label> Total </label> <label id="totalVentas" style="color: blue; background-color: white; padding-left: 1em; padding-right: 1em"></label> <label> No. Venta </label> <input name="noventa" id="noventa" type="number" class="cantidad" size="5" value="${noventa}" min="0" /> <label> Fecha
					Inicio </label> <input name="fechai" id="fechai" class="fecha" size="10" value="${fechai}" /> <label> Fecha Fin </label> <input name="fechaf" id="fechaf" class="fecha" size="10" value="${fechaf}" /> <a href="#" onclick=buscarVentas(); style="margin: 0em; padding: 0em;"><img
					src="${pageContext.request.contextPath}/css/img/buscarGrafica.png" width="30px" height="30px" style="margin-top: 2em; margin-bottom: -10px; padding: 0em; margin-right: 0px" /> </a> <a href="#" onclick=exportarExcel(); style="margin: 0em; padding: 0em;"><img
					src="${pageContext.request.contextPath}/css/img/tabla.png" width="30px" height="30px" style="margin-top: 2em; margin-bottom: -10px; padding: 0em; margin-left: 10px" /> </a>

			</div>
			<div class="datagrid catalogo">
				<table <c:if test="${fn:length(model.lstVenta) > 0}"> id="tablasort" class="tablesorter"</c:if>>
					<thead>
						<tr>
							<th>No. Venta</th>
							<th>Fecha Venta</th>
							<th>Usuario</th>
							<th>Cliente</th>
							<th>Observaciones</th>
							<th>Total</th>
							<th>Cancelada</th>
							<th>Imprimir Ticket</th>
							<th>Ticket IVA</th>
							<th>Factura</th>
							<th>Detalles</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach items="${model.lstVenta}" var="venta" varStatus="status">
							<tr>
								<td><c:out value="${venta.fiidventa}" /></td>
								<td><c:out value="${venta.fdfechaventa}" /></td>
								<td><c:out value="${venta.usuario.fcusuario}" /></td>
								<td><c:out value="${venta.cliente.fcnombre}" /> &nbsp; <c:out value="${venta.cliente.fcapepat}" /> &nbsp; <c:out value="${venta.cliente.fcapemat}" /></td>


								<td><c:out value="${venta.fcobservaciones}" /></td>
								<c:if test="${venta.fcactivo == '1' && venta.fcobservaciones != 'Venta a Pagos'}">
									<td class="total"><c:out value="${venta.fdtotal}" /></td>
								</c:if>
								<c:if test="${venta.fcactivo == '0' || venta.fcobservaciones == 'Venta a Pagos'}">
									<td><c:out value="${venta.fdtotal}" /></td>
								</c:if>
								<td><c:if test="${venta.fcactivo == '0'}">
										<img src="${pageContext.request.contextPath}/css/img/noactivo.png" width="20px" height="20px" />
									</c:if></td>

								<td><c:if test="${venta.fcactivo == '1'}">
										<a href="#" onclick="doAjaxImprimir('${venta.fiidventa}')"><img src="${pageContext.request.contextPath}/css/img/ticket3.png" width="20px" height="20px" /> </a>
									</c:if></td>
									
								<td><c:if test="${venta.fcactivo == '1'}">
										<a href="#" onclick="doAjaxImprimirIVA('${venta.fiidventa}')"><img src="${pageContext.request.contextPath}/css/img/ticket3.png" width="20px" height="20px" /> </a>
									</c:if></td>

								<td><c:if test="${venta.fcactivo == '1'}">
										<a href="#" onclick="doAjaxPost('${venta.fiidventa}', 2)"><img src="${pageContext.request.contextPath}/css/img/factura.png" width="20px" height="20px" /> </a>
									</c:if></td>
								<td><a href="#" onclick="doAjaxPost('${venta.fiidventa}', 1)"><img src="${pageContext.request.contextPath}/css/img/detalle.png" width="20px" height="20px" /> </a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</article>
	</section>
</body>
<div id="pagos" style="text-align: center" title="Venta">
	<table id="tablePagos">
	</table>
</div>
</html>