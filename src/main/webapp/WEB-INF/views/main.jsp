<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Main Page</title>
<style>
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
<body >
<div id="box">
<h1 align="center">Petty Cash Management</h1>
</div>


<c:if test="${not empty msg}">
<div id="box">
			<div class="msg">${msg}</div>
			</div>
		</c:if>

<div id="box">
<h1 align="center"><a href="public/view">Public Area</a></h1>

<br/>

<h1 align="center"><a href="custodio/view">Custodian Area</a></h1>
</div>

</body>
</html>