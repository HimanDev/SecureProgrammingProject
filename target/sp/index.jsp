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
	<form method="post" action="/sp/verify"
		style="width: 400px; margin: auto;margin-top: 200px">
		<div class="form-group">
			<label for="exampleInputEmail1">Username</label> <input type="text"
				class="form-control" id="exampleInputEmail1" required="required"
				aria-describedby="emailHelp" name="userName"
				value="${userName }"
				placeholder="Enter username"> <small id="emailHelp"
				class="form-text text-muted">We'll never share your email
				with anyone else.</small>
		</div>
		<div class="form-group">
			<label for="exampleInputPassword1">Password</label> <input
				type="password" name="password" required="required" value="${password }" class="form-control"
				id="exampleInputPassword1" placeholder="Password">
		</div>
		
		<br>

		<button type="submit" class="btn btn-primary">Submit</button>
		<br> <br>
		<c:if test="${error!=null }">
			<div class="alert alert-danger" role="alert">${error}</div>
		</c:if>


	</form>
</body>
</html>
