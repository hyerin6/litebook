<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url var="R" value="/" />
<link rel="stylesheet" href="${R}css/profile.css">
<!DOCTYPE html>
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
                        <!-- 친구의 기본 프로필 -->
                        <div class="profile-header-content">
                            <div class="profile-header-img">
                                <img src="${user.profile.filePath}" alt="" onerror="this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg'">
                            </div>
                            <div class="profile-header-info">
                                <h4 class="m-t-10 m-b-5">${user.name}</h4>
                                <div class="timeline-likes">
                                    <div class="stats">
                            <span class="fa-stack fa-fw stats-icon">
                                <i class="fa fa-circle fa-stack-2x text-danger"></i>
                                <i class="fa fa-heart fa-stack-1x fa-inverse t-plus-1"></i>
                            </span>
                                        <span class="stats-total" style="color: black"> ${likes} </span>
                                    </div>
                                    <br />
                                </div>
                                <p class="m-b-10">${user.email}</p>
                                <a href="/users/profile" class="btn-gradient blue mini"> My Page </a>
                                <a href="/follows/${user.id}" class="btn-gradient blue mini" style="margin-left: 5px;"> Follow </a>
                            </div>
                        </div>
                        <div class="main_nav tab_wrap">
                            <ul class="profile-header-tab nav nav-tabs center tab_menu_container">
                                <li class="nav-item tab_menu_btn on"><a href="#" class="tab_menu_btn1 tab_menu_btn1 on active show">POSTS</a></li>
                                <li class="nav-item tab_menu_btn"><a href="/users/search" target="_self" class="tab_menu_btn5">SEARCH</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="tab_box_container tab_box_container">
                    <div class="profile-content">
                        <div class="tab-content p-0">
                            <div class="tab-pane fade active show tab_box1 tab_box on" id="profile-posts">
                                    <h2 style="display: block; margin: 0px auto; text-align: center;">아직 ${user.name} 님이 작성한 게시글이 없습니다.</h2>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

