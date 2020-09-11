<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!-- pour le prefix on peut mettre ce que l'on veut -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GESTION SERIES FILM</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<link media="all" rel="stylesheet" href="<%=request.getContextPath()%>/CSS/Styles.css" type="text/css"/>
<link media="all" rel="stylesheet" href="<%=request.getContextPath()%>/CSS/BoutonHaut.css" type="text/css"/>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script type="text/javascript" src="JS/app.js"></script>
</head>
<body style="height:100%; position: relative;">

	<!-- AFFICHAGE DE LA CATEGORIE DU MENU CONCERNE -->

	<div class="container-fluid" id="haut">
	
		<!-- Mise en place des éléments fixes même après scroll -->
		<div class="sticky-top" style="background-color:white">
			<%@include file="./WEB-INF/Header/hautDePage.jspf"%>
			<%@include file="./WEB-INF/Header/menu2.jspf"%>
		</div>
	
		<div align="center">
			
			<!-- Affichage si il y a une erreur -->
			<c:if test="${erreur == 'oui'}">
				<div style="color:red">Erreur dans la requête !!</div>
			</c:if>

			<!-- Affichage des jspf centrales -->
			<c:if test="${nomMenu == 'serie'}">
				<%@include file="./WEB-INF/JSPCentral/Series.jspf"%>	
			</c:if>
			<c:if test="${nomMenu == 'saison'}">
				<%@include file="./WEB-INF/JSPCentral/Saisons.jspf"%>	
			</c:if>
			<c:if test="${nomMenu == 'episode'}">
				<%@include file="./WEB-INF/JSPCentral/Episodes.jspf"%>	
			</c:if>
			<c:if test="${nomMenu == 'AVoir'}">
				<%@include file="./WEB-INF/JSPCentral/AVoir.jspf"%>	
			</c:if>
			<br>
		</div>
	</div>
	
	<!-- Affichage de la fleche pour remonter en haut -->	
	<div class="cRetour"></div>

</body>
</html>