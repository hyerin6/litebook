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
                                <img src="${loginUser.profile == null ?
                    'https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg' : loginUser.profile.filePath}">
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
                                <li class="nav-item tab_menu_btn"><a href="/followers" target="_self" class="tab_menu_btn3">FOLLOWER</a></li>
                                <li class="nav-item tab_menu_btn"><a href="/followings" target="_self" class="tab_menu_btn4">FOLLOWING</a></li>
                                <li class="nav-item tab_menu_btn on"><a href="/users/search" target="_self" class="tab_menu_btn5 on active show">SEARCH</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div style="text-align: center;">
                    <form:form method="post" modelAttribute="userSearchDto" action="/users/search">
                    <div class="textbox" style="padding-top: 20px; margin: auto;">
                        <form:label path="name">Search</form:label>
                        <form:input path="name" class="form-control" placeholder="Name" />
                    </div>
                </div>
                <div class="textbox" style="padding-top: 5px; float: right">
                    <button type="submit" class="btn-gradient blue mini">
                        <i></i> Search
                    </button>
                </div>
                </form:form>

                <!-- 팔로워 리스트 : 나를 팔로우하는 사람들 -->
                <div class="follower_container">
                    <div class="people-nearby">
                        <div class="nearby-user">
                            <div class="row followers">
                                <h1>검색 결과가 없습니다. </h1>
                            </div>
                            <div class="row followers">
                                <h1>다른 검색어로 검색해보세요 ! </h1>
                            </div>
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