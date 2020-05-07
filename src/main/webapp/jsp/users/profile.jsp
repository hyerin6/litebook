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
                <!-- begin profile -->
                <div class="profile">
                    <div class="profile-header">
                        <!-- BEGIN profile-header-cover -->
                        <div class="profile-header-cover"></div>
                        <!-- END profile-header-cover -->

                        <!-- 사용자 기본 프로필 -->
                        <div class="profile-header-content">
                            <!-- 프로필 사진 -->
                            <div class="profile-header-img">
                                <sec:authentication property="user.profile.filePath" var="path"/>
                                <img src="${path}" alt="" onerror="this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg'">
                            </div>
                            <div class="profile-header-info"> <!-- 이름, 이메일 -->
                                <h4 class="m-t-10 m-b-5"><sec:authentication property="user.name" /></h4>
                                <p class="m-b-10"><sec:authentication property="user.email" /></p>
                                <sec:authorize access="authenticated">
                                    <a href="#" class="btn-gradient blue mini">Edit Profile</a>
                                </sec:authorize>
                            </div>
                        </div>

                        <!-- 네비게이션바 -->
                        <div class="main_nav tab_wrap">
                            <ul class="profile-header-tab nav nav-tabs center tab_menu_container">
                                <li class="nav-item tab_menu_btn on"><a href="#profile-posts" class="tab_menu_btn1 tab_menu_btn1 on active show" data-toggle="tab">POSTS</a></li>
                                <li class="nav-item tab_menu_btn"><a href="#profile-timeline" class="tab_menu_btn2"  data-toggle="tab">TIME LINE</a></li>
                                <li class="nav-item tab_menu_btn"><a href="#profile-follower" class="tab_menu_btn3"  data-toggle="tab">FOLLOWER</a></li>
                                <li class="nav-item tab_menu_btn"><a href="#profile-following" class="tab_menu_btn4"  data-toggle="tab">FOLLOWING</a></li>
                                <li class="nav-item tab_menu_btn"><a href="#profile-search" class="tab_menu_btn5"  data-toggle="tab">SEARCH</a></li>
                            </ul>
                        </div>
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
                                <li>
                                    <div class="timeline-body" style="padding-bottom: 10px;">
                                        <div class="form-group">
                                        <div class="panel-body timeline-comment-box" style="padding-top: 30px;">
                                            <form:form method="post" modelAttribute="insertPostDto" action="/posts/insertPost">
                                                <form:textarea path="mainText" class="form-control" placeholder="What are you thinking?" />
                                                    <div class="mar-top clearfix">
                                                        <form:button class="btn-gradient blue mini" type="submit" style="float: right; margin-top: 15px;">Share</form:button>
                                                    </div>
                                                </form:form>
                                        </div>
                                        </div>
                                    </div>
                                </li>
                                <c:forEach var="post" items="${ posts }">
                                <li>
                                    <div class="timeline-icon">
                                        <a href="javascript:;">&nbsp</a>
                                    </div>
                                    <div class="timeline-body">
                                        <div class="timeline-header">
                                            <span class="userimage">
                                                <sec:authentication property="user.profile.filePath" var="path"/>
                                                <img src="${path}" alt="" onerror="this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg'">
                                            </span>
                                            <span class="username"><a href="javascript:;">
                                                <sec:authentication property="user.name" />
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
                                            <div class="user"><img class="user" src="${path}" onerror="this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg'"></div>
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
                        </div>
                            <!-- end timeline -->
                        <!-- end #profile-post tab -->
                    </div>
                    <!-- 타임라인 -->
                    <div class="tab_box2 tab_box">
                        <div class="timeline_container">
                        TIME LINE
                        </div>
                    </div>
                    <!-- 팔로워 리스트 -->
                    <div class="tab_box3 tab_box">
                        <div class="follower_container">
                            <div class="people-nearby">
                                <div class="nearby-user">
                                    <div class="row">
                                        <div class="col-md-2 col-sm-2">
                                            <img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="user" class="profile-photo-lg" onerror="this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg'">
                                        </div>
                                        <div class="col-md-7 col-sm-7">
                                            <h5><a href="#" class="profile-link">Sophia Page</a></h5>
                                            <p>Software Engineer</p>
                                            <p class="text-muted">500m away</p>
                                        </div>
                                        <div class="col-md-3 col-sm-3">
                                            <button class="btn-gradient blue mini" type="button" style="margin-top: 30px; margin-left: 60px;">FOLLOW</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 팔로잉 리스트 -->
                    <div class="tab_box4 tab_box">
                        <div class="follower_container">
                            <div class="people-nearby">
                                <div class="nearby-user">
                                    <div class="row">
                                        <div class="col-md-2 col-sm-2">
                                            <img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="user" class="profile-photo-lg" onerror="this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg'">
                                        </div>
                                        <div class="col-md-7 col-sm-7">
                                            <h5><a href="#" class="profile-link">Sophia Page</a></h5>
                                            <p>Software Engineer</p>
                                            <p class="text-muted">500m away</p>
                                        </div>
                                        <div class="col-md-3 col-sm-3">
                                            <button class="btn-gradient blue mini" type="button" style="margin-top: 30px; margin-left: 60px;">UN FOLLOW</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 검색 -->
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