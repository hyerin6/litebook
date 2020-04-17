<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:url var="R" value="/" />
<link rel="stylesheet" href="${R}res/signup.css">
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
<div class="signup-container animated fadeInDown bootstrap snippets">
    <div class="signupbox bg-white"><br/><br/>
        <div class="signupbox-title">lite book</div>
        <div class="signupbox-title2">Create A New Account</div><br/>
        <div class="form-group">
            <form:form method="post" modelAttribute="userSignupDto" action="/users/signup" enctype="multipart/form-data">
                <div class="signupbox-textbox">
                    <form:label path="email">Email</form:label>
                    <form:input path="email" class="form-control" placeholder="Email"/>
                    <form:errors path="email" class="error" />
                </div>
                <div class="signupbox-textbox">
                    <form:label path="password1">Password</form:label>
                    <form:password path="password1" class="form-control" placeholder="Password" />
                    <form:errors path="password1" class="error" />
                </div>
                <div class="signupbox-textbox">
                    <form:label path="password2">Password</form:label>
                    <form:password path="password2" class="form-control" placeholder="Password" />
                    <form:errors path="password2" class="error" />
                </div>
                <div class="signupbox-textbox">
                    <form:label path="name">Name</form:label>
                    <form:input path="name" class="form-control" placeholder="Name" />
                    <form:errors path="name" class="error" />
                </div>
                <div class="signupbox-textbox">
                    <form:label path="phone">Phone</form:label>
                    <form:input path="phone" class="form-control" placeholder="Phone" />
                    <form:errors path="phone" class="error" />
                </div>
                <div class="signupbox-textbox">
                    <form:label path="profile">Profile</form:label>
                    <input type="file" class="form-control" placeholder="Profile" path="profile" />
                </div>
                <div class="signupbox-textbox">
                    <button type="submit" class="btn btn-primary btn-block">
                        <i class="glyphicon glyphicon-user"></i> Sign up
                    </button>
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>