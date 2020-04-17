<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:url var="R" value="/" />
<link rel="stylesheet" href="${R}res/signin.css">
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style> .error { color: red; }</style>
</head>
<body>
<div class="login-container animated fadeInDown bootstrap snippets">
    <div class="loginbox bg-white"><br/><br/><br/>
        <div class="loginbox-title">lite book</div>
        <div class="loginbox-title2">시작하려면 로그인하세요.</div>
        <br/><br/>
        <form:form modelAttribute="userSigninDto" method="post" action="/users/signin_processing">
            <div class="loginbox-textbox">
                <form:label path="email">Email</form:label>
                <form:input path="email" class="form-control" placeholder="Email" />
                <form:errors path="email" class="error" />
            </div>
            <div class="loginbox-textbox">
                <form:label path="password">Password</form:label>
                <form:password path="password" class="form-control" placeholder="Password" />
                <form:errors path="password" class="error" />
            </div><br/><br/>
            <div class="loginbox-forgot">
                <a href="">Forgot Password?</a>
            </div>
            <div class="loginbox-submit">
                <button type="submit" class="btn btn-primary btn-block">
                    <i class="glyphicon glyphicon-user"></i> Login </button>
            </div>
            <div class="loginbox-signup">
                <a href="#register.html">Sign Up With Email</a>
            </div>
            <c:if test="${ param.error != null }"> <div class="loginbox-title2" style="color: red">
                아이디나 비밀번호가 맞지 않습니다. <br/>
                다시 확인해주세요.
            </div> </c:if>
        </form:form>
    </div>
</div>
</body>
</html>