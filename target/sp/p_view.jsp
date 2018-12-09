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

</body>
<div class="container">
	<nav class="navbar navbar-light bg-light justify-content-between">
		<a class="navbar-brand">Welcome Professor <c:out
				value="${sessionScope.SP_USERNAME}" /></a>
		<form class="form-inline">
			<button class="btn btn-outline-success my-2 my-sm-0" type="submit"><a href="/sp/logout">Logout</a></button>
		</form>
	</nav>
	<br> <br>
	<table class="table">
		<thead>
			<tr>
				<th scope="col">Student Name</th>
				<th scope="col">Email</th>
				<th scope="col">Grade</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${data }" var="element">
				<tr>
					<td><c:out value="${element.NAME }"></c:out></td>
					<td><c:out value="${element.EMAIL }"></c:out></td>
					<td><c:out value="${element.GRADE }"></c:out></td>
					<td><button onclick="showPopup('${element.STUDENT_SUBJECT_ID}','${element.STUDENT_ID}','${element.GRADE }','${element.EMAIL }','${element.NAME }')" type="button" class="btn btn-success">Edit</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalCenterTitle"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle">Update Grade</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<form action="/sp/professor/save" method="post">
				<div class="modal-body">
					<div class="form-group row">
						<label for="staticEmail" class="col-sm-2 col-form-label">Name</label>
						<div class="col-sm-10">
							<input type="text" readonly class="form-control-plaintext"
								id="popName" value="">
						</div>
					</div>
					<div class="form-group row">
						<label for="staticEmail" class="col-sm-2 col-form-label">Email</label>
						<div class="col-sm-10">
							<input type="text" readonly class="form-control-plaintext"
								id="popEmail" value="">
						</div>
					</div>
					<div class="form-group row">
						<label for="staticEmail" class="col-sm-2 col-form-label">Grade</label>
						<div class="col-sm-10">
							<select class="form-control" name="grade" id="popGrade">
								<option>A</option>
								<option>B</option>
								<option>C</option>
								<option>D</option>
								<option>F</option>
							</select>
						</div>
					</div>
					<input type="hidden" value="" name="sid" id="sid">
				<input type="hidden" value="" name="ssid" id="ssId">
				<input type="hidden" value="${param.psid}" name="psid">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="submit" class="btn btn-primary">Update</button>
				</div>
				</div>
				<div class="modal-footer">
				
			</form>
		</div>
	</div>
</div>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>
<script type="text/javascript">
function showPopup(ssid, sid, grade,  name, email){
	$(".modal-body #popName").val( name );
	$(".modal-body #popEmail").val( email );
	$(".modal-body #ssId").val( ssid );
	$(".modal-body #sid").val( sid );
	$(".modal-body #popGrade").val( grade );
	$('#myModal').modal('show');
	
}
</script>
</html>