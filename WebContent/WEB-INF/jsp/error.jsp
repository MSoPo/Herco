<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/fragmentos/recursos.jsp' />
</head>
<body>
	<img src="${pageContext.request.contextPath}/css/img/error.png" style="margin: 10% 2% 2% 10%; float: left;" />
	<section style="height: 30%; float: left; width: 60%; margin-top: 10%; margin-left: 0%; margin-botom: auto; padding: 1em;">
		Ocurrio un error favor de reportarlos al Administrador y enviar el siguiente codigo:
		<div style="font-size: .8em">
			<br />Clase:
			<div style="color: blue">
				<c:out value="${model.clase}"></c:out>
			</div>
			<br />Error:
			<div style="color: blue">
				<c:out value="${model.error}"></c:out>
			</div>
			<br />Causa:
			<div style="color: blue">
				<c:out value="${model.causa}"></c:out>
			</div>
		</div>
	</section>
</body>
</html>
