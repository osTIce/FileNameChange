<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<form action="/" class="was-validated">
	<div class="form-group">
		<label for="email">Email:</label> <input type="text" class="form-control" id="email" placeholder="your email" name="email" required>
		<div class="valid-feedback">Valid.</div>
		<div class="invalid-feedback">Please fill out this field.</div>
	</div>
	<div class="form-group">
		<label for="password">Password:</label> <input type="password" class="form-control" id="password" placeholder="your password" name="password" required>
		<div class="valid-feedback">Valid.</div>
		<div class="invalid-feedback">Please fill out this field.</div>
	</div>
	<div class="form-group">
		<label for="username">Username:</label> <input type="text" class="form-control" id="username" placeholder="your username" name="username" required>
		<div class="valid-feedback">Valid.</div>
		<div class="invalid-feedback">Please fill out this field.</div>
	</div>
	<div class="form-group">
		<label for="nickname">Nickname:</label> <input type="text" class="form-control" id="nickname" placeholder="your nickname" name="nickname" required>
		<div class="valid-feedback">Valid.</div>
		<div class="invalid-feedback">Please fill out this field.</div>
	</div>
	<div class="form-group form-check">
		<label class="form-check-label"> <input class="form-check-input" type="checkbox" name="remember" required> I agree on blabla.
			<div class="valid-feedback">Valid.</div>
			<div class="invalid-feedback">Check this checkbox to continue.</div>
		</label>
	</div>
	<button type="submit" class="btn btn-primary">회원가입</button>
</form>

<%@ include file="../layout/footer.jsp"%>