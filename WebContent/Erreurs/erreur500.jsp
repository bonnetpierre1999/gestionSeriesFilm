<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PAGE non trouvée</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
</head>
<body>

	<div class="container-fluid" id="haut">
		<%@include file="../WEB-INF/Header/hautDePage.jspf"%>
	</div>
	
	<div class="container-fluid">
		<div class="text-danger">
			<h3>Erreur BDD</h3>
		</div>
		<br>
	</div>
</body>
</html>