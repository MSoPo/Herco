<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<c:import url='/WEB-INF/jsp/fragmentos/recursos.jsp' />
<script type="text/javascript">
	$(document).ready(function() {
		$.get('C:/Herco/caja.xml', function(d){
			alert('dd');
		});
		
		$(".submit").keypress(function(e) {
    		if(e.which == 13) {
    			cargarSubmit($('#usuarioXRol'), '${pageContext.request.contextPath}/css/img/cargando.gif');
    			//$("#usuarioXRol").submit();
    		}
		});
		
		$("#usuarioXRol").submit(function() {
		    var x = $("#slcrol").val();
		      if (x==-1) {
		        $("#msjerror").text("<fmt:message key='error.fiidrol.seleccionar'/>");
		        $.unblockUI();
		        return false;
		      } else 
		          return true;	
		    });
		
		
		<c:if test="${not empty  roles}">
		$('#usuarioXRol input').attr('readonly', 'readonly');
		</c:if>
	});
</script>

</head>
<body>
	<section class="login">
		<article class="empresa">

			<!-- 
				<h1>
				<fmt:message key="titulo.giro" />
				"
				<fmt:message key="titulo.empresa" />
				" 
				</h1>
				-->
			<img alt="logo" src="${pageContext.request.contextPath}/css/img/logoHerco.png">

		</article>
		<article id="form">
			<form:form method="post" commandName="usuarioXRol">
				<table>
					<tr>
						<td><form:input path="usuario.fcusuario" placeholder="Ingresa tu usuario" required="required" autofocus="autofocus" id="fcusuario" cssClass="inputLogin submit" /></td>
					</tr>
					<tr>
						<td><form:errors path="usuario.fcusuario" block="ul" item="li" cssClass="error" /></td>
					</tr>
					<tr>
						<td><form:input path="usuario.fcpassword" placeholder="Ingresa tu password" type="password" required="required" cssClass="inputLogin submit" /></td>
					</tr>
					<tr>
						<td><form:errors path="usuario.fcpassword" block="ul" item="li" cssClass="error" />
							<div class="error" id="msjerror"></div></td>
					</tr>
					<tr>
						<td><c:if test="${not empty  roles}">

								<form:select path="rol.fiidrol" cssClass="submit inputLogin" id="slcrol" autofocus="autofocus">
									<form:option value="-1">
										<fmt:message key='error.fiidrol.seleccionar' />
									</form:option>
									<form:options items="${roles}" itemLabel="fcdescrol" itemValue="fiidrol" />
								</form:select>
							</c:if></td>
					</tr>
					<tr>

						<td><br> <a href="#" onclick="cargarSubmit($('#usuarioXRol'), '${pageContext.request.contextPath}/css/img/cargando.gif');" class="entrar">Entrar</a></td>
					</tr>
				</table>
			</form:form>
		</article>
	</section>
</body>
</html>