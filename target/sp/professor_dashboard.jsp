<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<body>
	<div class="container">
		<nav class="navbar navbar-light bg-light justify-content-between">
			<a class="navbar-brand">Welcome Professor <c:out value="${sessionScope.SP_USERNAME}" /></a>
			<form class="form-inline">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Logout</button>
			</form>
		</nav>
		<br><br>
		<div class="row">
		<c:forEach items="${data}" var="element">
		${element }
				<div class="card" style="width: 18rem;">
					<div class="card-body">
						<h5 class="card-title"><c:out value="${element.NAME }"></c:out></h5><br>
						<p class="card-text"><c:out value="${element}"></c:out></p>
						<a href="#" class="btn btn-primary">View subjects</a>
					</div>
				</div>
				
		
		</c:forEach>
		


	</div>
</body>
</html>