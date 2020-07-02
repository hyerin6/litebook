<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url var="R" value="/" />
<link rel="stylesheet" href="${R}css/profile.css">
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
                    <img src="${user.profile == null ?
                    'https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg' : user.profile.filePath}">
                </div>
                <div class="profile-header-info"> <!-- 이름, 이메일 -->
                    <h4 class="m-t-10 m-b-5"><sec:authentication property="user.name" /></h4>
                    <div class="timeline-likes">
                        <div class="stats">
                            <span class="fa-stack fa-fw stats-icon">
                                <i class="fa fa-circle fa-stack-2x text-danger"></i>
                                <i class="fa fa-heart fa-stack-1x fa-inverse t-plus-1"></i>
                            </span>
                            <span class="stats-total" style="color: black"> ${user.likeCnt} </span>
                        </div>
                        <br />
                    </div>
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
                    <li class="nav-item tab_menu_btn on"><a href="/users/profile" target="_self" class="tab_menu_btn1 tab_menu_btn1 on active show">POSTS</a></li>
                    <li class="nav-item tab_menu_btn"><a href="/timeline/feeds" target="_self" class="tab_menu_btn2">TIME LINE</a></li>
                    <li class="nav-item tab_menu_btn"><a href="/followers" target="_self" class="tab_menu_btn3">FOLLOWER</a></li>
                    <li class="nav-item tab_menu_btn"><a href="/followings" target="_self" class="tab_menu_btn4">FOLLOWING</a></li>
                    <li class="nav-item tab_menu_btn"><a href="/users/search" target="_self" class="tab_menu_btn5">SEARCH</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="tab_box_container tab_box_container">
        <div class="profile-content">
            <div class="tab-content p-0">
                <div class="tab-pane fade active show tab_box1 tab_box on big-box" id="profile-posts">
                    <ul class="timeline posts">
                        <li>
                            <div class="timeline-body" style="padding-bottom: 10px;">
                                <div class="form-group">
                                    <div class="panel-body timeline-comment-box" style="padding-top: 30px;">
                                        <form:form method="post" modelAttribute="insertPostDto" action="/posts">
                                            <form:textarea path="mainText" class="form-control" rows="4" placeholder="What are you thinking?" />
                                            <div class="mar-top clearfix">
                                                <form:button class="btn-gradient blue mini" type="submit" style="float: right; margin-top: 15px;">Share</form:button>
                                            </div>
                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <c:forEach var="post" items="${ posts }" varStatus="vs">
                        <li>
                            <div class="timeline-icon">
                                <a>&nbsp</a>
                            </div>
                            <div class="timeline-body block">
                                <div class="timeline-header">
                                    <span class="userimage">
                                        <img src="${user.profile == null ?
                                        'https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg' : user.profile.filePath}">
                                    </span>
                                    <span class="username">
                                        ${user.name}
                                    </span>
                                    <span class="date pull-right text-muted">
                                            <fmt:formatDate value="${post.startedDate}" pattern="yyyy-MM-dd HH:mm"/>
                                    </span>
                                </div>
                                <div class="timeline-content">
                                    <p class="post">${post.mainText}</p>
                                </div>
                                <div class="timeline-likes">
                                    <div class="stats">
                                        <a href="/likes/${post.id}">
                                        <span class="fa-stack fa-fw stats-icon">
                                            <i class="fa fa-circle fa-stack-2x text-danger"></i>
                                            <i class="fa fa-heart fa-stack-1x fa-inverse t-plus-1"></i>
                                        </span>
                                        </a>
                                        <span class="stats-total">Like</span>
                                    </div>
                                </div>
                                <div class="timeline-footer">
                                    <div style="float: right;">
                                        <a style="color: aliceblue" href="/posts/${post.id}" class="btn-gradient blue mini">Delete</a>
                                        <button class="btn-gradient blue mini" type="button" id="viewDetailButton${vs.index}" data-target="#layerpop${vs.index}" data-toggle="modal">update</button>
                                        <form method="PATCH">
                                            <div class="modal fade" id="layerpop${vs.index}">
                                                <div class="modal-dialog">
                                                    <div class="modal-content" id="layerpop${vs.index}">
                                                        <div class="modal-header">
                                                            <div class="modal-title">
                                                                <h3 style="margin-bottom: 20px;">update</h3>
                                                                <p>
                                                                    수정을 원한다면 입력 후 '완료'를 누르세요. <br/>
                                                                    '취소'를 누르면 이전 페이지로 돌아갑니다.
                                                                </p>
                                                            </div>
                                                            <button type="button" class="close" data-dismiss="modal">×</button>
                                                        </div>
                                                        <div class="modal-body"><br/>
                                                            <textarea id="mainText${vs.index}" path="content" class="form-control" rows="4">${post.mainText}</textarea><br/>
                                                        </div>
                                                        <div class="modal-footer" id="layerpop${vs.index}">
                                                            <input id="layerpop${vs.index}" class="btn-gradient blue mini" type="button" data-dismiss="modal" onClick="update_btn(${post.id}, $('#mainText'.concat(${vs.index})).val());" value="완료" style="float: right; margin-top: 15px;"/>
                                                            <button type="button" class="btn-gradient blue mini" data-dismiss="modal" style="margin-top: 14px; margin-left: 10px;">취소</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </li>
                        </c:forEach>
                </div>
                </ul>
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
<script type="text/javascript">
    function formatDate(date) {
        let d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear(),
            hour = d.getHours(),
            minutes = d.getMinutes();

        if (month.length < 2)
            month = '0' + month;
        if (day.length < 2)
            day = '0' + day;
        if(hour.toString().length < 2)
            hour = '0' + hour;
        if(minutes.toString().length < 2)
            minutes = '0' + minutes;

        return [year, month, day].join('-') + " " + [hour, minutes].join(':');
    }

    let lastIdOfPosts = <c:out value="${lastIdOfPosts}" />;
    let minIdOfPosts = <c:out value="${minIdOfPosts}" />;
    let count = 0;
    let isLoading = false;
    let profileImage = "<c:out value="${user.profile == null ?
                    'https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg' : user.profile.filePath}" />";

        $(window).scroll(function() {
        var window_height = window.innerHeight;
        if($(window).scrollTop() > 0 && !isLoading && lastIdOfPosts > minIdOfPosts) {
            if ($(window).scrollTop() == $(document).height() - window_height) {
                isLoading = true; // 로딩 시작
                $.ajax({
                    type: 'POST',
                    url: '/myPosts',
                    headers: {
                        "Content-Type": "application/json",
                        "X-HTTP-Method-Override": "POST"
                    },
                    dataType: 'json',
                    data: JSON.stringify({
                        lastIdOfPosts: lastIdOfPosts
                    }),
                    success: function (data) {
                        console.log(data);
                        lastIdOfPosts = data.lastIdOfPosts;
                        count += 5;

                        if(data.posts != null && data.posts.length != 0) {
                            for(let i = 0; i < data.posts.length; ++i) {
                                let sd = formatDate(data.posts[i].startedDate);

                                $(".posts").append(
                                    "<li>\n" +
                                    "<div class=\"timeline-icon\">\n" +
                                    "<a>&nbsp</a>\n" +
                                    "</div>\n" +
                                    "<div class=\"timeline-body block\">\n" +
                                    "<div class=\"timeline-header\">\n" +
                                    "<span class=\"userimage\">\n" +
                                    "<img src=\"" + profileImage + "\">\n" +
                                    "</span>\n" +
                                    "<span class=\"username\">\n" +
                                    data.posts[i].user.name +
                                    "</span>\n" +
                                    "<span class=\"date pull-right text-muted\">\n" +
                                    sd +
                                    "</span>\n" +
                                    "</div>\n" +
                                    "<div class=\"timeline-content\">\n" +
                                    "<p class=\"post\">" + data.posts[i].mainText + "</p>\n" +
                                    "</div>\n" +
                                    "<div class=\"timeline-likes\">\n" +
                                    "<div class=\"stats\">\n" +
                                    "<a href=\"/likes/" + data.posts[i].id + "\">\n" +
                                    "<span class=\"fa-stack fa-fw stats-icon\">\n" +
                                    "<i class=\"fa fa-circle fa-stack-2x text-danger\"></i>\n" +
                                    "<i class=\"fa fa-heart fa-stack-1x fa-inverse t-plus-1\"></i>\n" +
                                    "</span>\n" +
                                    "</a>\n" +
                                    "<span class=\"stats-total\">Like</span>" +
                                    "</div>\n" +
                                    "</div>\n" +
                                    "<div class=\"timeline-footer\">\n" +
                                    "<div style=\"float: right;\">\n" +
                                    "<a style=\"color: aliceblue\" href=\"/posts/" + data.posts[i].id + "\" class=\"btn-gradient blue mini\">Delete</a>\n" +
                                    "<button class=\"btn-gradient blue mini\" type=\"button\" id=\"viewDetailButton" + count+i + "\" data-target=\"#layerpop" + count+i + "\" data-toggle=\"modal\">update</button>\n" +
                                    "<form method=\"PATCH\">\n" +
                                    "<div class=\"modal fade\" id=\"layerpop" + count+i + "\">\n" +
                                    "<div class=\"modal-dialog\">\n" +
                                    "<div class=\"modal-content\" id=\"layerpop" + count+i + "\">\n" +
                                    "<div class=\"modal-header\">\n" +
                                    "<div class=\"modal-title\">\n" +
                                    "<h3 style=\"margin-bottom: 20px;\">update</h3>\n" +
                                    "<p>\n" +
                                    "수정을 원한다면 입력 후 '완료'를 누르세요. <br/>\n" +
                                    "'취소'를 누르면 이전 페이지로 돌아갑니다.\n" +
                                    "</p>\n" +
                                    "</div>\n" +
                                    "<button type=\"button\" class=\"close\" data-dismiss=\"modal\">×</button>\n" +
                                    "</div>\n" +
                                    "<div class=\"modal-body\"><br/>\n" +
                                    "<textarea id=\"mainText" + count+i + "\" path=\"content\" class=\"form-control\" rows=\"4\">" + data.posts[i].mainText + "</textarea><br/>\n" +
                                    "</div>\n" +
                                    "<div class=\"modal-footer\" id=\"layerpop" + count+i + "\">\n" +
                                    "<input id=\"layerpop" + count+i + "\" class=\"btn-gradient blue mini\" type=\"button\" data-dismiss=\"modal\" onClick=\"update_btn(" + data.posts[i].id + ", $('#mainText'.concat(" + count+i + ")).val());\" value=\"완료\" style=\"float: right; margin-top: 15px;\"/>\n" +
                                    "<button type=\"button\" class=\"btn-gradient blue mini\" data-dismiss=\"modal\" style=\"margin-top: 14px; margin-left: 10px;\">취소</button>\n" +
                                    "</div>\n" +
                                    "</div>\n" +
                                    "</div>\n" +
                                    "</div>\n" +
                                    "</form>\n" +
                                    "</div>\n" +
                                    "</div>\n" +
                                    "</div>\n" +
                                    "</li>"
                                )
                            }
                        }
                        isLoading = false;
                    },
                    error: function(request, status, error){
                        this.isLoading = false;
                        alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" +  "error:" + error);
                    }
                });
            }
        }
    });

    function update_btn(id, mainText) {
        $.ajax({
            type: 'PATCH',
            url: '/api/posts',
            headers: {
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "POST"
            },
            dataType: 'json',
            data: JSON.stringify({
                lastIdOfPosts: lastIdOfPosts,
                id: id,
                mainText: mainText
            }),
            success: function () {
                location.href = location.href;
            },
            error: function () {
                location.href = location.href;
            }
        });
    };

</script>
</body>
</html>