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
						solodecimales($(".decimal"));
						
						$('input[name=imprimir]').on('change', function(){ 
						
						if($(this).val() == 1){
							$('#notaMax').show();
						}else{
							$('#notaMax').hide();
						} 
						
						});

						$("#ventaForm").submit(function() {
											var cantidadPago = $("#cantidadPago").val();
											$(".glerror").remove();

											if ($.trim(cantidadPago) == "" || cantidadPago < 0 || cantidadPago > <c:out value="${ventaForm.venta.fdtotal}"/>) {
												$("#cantidadPago")
														.focus()
														.after(
																"<span class='glerror' style='font-size: .5em'>No 2 valido</span>");
												$.unblockUI();
												return false;
											}
										});

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

						$("#tiquet").dialog({
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

						$("#dialogPagos").dialog({
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

						$("#pagos")
								.click(
										function(e) {

											var fiidcliente = $("#fiidcliente")
													.val();

											if (fiidcliente == 1) {
												$("#fiidcliente")
														.focus()
														.after(
																"<span class='glerror'>Seleccione el cliente para la venta</span>");
												return false;
											}

											e.preventDefault();

											$("#dialogPagos")
													.dialog(
															{
																buttons : {
																	"Realizar" : function() {
																		$('#accion').val(7);
																		$('#cantPagoVenta').val($("#cantidadPago").val());
																		$('#observacionesPago').val($("#observacionesP").val());
																		cargarSubmit($('#ventaForm'), '${pageContext.request.contextPath}/css/img/cargando.gif');
																		//$('#ventaForm').submit();
																	},
																	"Cancelar" : function() {
																		$(this).dialog("close");
																	}
																}
															});

											$("#dialogPagos").dialog("open");
										});

						$("#cobrar").click(function(e) {
							e.preventDefault();

							$("#tiquet").dialog({
								buttons : {
									"Si" : function() {
										$('#impresion').val($('input[name=imprimir]:checked').val());
										$('#accion').val(6);
										$('#recibido').val($('#reci').val());
										$('#nombreVenta').val($('#cobrarCliente').val());
										$('#domicilioVenta').val($('#cobrarDomicilio').val());
										if ($.trim($('#camb').val()) != "" &&  $('#camb').val() >= 0) {
											$('#cambio').val($('#camb').val());
											}else{
												$("#reci")
														.focus()
														.after(
																"<span class='glerror' style='font-size: .5em'>No valido</span>");
												return false;
											}
									    cargarSubmit($('#ventaForm'), '${pageContext.request.contextPath}/css/img/cargando.gif');
										//$('#ventaForm').submit();
									}/*,
									"No" : function() {
										$('#accion').val(5);
										$('#ventaForm').submit();
									}*/,
									"Regresar" : function() {
										$(this).dialog("close");
									}
								}
							});

							$("#tiquet").dialog("open");
						});

						$("#cancelar").click(function(e) {
							e.preventDefault();

							$("#dialog").dialog({
								buttons : {
									"Si" : function() {
										$('#accion').val(4);
										cargarSubmit($('#ventaForm'), '${pageContext.request.contextPath}/css/img/cargando.gif');
										//$('#ventaForm').submit();
									},
									"No" : function() {
										$(this).dialog("close");
									}
								}
							});

							$("#dialog").dialog("open");
						});
						
						var dobleenter = 0;

						$("#nombre")
								.autocomplete(
										{
											source : "${pageContext.request.contextPath}/autocompletarProdActivos.htm",
											select : function(a, b) {
												$(this).val(b.item.value);
												dobleenter = 1;
												doAjaxPost();
											}
										});

						$("#nombre").keypress(function(e) {
							if (e.which == 13) {
								if(dobleenter == 1){
									ejecutarAccion(1, $('#ventaForm'));
								}
								dobleenter = 1;
								doAjaxPost();
							}else{
								dobleenter = 0;
							}
						});

						$("#codigo").keypress(function(e) {
							if (e.which == 13) {
									ejecutarAccion(1, $('#ventaForm'));
									}
						});

						$(".clsreadonly").attr('readonly', 'readonly');
						
						<c:if test="${ventaForm.venta.fiidventaanterior > 0}">
							$("#fiidcliente").attr('disabled','disabled');
						</c:if>
										

					});

	function doAjaxPostCodigo() {

		var codigo = $('#codigo').val();
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/buscarcodigo.htm",
			data : "codigo=" + codigo,
			success : function(response) {
				if (response.status == "SUCCESS") {
					$('#nombre').val(response.result.fcnomproducto);
					$('#preciop').val(dosdecimales(response.result.fdprecioproveedor));
					$('#precio').val(dosdecimales(response.result.fdpreciounitario));
					$('#cantidad').val(response.result.ficantidad);
					$('#preciom').val(dosdecimales(response.result.fdpreciomayoreo));
					if ($('#descripcion').val() != "") {
						$('#datosprod').hide("slow");
						$('#datosprod').show("slow");
						$('#error').hide("slow");
					} else {
						$('#datosprod').hide("slow");
						$('#error').hide("slow");
					}
				}
			}
		});
	}

	function doAjaxPost() {
		var codigo = $('#nombre').val();
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/buscarNombre.htm",
			data : "codigo=" + codigo,
			success : function(response) {
				if (response.status == "SUCCESS") {
					$('#codigo').val(response.result.fiidproducto);
					$('#preciop').val(dosdecimales(response.result.fdprecioproveedor));
					$('#precio').val(dosdecimales(response.result.fdpreciounitario));
					$('#cantidad').val(response.result.ficantidad);
					$('#preciom').val(dosdecimales(response.result.fdpreciomayoreo));
					if ($('#descripcion').val() != "") {
						$('#datosprod').hide("slow");
						$('#datosprod').show("slow");
						$('#error').hide("slow");
					} else {
						$('#datosprod').hide("slow");
						$('#error').hide("slow");
					}
				}
			}
		});
	}

	function buscaAmbos() {
		if ($('#nombre').val() != "")
			doAjaxPost();
		else {
			doAjaxPostCodigo();
		}
	}

	var intervalId = 0;
	
	$('#camb').attr('readonly', true);
	function validarCambio(){
			$(".glerror").remove();
			
			var num = 0;
			num = $("#reci").val() - $("#totalVenta").val();
			<c:if test="${ventaForm.venta.fiidventaanterior > 0}">
			num = $("#reci").val() - $("#diferenciaventas").val();
			</c:if>
			
			
			$("#camb").val(dosdecimales(num));
	}
	
