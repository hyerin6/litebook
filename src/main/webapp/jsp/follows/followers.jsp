<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url var="R" value="/" />
<link rel="stylesheet" href="${R}css/profile.css">
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>litebook</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <link href="http://netdna.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div id="content" class="content content-full-width">
                <div class="profile">
                    <div class="profile-header">
                        <div class="profile-header-cover"></div>
                        <!-- 사용자 기본 프로필 -->
                        <div class="profile-header-content">
                            <!-- 프로필 사진 -->
                            <div class="profile-header-img">
                                <img src="${user.profile.filePath}" alt="" onerror="this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg'">
                            </div>
                            <div class="profile-header-info"> <!-- 이름, 이메일 -->
                                <h4 class="m-t-10 m-b-5"><sec:authentication property="user.name" /></h4>
                                <p class="m-b-10"><sec:authentication property="user.email" /></p>
                                <sec:authorize access="authenticated">
                                    <a href="/users/modify" class="btn-gradient blue mini">settings</a>
                                </sec:authorize>
                                <sec:authorize access="authenticated">
                                    <a href="/users/logout" class="btn-gradient blue mini" style="margin-left: 5px;">logout</a>
                                </sec:authorize>
                            </div>
                        </div>

                        <!-- 네비게이션바 -->
                        <div class="main_nav tab_wrap">
                            <ul class="profile-header-tab nav nav-tabs center tab_menu_container">
                                <li class="nav-item tab_menu_btn"><a href="/users/profile" target="_self" class="tab_menu_btn1 tab_menu_btn1">POSTS</a></li>
                                <li class="nav-item tab_menu_btn"><a href="/timeline/feeds" target="_self" class="tab_menu_btn2">TIME LINE</a></li>
                                <li class="nav-item tab_menu_btn on"><a href="/followers" target="_self" class="tab_menu_btn3 on active show">FOLLOWER</a></li>
                                <li class="nav-item tab_menu_btn"><a href="/followings" target="_self" class="tab_menu_btn4">FOLLOWING</a></li>
                                <li class="nav-item tab_menu_btn"><a href="#profile-search" target="_self" class="tab_menu_btn5">SEARCH</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
<!-- 팔로워 리스트 : 나를 팔로우하는 사람들 -->
<div class="follower_container">
    <div class="people-nearby">
        <div class="nearby-user">
            <c:forEach var="following" items="${ followings }">
                <div class="row followers">
                    <div class="col-md-2 col-sm-2">
                        <img src="${following.follower.profile.filePath}" alt="user" class="profile-photo-lg" onerror="this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg'">
                    </div>
                    <div class="col-md-7 col-sm-7">
                        <h5><a href="/users/${following.follower.id}" class="profile-link"> ${following.follower.name} </a></h5>
                        <p>${following.follower.email}</p>
                    </div>
                    <div class="col-md-3 col-sm-3">
                        <a href="/follows/${following.follower.id}" class="btn-gradient blue mini" style="margin-top: 30px; margin-left: 60px;">FOLLOW</a>
                    </div>
                </div>
                <hr/><br/>
            </c:forEach>
        </div>
    </div>
</div>
</div>
</div>
</div>
</div>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script type="text/javascript"></script>
</body>
</html>