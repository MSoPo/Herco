<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/fragmentos/recursos.jsp' />
<script type="text/javascript">
$(document).ready(function() {
	solonumeros($("#codigo"));
});

function generarCodigo(){
	$(".glerror").remove();
	var codigo = $("#codigo").val();

	if ($.trim(codigo) == "") {
	$("#codigo")
	.focus()
	.after("<span class='glerror' style='font-size: .5em'>Codigo no valido</span>");
	return false;
	}
	editar(codigo, "${pageContext.request.contextPath}/generarCodigoDeBarra.htm?codigo=");
	
}

function generarCodigoAleatoria(){
var text = "";
var possible = "0123456789";

    for( var i=0; i < 10; i++ )
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    editar(text, "${pageContext.request.contextPath}/generarCodigoDeBarra.htm?codigo=");

}

function limpiarError(){
$('.glerror').remove();
}


</script>
</head>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion">Crear Codigo De Barras</div>
			<table>
				<tr>
					<td><label>Ingresa el codigo</label></td>
					<td><input type="text" id="codigo" onkeypress="limpiarError()"></td>
					<td><input type="button" value="Generar" class="buttonForm" onclick="generarCodigo()"></td>
				</tr>
				<tr>
					<td colspan="3"><input type="button" value="Generar Automatico" class="buttonForm" onclick="generarCodigoAleatoria()"></td>
				</tr>
			</table>
		</article>
	</section>
</body>
</html>
