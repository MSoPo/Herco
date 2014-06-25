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
						
						$("#cobrar").click(function(e) {
							e.preventDefault();

							$("#tiquet").dialog({
								buttons : {
									"Si" : function() {
										$('#accion').val(5);
										cargarSubmit($('#compraForm'), '${pageContext.request.contextPath}/css/img/cargando.gif');
										//$('#compraForm').submit();
									},
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
										cargarSubmit($('#compraForm'), '${pageContext.request.contextPath}/css/img/cargando.gif');
										//$('#compraForm').submit();
									},
									"No" : function() {
										$(this).dialog("close");
									}
								}
							});

							$("#dialog").dialog("open");
						});

						$("#nombre")
								.autocomplete(
										{
											source : "${pageContext.request.contextPath}/autocompletarProdActivos.htm",
											select : function(a, b) {
												$(this).val(b.item.value);
												doAjaxPost();
											}
										});

						$("#nombre").keypress(function(e) {
							if (e.which == 13) {
								doAjaxPost();
							}
						});

						$("#codigo").keypress(function(e) {
							if (e.which == 13) {
								ejecutarAccion(1, $('#compraForm'));
							}
						});

						$(".clsreadonly").attr('readonly', 'readonly');

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
					$('#descripcion').val(response.result.fcdescproducto);
					$('#precio').val(response.result.fdpreciocompra);
					$('#cantidad').val(response.result.ficantidad);
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
					$('#descripcion').val(response.result.fcdescproducto);
					$('#precio').val(response.result.fdpreciocompra);
					$('#cantidad').val(response.result.ficantidad);
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
</script>
</head>

<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<form:form method="post" commandName="compraForm" id="compraForm">
			<form:input path="accion" type="hidden" id="accion" />
			<article>
				<div class="accion">Realizar Compra</div>
				<label>Codigo</label>
				<form:input path="producto.fiidproducto" id="codigo" required="required" autofocus="autofocus" />

				<label>Producto</label>
				<form:input path="producto.fcnomproducto" id="nombre" />

				<a href="#" onclick="editar('compra.htm', 'agregarProducto.htm?salida=')"><img src="${pageContext.request.contextPath}/css/img/agregar.png" width="18px" height="18px" style="margin: 0px" /> </a>


				<form:errors path="producto.fiidproducto" block="ul" item="li" cssClass="error" id="error" />

				<br> <input type="button" value="Buscar" onclick="buscaAmbos()" class="buttonForm" /> <input type="button" value="Agregar" onclick="ejecutarAccion(1, $('#compraForm'))" class="buttonForm" />
			</article>

			<article hidden="hidden" id="datosprod">
				<div class="subaccion">
					Datos del producto <a href="#" onclick="editar($('#codigo').val(), 'agregarProducto.htm?salida=compra.htm&fiidproducto=')"><img src="${pageContext.request.contextPath}/css/img/editar.png" width="18px" height="18px" style="margin: 0px" /> </a>
				</div>

				<label>Descripcion</label>
				<form:textarea path="producto.fcdescproducto" id="descripcion" cols="25" rows="3" cssClass="clsreadonly" />

				<label>Precio</label>
				<form:input path="producto.fdpreciocompra" class="inputnumero clsreadonly" id="precio" size="5" />
				<label>Cantidad</label>
				<form:input path="producto.ficantidad" class="inputnumero clsreadonly" id="cantidad" size="5" />
			</article>

			<c:if test="${not empty  detalleCompra}">
				<article id="listaprod">
					<div class="subaccion">Productos de la Compra</div>
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
								<c:forEach var="lstproducto" items="${detalleCompra}" varStatus="status">
									<tr <c:if test="${status.index%2 != 0}"> class="alt" </c:if>>
										<td>${lstproducto.producto.fiidproducto}</td>
										<td>${lstproducto.producto.fcnomproducto}</td>
										<td><input value="${lstproducto.ficantidad}" id="cantidadCompra" type="number" min="1" <c:if test="${lstproducto.producto.medida.fcvaloresenteros == '1'}" >
											class="cantidad" step="1"
											</c:if>
											<c:if test="${lstproducto.producto.medida.fcvaloresenteros == '0'}" >
											class="decimal" step=".25"
											</c:if> onchange="actualizarCantidad('${lstproducto.producto.fiidproducto}', this, $('#compraForm'));"></td>
										<td>${lstproducto.producto.fdpreciocompra}</td>
										<td class="clsubtotal">${lstproducto.fdsubtotal}</td>
										<td><a href="#" onclick="borrarProducto('${lstproducto.producto.fiidproducto}', $('#compraForm'));"><img src="${pageContext.request.contextPath}/css/img/desactivar.png" width="13px" height="13px" /> </a></td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td><form:select path="compra.catproveedor.fiidproveedor">
											<form:options items="${lstProveedor}" itemLabel="fcnomproveedor" itemValue="fiidproveedor" />
										</form:select> <a href="#" onclick="editar('compra.htm', 'agregarProveedor.htm?salida=')"><img src="${pageContext.request.contextPath}/css/img/agregar.png" width="18px" height="18px" style="margin: 0px" /> </a></td>
								</tr>

								<tr>
									<td>
									<td></td>
									<td>Total</td>
									<td id="tdTotal">$ <form:input path="compra.fdtotal" id="totalVenta" class="clsreadonly" /></td>
									<td><input type="button" value="Cancelar" id="cancelar" class="buttonForm" /></td>
									<td><input type="button" value="Comprar" id="cobrar" class="buttonForm" /></td>
								</tr>
							</tfoot>
						</table>
					</div>
				</article>
			</c:if>
		</form:form>
	</section>
	<div id="dialog" title="Cancelar">¿Seguro que deseas cancelar la compra?</div>
	<div id="tiquet" title="Cobrar">¿Finalizar la compra?</div>
</body>
</html>