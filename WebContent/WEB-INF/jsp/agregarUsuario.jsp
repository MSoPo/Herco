<%@ include file="/WEB-INF/jsp/fragmentos/directiva.jsp"%>
<!DOCTYPE html">
<html>
<head>
<c:import url='/WEB-INF/jsp/fragmentos/recursos.jsp' />
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						if ($('#operacion').val() == 2) {
							$('#fcusuario').attr('readonly', true);
							$('#repPass').val($("#fcpassword").val());
						}
						$("#catalogosForm")
								.submit(
										function() {
											var fcusuario = $("#fcusuario")
													.val();
											var fcpassword = $("#fcpassword")
													.val();
											var repPass = $("#repPass").val();
											var fcpregunta = $("#fcpregunta")
													.val();
											var fcrespuesta = $("#fcrespuesta")
													.val();
											var fcnombre = $("#fcnombre").val();
											var fcapepat = $("#fcapepat").val();
											var rol = false;
											$(".chk").each(function() {
												if ($(this).val() == 2)
													rol = true;
											});

											$(".glerror").remove();
											
											if(fcusuario == "Administrador"){
												$("#fcusuario")
														.focus()
														.after(
																"<span class='glerror'>Este Usuario no se  <br>puede modificar</span>");
												return false;
											}

											if ($.trim(fcusuario) == "") {
												$("#fcusuario")
														.focus()
														.after(
																"<span class='glerror'>Ingresa el usuario</span>");
												return false;
											} else if ($.trim(fcpassword) == ""
													|| $.trim(fcpassword).length < 6) {
												$("#fcpassword")
														.focus()
														.after(
																"<span class='glerror'>El password debe contener minimo 6 caracteres</span>");
												return false;
											} else if (repPass != fcpassword) {
												$("#repPass")
														.focus()
														.after(
																"<span class='glerror'>No coinciden los password</span>");
												return false;
											} else if ($.trim(fcpregunta) == "") {
												$("#fcpregunta")
														.focus()
														.after(
																"<span class='glerror'>Ingresa la pregunta</span>");
												return false;
											} else if ($.trim(fcrespuesta) == "") {
												$("#fcrespuesta")
														.focus()
														.after(
																"<span class='glerror'>Ingresa la respuesta</span>");
												return false;
											} else if ($.trim(fcnombre) == "") {
												$("#fcnombre")
														.focus()
														.after(
																"<span class='glerror'>Ingresa el nombre</span>");
												return false;
											} else if ($.trim(fcapepat) == "") {
												$("#fcapepat")
														.focus()
														.after(
																"<span class='glerror'>Ingresa el apellido paterno</span>");
												return false;
											} else if (!rol) {
												$("#lbroles")
														.focus()
														.after(
																"<span class='glerror'>Selecciona por lo menos 1 rol</span>");
												return false;
											}
										});

						$("#fcusuario").keypress(function(e) {
							if (e.which == 13) {
								doAjaxPost();
							}
						});

						$(".chk").change(function(e) {
							if ($(this).val() == 1)
								$(this).val(2);
							else if ($(this).val() == 2)
								$(this).val(1);
						});

					});

	function doAjaxPost() {
		var fcusuario = $('#fcusuario').val();
		$
				.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/buscarUsuario.htm",
					data : "fcusuario=" + fcusuario,
					success : function(response) {
						if (response.status == "SUCCESS") {
							$(".glerror").remove();
							if (response.result)
								$("#fcusuario")
										.focus()
										.after(
												"<span class='glerror'> Este usuario ya existe</span>");
						}
					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
	}

	var intervalId = 0;
</script>
<body>
	<c:import url='/WEB-INF/jsp/fragmentos/header.jsp' />
	<section id="cuerpo">
		<article>
			<div class="accion">Usuario</div>
			<div class="agregarAccion">
				<a href="<c:url value="listarUsuario.htm"/>"><img src="${pageContext.request.contextPath}/css/img/regresar.png" width="30px" height="30px" /> </a> <a class="btnguardar" href="#" onclick="$('#catalogosForm').submit();"><img src="${pageContext.request.contextPath}/css/img/guardar.png"
					width="30px" height="30px" /> </a>
			</div>

			<form:form method="post" commandName="catalogosForm" id="catalogosForm">
				<form:hidden path="operacion" id="operacion" />

				<div class="datagrid agregarEditar">
					<table class="agregarEditar">
						<tbody>
							<tr>
								<td><label>Usuario</label></td>
								<td><form:input path="usuario.fcusuario" id="fcusuario" placeholder="Ingresa el usuario" required="required" autofocus="autofocus" onchange="doAjaxPost()" /></td>
							</tr>
							<tr class="alt">
								<td><label>Password</label></td>
								<td><form:input path="usuario.fcpassword" id="fcpassword" placeholder="Ingresa el password" type="password" required="required" /></td>
							</tr>
							<tr>
								<td><label>Confirma Password</label></td>
								<td><input id="repPass" placeholder="Repite el password" type="password" required="required" /></td>
							</tr>
							<tr class="alt">
								<td><label>Pregunta para recuperar</label></td>
								<td><form:input path="usuario.fcpreguntasec" id="fcpregunta" placeholder="Ingresa la pregunta" required="required" /></td>
							</tr>
							<tr>
								<td><label>Respuesta</label></td>
								<td><form:input path="usuario.fcrespuestasec" id="fcrespuesta" placeholder="Ingresa la Respuesta" required="required" /></td>
							</tr>
							<tr class="alt">
								<td><label>Nombre</label></td>
								<td><form:input path="usuario.fcnombre" id="fcnombre" placeholder="Ingresa el nombre" required="required" /></td>
							</tr>
							<tr>
								<td><label>Apellido Paterno</label></td>
								<td><form:input path="usuario.fcapepat" id="fcapepat" placeholder="Ingresa el apellido paterno" required="required" /></td>
							</tr>
							<tr class="alt">
								<td><label>Apellido Materno</label></td>
								<td><form:input path="usuario.fcapemat" id="fcapemat" placeholder="Ingresa el apellido materno" /></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: center;"><label id="lbroles">Roles</label></td>
							</tr>
							<c:forEach items="${catalogosForm.roles}" var="roles" varStatus="status">
								<c:if test="${status.index % 2 == 0}">
									<tr>
								</c:if>
								<td><input class="chk" type="checkbox" name="roles[${status.index}].fcactivo" value="${roles.fcactivo}" <c:if test="${roles.fcactivo == '2'}"> checked </c:if> /><label>${roles.fcdescrol}</label> <input name="roles[${status.index}].fiidrol" value="${roles.fiidrol}" type="hidden" /></td>
								<c:if test="${status.index % 2 != 0}">
									</tr>
								</c:if>
							</c:forEach>
						</tbody>

					</table>
				</div>
			</form:form>
		</article>
	</section>
</body>
</html>