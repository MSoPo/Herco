<%@page import="ventas.util.MenuDianmico"%>
<%@page import="java.util.List"%>
<%@page import="control.springmvc.controlador.LoginController"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<header>
	<div class="empresa">
		<!-- Sin Logo -->
		<!-- <div class="giro">
			<fmt:message key="titulo.giro" />
		</div>
		<fmt:message key="titulo.empresa" /> -->
		<img alt="logo" src="${pageContext.request.contextPath}/css/img/logoHerco.png">
	</div>
	<div style="height: 46%;"></div>
	<nav id="cssmenu">
		<div style="width: 5%; height: 100%; float: left;">
			<a href="${pageContext.request.contextPath}/cerrarSesion.htm" style="float: left; background-color: transparent; margin: 0px; padding: 0px; margin-top: 10px;"> <img src="${pageContext.request.contextPath}/css/img/apagar.png" width="20px" height="20px" />
			</a>
		</div>
		<%!int primero = 0;

	String crearMenu(List<MenuDianmico> lst, String contexto) {
		String html = "<ul>";
		if (primero == 0) {
			html = "<ul class='nav'>";
			primero = 1;
		}
		for (MenuDianmico md : lst) {

			html += "\n<li ";

			if (md.getLstMenu().size() > 0)
				html += "class='has-sub '";

			html += "><a href='";
			if(!md.getMenu().getFcurl().equals("#"))
			 html += contexto; 
			html +=md.getMenu().getFcurl() +"'><span>" + md.getMenu().getFcnombremenu()
					+ "</span></a>" + crearMenu(md.getLstMenu(), contexto) + "</li>\n";
		}

		return html += "</ul>";
	}%>


		<%
			if ( request.getSession().getAttribute("menu") != null) 
				out.print(crearMenu((List<MenuDianmico>) request.getSession().getAttribute("menu"), request.getContextPath()));
		%>

	</nav>
</header>