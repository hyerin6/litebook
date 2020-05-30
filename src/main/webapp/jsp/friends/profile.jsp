<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<c:url var="R" value="/" />
<link rel="stylesheet" href="${R}css/profile.css">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <!--  This file has been downloaded from https://bootdey.com  -->
    <!--  All snippets are MIT license https://bootdey.com/license -->
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
                            <!-- BEGIN profile-header-img -->
                            <div class="profile-header-img">
                                <img src="${user.profile.filePath}" alt="" onerror="this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg'">
                            </div>
                            <!-- END profile-header-img -->
                            <!-- BEGIN profile-header-info -->
                            <div class="profile-header-info">
                                <h4 class="m-t-10 m-b-5">
                                    ${user.name}
                                </h4>
                                <p class="m-b-10">
                                    ${user.email}
                                </p>

                            <a href="/users/profile" class="btn-gradient blue mini"> My Page </a>
                            </div>
                        </div>
                        <!-- BEGIN profile-header-tab -->
                        <div class="main_nav tab_wrap">
                            <ul class="profile-header-tab nav nav-tabs center tab_menu_container">
                                <li class="nav-item tab_menu_btn on"><a href="#profile-posts" class="tab_menu_btn1 tab_menu_btn1 on active show" data-toggle="tab">POSTS</a></li>
                                <li class="nav-item tab_menu_btn"><a href="#profile-timeline" class="tab_menu_btn2"  data-toggle="tab">SEARCH</a></li>
                            </ul>
                        </div>
                        <!-- END profile-header-tab -->
                    </div>
                </div>
                <!-- end profile -->
                <div class="tab_box_container tab_box_container">
                    <!-- begin profile-content -->
                    <div class="profile-content">
                        <!-- begin tab-content -->
                        <div class="tab-content p-0">
                            <!-- begin #profile-post tab -->
                            <div class="tab-pane fade active show tab_box1 tab_box on" id="profile-posts">
                                <!-- begin timeline -->
                                <ul class="timeline">
                                    <c:forEach var="post" items="${ posts }">
                                        <li>
                                            <div class="timeline-icon">
                                                <a href="javascript:;">&nbsp</a>
                                            </div>
                                            <div class="timeline-body">
                                                <div class="timeline-header">
                                            <span class="userimage">
                                                <img src="${user.profile.filePath}" alt="" onerror="this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg'"></span>
                                                    <span class="username"><a href="javascript:;">
                                                ${user.name}
                                            </a> <small></small></span>
                                                    <span class="date pull-right text-muted">${post.startedDate}</span>
                                                </div>
                                                <div class="timeline-content">
                                                    <p class="post">
                                                            ${post.mainText}
                                                    </p>
                                                </div>
                                                <div class="timeline-likes">
                                                    <div class="stats-right"> <span class="stats-text">21 Comments</span> </div>
                                                    <div class="stats">
                                                <span class="fa-stack fa-fw stats-icon">
                                                    <i class="fa fa-circle fa-stack-2x text-danger"></i>
                                                    <i class="fa fa-heart fa-stack-1x fa-inverse t-plus-1"></i>
                                                </span>
                                                        <span class="stats-total">4.3k</span>
                                                    </div>
                                                </div>
                                                <div class="timeline-footer">
                                                    <a href="javascript:;" class="m-r-15 text-inverse-lighter" style="margin-right: 5px;"> Like </a> |
                                                    <a href="javascript:;" class="m-r-15 text-inverse-lighter" style="margin-right: 5px; margin-left: 5px;"> Comment </a> |
                                                    <a href="javascript:;" class="m-r-15 text-inverse-lighter" style="margin-left: 5px;"> Delete </a>
                                                </div>
                                                <div class="timeline-comment-box">
                                                    <div class="user">
                                                        <sec:authentication property="user.profile.filePath" var="path"/>
                                                        <img class="user" src="${path}" alt="" onerror="this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg'">
                                                    </div>
                                                    <div class="input">
                                                        <form action="">
                                                            <div class="input-group">
                                                                <input type="text" class="form-control rounded-corner" placeholder="Write a comment...">
                                                                <span class="input-group-btn p-l-10">
                                          <button class="btn-gradient blue mini" type="button" style="margin-left: 15px;">Comment</button>
                                          </span>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- end timeline-body -->
                                        </li>
                                    </c:forEach>
                                <li>
                                    <!-- begin timeline-icon -->
                                    <div class="timeline-icon">
                                        <a href="javascript:;">&nbsp;</a>
                                    </div>
                                    <!-- end timeline-icon -->
                                    <!-- begin timeline-body -->
                                    <div class="timeline-body">
                                        Loading...
                                    </div>
                                    <!-- begin timeline-body -->
                                </li>
                            </ul>
                            <!-- end timeline -->
                        </div>
                            <!-- end #profile-post tab -->
                            <div class="tab_box5 tab_box">
                                SEARCH
                            </div>
                        </div>
                        <!-- end tab-content -->
                    </div>
                    <!-- end profile-content -->
                </div>
            </div>
        </div>
    </div>
</div>

<script src="http://netdna.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $('.tab_menu_btn').on('click',function(){
        //버튼 색 제거,추가
        $('.tab_menu_btn').removeClass('on');
        $(this).addClass('on')

        //컨텐츠 제거 후 인덱스에 맞는 컨텐츠 노출
        var idx = $('.tab_menu_btn').index(this);

        $('.tab_box').hide();
        $('.tab_box').eq(idx).show();
    });
</script>
</body>
</html>