<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<html>
<head>
<title>Custodian Page</title>
<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

.cashbalance {
	padding: 15px;
	margin-bottom: 10px;
	text-align: center;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#box {
	width: 600px;
	padding: 15px;
	margin: 30px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>
</head>
<body>

	<div id="box">
		<h1 align="center">Petty Cash Management Custodian</h1>

		<h3 align="right">User: ${username}</h3>
		<h3 align="left">
			<a href="/pettycash">Main Page</a>
		</h3>
		<h3 align="left">
			<a href="/pettycash/logout">Logout</a>
		</h3>

	</div>

	<div id="box">

		<h2 align="center">Cash Balance</h2>
		<c:if test="${not empty cashbalance}">
			<div class="cashbalance">${cashbalance}</div>
		</c:if>


		<h2>Order Cash</h2>


		<!-- 
		<form name='loginForm'
		  action="<c:url value='/j_spring_security_check' />" method='POST'>
		-->
		<form name='orderForm' action="<c:url value='/custodio/register' />"
			method='POST'>

			<table>
				<tr>
					<td>Amount:</td>
					<td><input type='number' step="0.01" min="0.01"
						max="10000000000" name='amount' required="required"></td>

					<td colspan='2'><input name="submit" type="submit"
						value="submit" /></td>
				</tr>
			</table>

		</form>
	</div>




	<div id="box">
		<h2 align="center">Public Orders to approve</h2>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>#ID</th>
					<th>Name</th>
					<th>Amount</th>
					<th>Date</th>
					<th>Approve</th>
				</tr>
			</thead>

			<c:forEach var="request" items="${requests}">
				<tr>
					<td>${request.id}</td>
					<td width="150px" align="center">${request.requestUser.name}</td>
					<td width="100px" align="center">${request.amount}</td>
					<td width="200px" align="center">${request.requestDate}</td>

					<td align="center"><spring:url
							value="/custodio/approve/${request.id}" var="approveUrl" /> <form:form
							class="btn btn-danger" method="post" action="${approveUrl}">
							<input type="hidden" name="id" value="${request.id}" />
							<input type="submit" name="menuButton" value="Approve" />

						</form:form></td>
				</tr>
			</c:forEach>
		</table>

	</div>
	<div id="box">

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
	</div>


</body>
</html>