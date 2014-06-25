<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
<!DOCTYPE html">
<html>
<head>
<c:import url='/WEB-INF/jsp/fragmentos/recursos.jsp' />
<script type="text/javascript">
	
	$(document).ready(function() {
	
	solonumeros($(".cantidad"));
	solodecimales($(".decimal"));
	
	if($('#operacion').val() == 2)
		$('#fiidproducto').attr('readonly', true);
	
	$("#catalogosForm").submit(function() {
		    var idproducto = $("#fiidproducto").val();
		    var fcnombre = $("#fcnombre").val();	
		    //var fcdescripcion = $("#fcdescripcion").val();
		    var fcdescripcion = "--";
		    var fdpreciou = $("#fdpreciou").val();
		    var fdpreciom = $("#fdpreciom").val();
		    var fdprecioc = $("#fdprecioc").val();
		    var ficantidad = $("#ficantidad").val();
		    var fiidmedida = $("#fiidmedida").val();
		    var fdprecoprov = $('#fdprecoprov').val();
		    
		    $(".glerror").remove();
		    
		    if($.trim(idproducto) == ""){
					$("#fiidproducto").focus().after("<span class='glerror'>Ingresa el codigo</span>");
				return false;
			}else if($.trim(fcnombre) == "" ){
				$("#fcnombre").focus().after("<span class='glerror'>Ingresa el nombre</span>");
					return false;
			}else if($.trim(fcdescripcion) == ""){
				$("#fcdescripcion").focus().after("<span class='glerror'>Ingresa la descripcion</span>");
					return false;
			}else if((isNaN(fdpreciou) || fdpreciou == "") || fdpreciou <= 0){
				$("#fdpreciou").focus().after("<span class='glerror'>Precio no valido</span>");
					return false;
			}else if((isNaN(fdpreciom) || fdpreciom == "") ||  fdpreciom <= 0){
				$("#fdpreciom").focus().after("<span class='glerror'>Precio no valido</span>");
					return false;
			}else if((isNaN(fdprecioc) || fdprecioc=="") || fdprecioc <= 0){
				$("#fdprecioc").focus().after("<span class='glerror'>Precio no valido</span>");
					return false;
			}else if((isNaN(fdprecoprov) || fdprecoprov=="") || fdprecoprov <= 0){
				$("#fdprecoprov").focus().after("<span class='glerror'>Precio no valido</span>");
				return false;
			}
			else if(isNaN(ficantidad) || ficantidad == ""){
				$("#ficantidad").focus().after("<span class='glerror'>Cantidad no valida</span>");
						return false;
			}else if(!fiidmedida > 0){
			$("#fiidmedida").focus().after("<span class='glerror'>Agrege medidas para los productos</span>");
						return false;
			}
		 });
    	
    });
	
	</script>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion">Producto</div>
			<div class="agregarAccion">


				<c:choose>
					<c:when test="${catalogosForm.salida != null && catalogosForm.salida != ''}">
						<a href="<c:url value="${catalogosForm.salida}"/>">
					</c:when>
					<c:otherwise>
						<a href="<c:url value="listarProductos.htm"/>">
					</c:otherwise>
				</c:choose>

				<img src="${pageContext.request.contextPath}/css/img/regresar.png" width="30px" height="30px" /></a> <a class="btnguardar" href="#" onclick="$('#catalogosForm').submit();"><img src="${pageContext.request.contextPath}/css/img/guardar.png" width="30px" height="30px" /></a>
			</div>
			<form:form method="post" commandName="catalogosForm" id="catalogosForm">
				<form:hidden path="operacion" id="operacion" />
				<div class="datagrid agregarEditar">
					<table class="agregarEditar">
						<tbody>
							<tr>
								<td><label>Codigo</label></td>
								<td><form:input path="producto.fiidproducto" id="fiidproducto" placeholder="Codigo del producto" required="required" autofocus="autofocus" onchange="doAjaxPost()" /> <form:errors path="producto.fiidproducto" cssClass="glerror" /></td>

							</tr>
							<tr class="alt">
								<td><label>Nombre</label></td>
								<td><form:input path="producto.fcnomproducto" id="fcnombre" placeholder="Nombre del producto" required="required" /> <form:errors path="producto.fcnomproducto" cssClass="glerror" /></td>
							</tr>
							<!-- <tr><td> <label>Descripcion</label> </td>
				<td><form:textarea cols="25" rows="3" path="producto.fcdescproducto" id="fcdescripcion" placeholder="Descripcion del producto" required="required"/></td>
			</tr >  -->
							<form:hidden path="producto.fcdescproducto" value="--" />
							<tr>
								<td><label>Precio Unitario</label></td>
								<td><form:input path="producto.fdpreciounitario" id="fdpreciou" required="required" cssClass="inputnumero decimal" size="5" type="number" step="0.10" min="0.10" /></td>
							</tr>
							<tr class="alt">
								<td><label>Precio Mayoreo</label></td>
								<td><form:input path="producto.fdpreciomayoreo" id="fdpreciom" required="required" cssClass="inputnumero decimal" size="5" type="number" step="0.10" min="0.10" /></td>
							</tr>
							<tr>
								<td><label>Precio Proveedor</label></td>
								<td><form:input path="producto.fdprecioproveedor" id="fdprecoprov" required="required" cssClass="inputnumero decimal" size="5" type="number" step="0.10" min="0.10" /></td>
							</tr>
							<tr class="alt">
								<td><label>Precio Compra</label></td>
								<td><form:input path="producto.fdpreciocompra" id="fdprecioc" required="required" cssClass="inputnumero decimal" size="5" type="number" step="0.10" min="0.10" /></td>
							</tr>
							<tr>
								<td><label>Medida</label></td>
								<td><form:select path="producto.medida.fiidmedida" id="fiidmedida">
										<form:options items="${lstMedida}" itemLabel="fcdescmedida" itemValue="fiidmedida" />
									</form:select></td>
							</tr>
							<tr class="alt">
								<td><label>Cantidad</label></td>
								<td><form:input path="producto.ficantidad" id="ficantidad" size="5" required="required" cssClass="inputnumero decimal" type="number" min="0" /> <form:errors path="producto.medida.fiidmedida" cssClass="glerror" /></td>
							</tr>
							<tr>
								<td><label>Cantidad Mayoreo</label></td>
								<td><form:input path="producto.ficantidadmayore" id="ficantidadmayore" size="5" required="required" cssClass="inputnumero cantidad" type="number" min="0" />
							</tr>
						</tbody>
					</table>
				</div>
			</form:form>
		</article>
	</section>
</body>
</html>