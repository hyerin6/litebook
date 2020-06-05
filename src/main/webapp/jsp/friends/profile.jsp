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
                    <ul class="timeline posts">
                        <c:forEach var="post" items="${ posts }">
                            <li>
                                <div class="timeline-icon">
                                    <a>&nbsp</a>
                                </div>
                                <div class="timeline-body">
                                    <div class="timeline-header">
                                        <span class="userimage">
                                            <img src="${user.profile.filePath}" alt="" onerror="this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg'"></span>
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
                                            <a href="#">
                                        <span class="fa-stack fa-fw stats-icon">
                                            <i class="fa fa-circle fa-stack-2x text-danger"></i>
                                            <i class="fa fa-heart fa-stack-1x fa-inverse t-plus-1"></i>
                                        </span>
                                            </a>
                                            <span class="stats-total">4.3k</span>
                                        </div>
                                    </div>
                                    <div class="timeline-footer">
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
</div>
<script src="http://netdna.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script type="text/javascript">

    function formatDate(date) {
        var d = new Date(date),
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

    var lastIdOfPosts = <c:out value="${lastIdOfPosts}"/>;
    var minIdOfPosts = <c:out value="${minIdOfPosts}"/>;
    var isLoading = false;
    var userId = <c:out value="${user.id}"/>;
    let defaultProfile = 'https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg';

    $(window).scroll(function() {
        var window_height = window.innerHeight;
        if($(window).scrollTop() > 0 && !isLoading && lastIdOfPosts > minIdOfPosts) {
            if ($(window).scrollTop() == $(document).height() - window_height) {
                isLoading = true;

                    $.ajax({
                        type: 'POST',
                        url: '/' + userId + '/posts',
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

                            if(data.posts != null && data.posts.length != 0) {
                                for(let i = 0; i < data.posts.length; ++i) {
                                    let sd = formatDate(data.posts[i].startedDate);

                                    let profileImage = data.posts[i].user.profile == null ?
                                        defaultProfile : data.posts[i].user.profile.filePath;

                                    $(".posts").append(
                                        "<li>\n" +
                                        "<div class=\"timeline-icon\">\n" +
                                        "<a>&nbsp</a>\n" +
                                        "</div>" +
                                        "<div class=\"timeline-body\">\n" +
                                        "<div class=\"timeline-header\">\n" +
                                        "<span class=\"userimage\">\n" +
                                        "<img src=\"" + profileImage + "\">\n" +
                                        "</span>\n" +
                                        "<span class=\"username\">\n" +
                                        data.posts[i].user.name + "\n" +
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
                                        "<a href=\"#\">\n" +
                                        "<span class=\"fa-stack fa-fw stats-icon\">\n" +
                                        "<i class=\"fa fa-circle fa-stack-2x text-danger\"></i>\n" +
                                        "<i class=\"fa fa-heart fa-stack-1x fa-inverse t-plus-1\"></i>\n" +
                                        "</span>\n" +
                                        "</a>\n" +
                                        "<span class=\"stats-total\">4.3k</span>\n" +
                                        "</div>\n" +
                                        "</div>\n" +
                                        "<div class=\"timeline-footer\">\n" +
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

</script>
</body>
</html>