<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
.spanData {
	color: black;
	text-decoration: underline;
}

h1 {
	text-decoration: underline;
}
</style>
<title>HOME</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

<script type="text/javascript" src = "../js/excelfile.js"></script>
</head>
<body>

	<nav class="navbar-expand-sm bg-dark navbar-dark sticky-top">
		<!-- Brand/logo -->
		
		<a class="navbar-brand" href="/home.do">HOME</a>

		<!-- Links -->
		<ul class="navbar-nav" style="float: right;">
			<li class="nav-item"><a class="nav-link" href="/user/login.do">Sign In</a></li>
			<li class="nav-item"><a class="nav-link" href="/user/join.do">Sign Up</a></li>
			<li class="nav-item" style="display: flex; margin-left: auto;">
				<div class="dropdown">
					<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">Menu</button>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="/file/excelchange.do">EXCEL</a>
						<a class="dropdown-item" href="#">없음</a>
						<a class="dropdown-item" href="#">없음</a>
						<a class="dropdown-item" href="#">없음</a>
						<a class="dropdown-item" href="#">없음</a>
					</div>
				</div>
			</li>
		</ul>
	</nav>