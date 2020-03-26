<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!-- pour le prefix on peut mettre ce que l'on veut -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GESTION DU CINEMA</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<!-- http://www.favicon.pro/fr/ -->
<link rel="shortcut icon" type="image/x-icon"
	href="./images/favicon.ico">
<script type="text/javascript" src="js/clock.js"></script>
</head>
<body style="height:100%; position: relative;">

	<!-- AFFICHAGE DE LA CATEGORIE DU MENU CONCERNE -->

	<div class="container-fluid" id="haut">
		<%@include file="./WEB-INF/Header/hautDePage.jspf"%>
		
		<div align="center">
			<c:if test="${erreur == 'oui'}">
				<div style="color:red">Erreur dans la requête !!</div>
			</c:if>

			<c:if test="${nomMenu == 'serie'}">
				<%@include file="./WEB-INF/JSPCentral/Series.jspf"%>	
			</c:if>
			<c:if test="${nomMenu == 'saison'}">
				<%@include file="./WEB-INF/JSPCentral/Saisons.jspf"%>	
			</c:if>
			
		</div>
		<!-- https://www.javatpoint.com/jsp-include-action -->
	</div>
</body>
</html>