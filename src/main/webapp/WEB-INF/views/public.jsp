<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Public Page</title>
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
<body onload='document.PublicForm.name.focus();'>

	<div id="box">
		<h1 align="center">Petty Cash Management Public</h1>
		<h3 align="left"><a href="/pettycash">Main Page</a></h3>
	</div>

	<div id="box">
		<form name='PublicForm' action="<c:url value='/public/order' />"
			method='POST'>

			<table>
				<tr>
					<td>Name:</td>
					<td><input type='text' name='name' required = "required" width="300px"></td>
				</tr>
				<tr>
					<td>Amount:</td>
					<td width="300"><input type='number' step="0.01" min="0.01"
						max="10000000000" name='amount' required="required"
						></td>
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit"
						value="submit" /></td>
				</tr>
			</table>

		</form>
	</div>
	<div id="box">

		<h2>Action Information</h2>

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
	</div>


</body>
</html>