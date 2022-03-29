<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<meta charset="utf-8">
<form action="/">
	<div class="form-group">
		<label for="email">Email address:</label> <input type="email" class="form-control" placeholder="your email" id="email">
	</div>
	<div class="form-group">
		<label for="pwd">Password:</label> <input type="password" class="form-control" placeholder="your password" id="pwd">
	</div>
	<div class="form-group form-check">
		<label class="form-check-label"> <input class="form-check-input" type="checkbox"> Remember me
		</label>
	</div>
	<button type="submit" class="btn btn-primary">로그인</button>
</form>

<%@ include file="../layout/footer.jsp"%>