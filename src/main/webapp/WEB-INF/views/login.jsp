<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container" id="form-login">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Login</h3>
				</div>

				<div class="panel-body">
					
					<form action="login" method="post">
						<div class="form-group">
							<label>Name</label> <input type="text" class="form-control"
								placeholder="Name" name="username" value="${userName }">
						</div>
						<div class="form-group">
							<label>Password</label> <input type="password"
								class="form-control" placeholder="Password" name="password">
						</div>
						<div class="checkbox">
							<label> <input name="remember-me" type="checkbox">Remember me</label>
						</div>
						<input name="${_csrf.parameterName }" value="${_csrf.token }"
							type="hidden">
						<button type="submit" class="btn btn-default">Login</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>