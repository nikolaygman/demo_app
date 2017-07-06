<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<div class="container" id="form-register">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-default" >
				<div class="panel-heading">
					<h3 class="panel-title">Register</h3>
				</div>

				<div class="panel-body">
					<c:if test="${errorMessages!=null}">
						<div class="alert alert-danger" role="alert">
							<c:forEach items="${errorMessages}" var="message">
								<p>${message.getDefaultMessage()}</p>
							</c:forEach>
						</div>
					</c:if>
					<form action="register" method="post">
						<div class="form-group">
							<label>Name</label> <input type="text" class="form-control"
								placeholder="Name" name="username" value="${userName }">
						</div>
						<div class="form-group">
							<label>Password</label> <input type="password"
								class="form-control" placeholder="Password" name="password">
						</div>
						<input name="${_csrf.parameterName }" value="${_csrf.token }"
							type="hidden">
						<button type="submit" class="btn btn-default">Register</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
