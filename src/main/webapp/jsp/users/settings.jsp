<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url var="R" value="/" />
<link rel="stylesheet" href="${R}css/signup.css">
<html>
<head>
    <meta charset="utf-8">
    <title>litebook</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <link href="http://netdna.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="signup-container animated fadeInDown bootstrap snippets">
    <div class="signupbox bg-white"><br/><br/>
        <div class="signupbox-title">lite book</div>
        <div class="signupbox-title2" style="margin-top: 15px;">변경 시, 재로그인해야 합니다.</div><br/>
        <div class="form-group">
            <form:form method="post" modelAttribute="userModifyDto" action="/users/modify" enctype="multipart/form-data">
                <div class="signupbox-textbox">
                    <form:label path="name">Name</form:label>
                    <form:input path="name" class="form-control" placeholder="${user.name}" />
                </div>
                <div class="signupbox-textbox">
                    <form:label path="phone">Phone</form:label>
                    <form:input path="phone" class="form-control" placeholder="${user.phone}" />
                    <form:errors path="phone" class="error" />
                </div>
                <div class="signupbox-textbox">
                    <form:label path="password">Password</form:label>
                    <form:password path="password" class="form-control" placeholder="Password" />
                </div>
                <div class="signupbox-textbox">
                    <form:label path="profile">Profile</form:label>
                    <form:input type="file" class="form-control" placeholder="Profile" path="profile"/>
                </div>

                <div class="signupbox-textbox" style="margin-top: 20px;">
                    <button type="submit" class="btn btn-primary btn-block">
                        <i class="glyphicon glyphicon-user"></i> UPDATE
                    </button>
                </div>
                <div class="signupbox-textbox">
                    <div class="btn btn-primary btn-block">
                        <a href="/users/profile" class="glyphicon glyphicon-user" style="color: #eeeeee">CANCEL</a>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>