</script>
</head>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<form:form method="post" commandName="ventaForm" id="ventaForm">
			<form:input path="pago.fdcantidad" type="hidden" value="10" id="cantPagoVenta" />
			<form:input path="pago.fcobservacion" type="hidden" id="observacionesPago" />
			<form:input path="accion" type="hidden" id="accion" />
			<form:input path="nombre" type="hidden" id="nombreVenta" />
			<form:input path="domicilio" type="hidden" id="domicilioVenta" />
			<form:hidden path="venta.fiidventaanterior" />
			<form:hidden path="recibido" id="recibido" />
			<form:hidden path="cambio" id="cambio" />
			<form:hidden path="impresion" id="impresion" />

			<article>
				<div class="accion">Realizar Venta</div>

				<label> Codigo </label>
				<form:input path="producto.fiidproducto" id="codigo" required="required" autofocus="autofocus" />

				<label> Producto </label>
				<form:input path="producto.fcnomproducto" id="nombre" />

				<form:errors path="producto.fiidproducto" block="ul" item="li" cssClass="error" id="error" />

				<br> <input type="button" value="Buscar" onclick="buscaAmbos()" class="buttonForm" /> <input type="button" value="Agregar" onclick="ejecutarAccion(1, $('#ventaForm'))" class="buttonForm" />
			</article>

			<article <%--<c:if test="${empty  detalleVenta}"> hidden="hidden"</c:if> --%> hidden="hidden" id="datosprod">
				<div class="subaccion">Datos del producto</div>

				<label> Precio Unitario </label>
				<form:input path="producto.fdpreciounitario" class="inputnumero clsreadonly" id="precio" size="5" />

				<label> Precio Mayoreo </label>
				<form:input path="producto.fdpreciomayoreo" class="inputnumero clsreadonly" id="preciom" size="5" />

				<label> Precio Proveedor </label>
				<form:input path="producto.fdprecioproveedor" id="preciop" class="inputnumero clsreadonly" size="5" />

				<label> Cantidad </label>
				<form:input path="producto.ficantidad" class="inputnumero clsreadonly" id="cantidad" size="5" />
			</article>
			<c:if test="${not empty  detalleVenta}">
				<article id="listaprod">
					<div class="subaccion">Productos de la Venta</div>
					<div class="datagrid">
						<table>
							<thead>
								<tr>
									<th>Codigo</th>
									<th>Producto</th>
									<th>Cantidad</th>
									<th>Precio</th>
									<th>SubTotal</th>
									<th>Eliminar</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="lstproducto" items="${detalleVenta}" varStatus="status">
									<tr <c:if test="${status.index%2 != 0}"> class="alt" </c:if>>
										<td>${lstproducto.producto.fiidproducto}</td>
										<td>${lstproducto.producto.fcnomproducto}</td>
										<td><input value="${lstproducto.ficantidad}" id="cantidadVenta" type="number" min="1" max="${lstproducto.producto.ficantidad}" <c:if test="${lstproducto.producto.medida.fcvaloresenteros == '1'}" >
											class="cantidad" step="1"
											</c:if>
											<c:if test="${lstproducto.producto.medida.fcvaloresenteros == '0'}" >
											class="decimal" step=".25"
											</c:if> onchange="actualizarCantidad('${lstproducto.producto.fiidproducto}', this, $('#ventaForm'));"></td>
										<td>${lstproducto.fdprecioventa}</td>
										<td class="clsubtotal">${lstproducto.fdsubtotal}</td>
										<td><a href="#" onclick="borrarProducto('${lstproducto.producto.fiidproducto}', $('#ventaForm'));"><img src="${pageContext.request.contextPath}/css/img/desactivar.png" width="13px" height="13px" /> </a></td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td><form:select path="venta.cliente.fiidcliente" onchange="ejecutarAccion(0, $('#ventaForm'))" id="fiidcliente">
											<c:forEach var="cliente" items="${lstCliente}">
												<form:option value="${cliente.fiidcliente}">${cliente.fcnombre} ${cliente.fcapepat}</form:option>
											</c:forEach>
										</form:select> <%-- <a href="#" onclick="editar('venta.htm', 'agregarCliente.htm?salida=')"><img
												src="${pageContext.request.contextPath}/css/img/agregar.png"
												width="18px" height="18px" style="margin: 0px" /></a> --%></td>
									<c:if test="${ventaForm.venta.cliente.fddescuento != 0}">
										<td>Descuento:</td>
										<td><form:input path="venta.cliente.fddescuento" size="5" cssClass="inputnumero clsreadonly" /></td>
									</c:if>
								</tr>

								<tr>
									<td><c:if test="${ventaForm.venta.fiidventaanterior == 0}">
											<input type="button" value="Pagos" id="pagos" class="buttonForm" />
										</c:if></td>
									<td></td>
									<td>Total</td>
									<td id="tdTotal">$ <form:input path="venta.fdtotal" id="totalVenta" class="clsreadonly" />
									</td>
									<td><c:if test="${ventaForm.venta.fiidventaanterior <= 0}">
											<input type="button" value="Cancelar" id="cancelar" class="buttonForm" />
										</c:if> <c:if test="${ventaForm.venta.fiidventaanterior > 0}">
											<input type="button" value="Nueva Venta" id="cancelar" class="buttonForm" />
										</c:if></td>
									<td><input type="button" value="Cobrar" id="cobrar" class="buttonForm" /></td>
								</tr>
							</tfoot>
						</table>
					</div>
				</article>
			</c:if>
			<!-- <input type="submit" class="buttonSubmit" onclick="ejecutarAccion(1)"> -->
		</form:form>
	</section>
	<div id="tiquet" title="Cobrar">
		<!-- ¿Deseas imprimir tiquet?  -->

		<c:if test="${ventaForm.venta.fiidventaanterior > 0}">
			<label> Diferencia entre ventas </label>
			<input value="${ventaForm.diferenciaCambioVenta}" type="number" class="clsreadonly" id="diferenciaventas" />
			<br />
		</c:if>
		<label> Cantidad Recibida: </label> <input type="number" id="reci" onkeyup="validarCambio()" onchange="validarCambio()" class="decimal" /> <br /> <label> Cambio: </label> <input type="number" id="camb" class="clsreadonly" /> <br /> <label> Total: </label> <input type="number"
			class="clsreadonly" value="${ventaForm.venta.fdtotal}" />
		<c:if test="${ventaForm.venta.cliente.fiidcliente == 1}">
			<br />
			<label> Cliente: </label>
			<input id="cobrarCliente" />
			<br />
			<label> Domicilio: </label>
			<input id="cobrarDomicilio" />

		</c:if>
		<br /> <input type="radio" name="imprimir" value="0" checked="checked">Ticket<br /> <input type="radio" name="imprimir" value="1">Nota<br />
		<c:if test="${fn:length(detalleVenta) > 16}">
			<br />
			<span id="notaMax" style="display: none" class="error">Sobrepasa la cantidad maxima (16) de productos que caben en una nota. <br />Se recomienda eliminar algunos y hacer otra venta
			</span>
		</c:if>

	</div>
	<div id="dialog" title="Cancelar">¿Seguro que deseas cancelar la venta?</div>
	<div id="dialogPagos" title="Pagos" style="text-align: center">
		¿Cantidad del pago? <input type="number" id="cantidadPago" min="0" step="0.1" value="0" max="${ventaForm.venta.fdtotal}" class="decimal">
		<textarea placeholder="Observaciones" id="observacionesP"></textarea>
	</div>
</body>
</html>