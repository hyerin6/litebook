<%@ page pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:url var="R" value="/" />
<link rel="stylesheet" href="${R}res/signin.css">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <!--  This file has been downloaded from https://bootdey.com  -->
    <!--  All snippets are MIT license https://bootdey.com/license -->
    <title>lite book</title>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<div class="login-container animated fadeInDown bootstrap snippets">
    <div class="loginbox bg-white">
        <br/><br/>
        <div class="loginbox-title">lite book</div>
        <div class="loginbox-title2">시작하려면 로그인하세요.</div>
        <br/><br/>

        <div class="loginbox-textbox">
            <label>Email</label>
            <input type="text" class="form-control" placeholder="Email">
        </div>
        <div class="loginbox-textbox">
            <label>Password</label>
            <input type="text" class="form-control" placeholder="Password">
        </div>
        <div class="loginbox-forgot">
            <a href="">Forgot Password?</a>
        </div>
        <br/>
        <div class="loginbox-submit">
            <input type="button" class="btn btn-primary btn-block" value="Login">
        </div>
        <div class="loginbox-signup">
            <a href="#register.html">Sign Up With Email</a>
        </div>
    </div>
    <div class="logobox">
    </div>
</div>

<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript">

</script>
</body>
</html>