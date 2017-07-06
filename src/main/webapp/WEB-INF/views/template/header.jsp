<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="navbar navbar-default navbar-static-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>

			<a class="navbar-brand" href="${pageContext.request.contextPath}">Book
				store</a>

		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath}">Home</a></li>
				<li><a href="#">Help</a></li>

			</ul>

			<form class="navbar-form navbar-left"
				action="${pageContext.request.contextPath}" method="GET">
				<div class="input-group">
					<input type="text" class="form-control" name="search" value="${searchQuery}">
					<div class="input-group-btn">
						<button type="submit" class="btn btn-default">Search</button>
						<button style="height: 34px" type="button"
							class="btn btn-default dropdown-toggle" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">
							<span class="caret"></span> <span class="sr-only">Toggle
								Dropdown</span>
						</button>
						<ul class="dropdown-menu dropdown-menu-right">
							<li><a> <label><input type="radio"
										name="selector" value="byTitle" checked> By title </label>
							</a></li>
							<li><a> <label> <input type="radio"
										name="selector" value="byGenre" onclick="byTitle.checked = false"> By genre
								</label>

							</a></li>
							<li><a> <label> <input type="radio"
										name="selector" value="byAuthor" onclick="byTitle.checked = false"> By author
								</label>

							</a></li>
						</ul>
					</div>
				</div>
			</form>

			<ul class="nav navbar-nav navbar-right">
				<li><a href="${pageContext.request.contextPath}/orders">Orders</a></li>
				<c:choose>
					<c:when test="${currentUser==null}">
						<li><a href="${pageContext.request.contextPath}/login">Login</a></li>
						<li><a href="${pageContext.request.contextPath}/register">Register</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
					</c:otherwise>
				</c:choose>
			</ul>

		</div>
	</div>
</nav>