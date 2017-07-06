<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container">
	<c:forEach items="${orders}" var="order">
		<table class="table " id="order-info">
			<tbody>
				<tr class="active">
					<th colspan="5">Order #${order.id } <small
						style="float: right; font-size: 75%; color: #777777;">${order.getShortDate() }</small>
					</th>
				</tr>
				<tr>
					<td style="border-right: 1px solid #DDDDDD" class="small">
						<form class="form-horizontal small">
							<div class="form-group ">
								<label class="col-sm-4 control-label">First name</label>
								<div class="col-sm-6">
									<input type="text" class="form-control"
										value="${order.firstName }" readonly>
								</div>
							</div>
							<div class="form-group text-left">
								<label class="col-sm-4 control-label">Last name</label>
								<div class="col-sm-6">
									<input type="text" class="form-control"
										value="${order.lastName }" readonly>
								</div>
							</div>
							<div class="form-group text-left">
								<label class="col-sm-4 control-label">Address</label>
								<div class="col-sm-6">
									<input type="text" class="form-control"
										value="${order.address }" readonly>
								</div>
							</div>
						</form>
					</td>

					<td class="small">
						<form class="form-horizontal small">
							<div class="form-group ">
								<label class="col-sm-4 control-label"> Book </label>
								<div class="col-sm-3">
									<img alt="${order.book.title }" src="${order.book.img_url }"
										height="150px" width="100px">
								</div>
								<div class="col-sm-5" style="padding-top: 7px">
									${order.book.title }</div>

							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label"> Price </label>
								<div class="col-sm-6" style="padding-top: 7px">${order.book.price }</div>
							</div>
							<div class="form-group">

								<label class="col-sm-4 control-label"> Quantity </label>
								<div class="col-sm-6">
									<input class="form-control" value="${order.quantity }"
										style="width: 75px" readonly>
								</div>

							</div>
						</form>
					</td>
				</tr>
				<tr>
					<th colspan="2"><small style="float: right; padding-top: 7px"
						id="order-total">Total : ${order.getTotal() }</small></th>
				</tr>
			</tbody>
		</table>
	</c:forEach>
</div>
<script>
	
</script>
