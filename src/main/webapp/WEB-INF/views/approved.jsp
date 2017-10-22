<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<html>
<head>
<title>Approved Requests Page</title>
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
		<h1 align="center">Petty Cash Management Approved Requests</h1>

		<table>
			<tr>
				<td width="150px"><h3><a href="/pettycash">Main Page</a></h3></td>
				<td width="150px" align="center"><h3><a href="/pettycash/custodio/view">Custodian Page</a></h3></td>
				<td width="150px" align="center"><h3><a href="/pettycash/logout">Logout</a></h3></td>
				<td width="150px" align="Right"><h3>User: ${username}</h3></td>
			</tr>
		</table>
	</div>
	
	<div id="box">

		<h2>Request Approved Period:</h2>

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>

		  <form name='PeriodForm'
		  action="<c:url value='/custodio/approved/list' />" method='GET'>

		<table>
			<tr><td></td>
			<td>The Parameters must be in the format: "yyyy-MM-dd HH:mm" <br/>
				(example: "2017-10-22 16:30")</td></tr>
			<tr>
				<td>From:</td>
				<td><input type="datetime" name='from'></td>
			</tr>
			<tr>
				<td>To:</td>
				<td><input type="datetime" name='to' /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
				  value="submit" /></td>
			</tr>
		  </table>
		</form>
	</div>


	<div id="box">
		<h2 align="center">Approved Public Orders</h2>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>#ID</th>
					<th>Name</th>
					<th>Amount</th>
					<th>Requested Date</th>
					<th>Approved Date</th>
				</tr>
			</thead>

			<c:forEach var="request" items="${requests}">
				<tr>
					<td>${request.id}</td>
					<td width="150px" align="center">${request.requestUser.name}</td>
					<td width="100px" align="center">${request.amount}</td>
					<td width="200px" align="center">${request.requestDate}</td>
					<td width="200px" align="center">${request.deliveryDate}</td>
				</tr>
			</c:forEach>
		</table>

	</div>


</body>
</html>