<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<title>Create an account</title>

<link href="${contextPath}/bootstraps/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/bootstraps/css/common.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<div class="container">

		<form:form method="POST" modelAttribute="userForm"
			class="form-signin col-lg-4">
			<h2 class="form-signin-heading">Create your account</h2>

			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:label class="control-label" path="username">Login:</form:label>
				<form:input type="text" path="username" class="form-control"
					placeholder="Username" autofocus="true"></form:input>
				<form:errors path="username" class="small alert-warning"></form:errors>
			</div>

			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:label class="control-label" path="password">Password:</form:label>
				<form:input type="password" path="password" class="form-control"
					placeholder="Password"></form:input>
				<form:errors path="password" class="small alert-warning"></form:errors>
			</div>

			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:label class="control-label" path="passwordConfirm">Re-typed password:</form:label>
				<form:input type="password" path="passwordConfirm"
					class="form-control" placeholder="Confirm your password"></form:input>
				<form:errors path="passwordConfirm" class="small alert-warning"></form:errors>
			</div>

			<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>

			<h4 class="text-center">
				<a href="${contextPath}/login">Log in</a>
			</h4>

		</form:form>

	</div>
	<!-- /container -->
	<script src="${contextPath}/bootstraps/js/jquery.min.js"></script>
	<script src="${contextPath}/bootstraps/js/bootstrap.min.js"></script>
</body>
</html>