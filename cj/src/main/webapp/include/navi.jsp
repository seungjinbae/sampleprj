<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<nav class="navbar navbar-fixed-top header">
    <div class="col-md-12">
        <div class="navbar-header">
            <a href="/index.html" class="navbar-brand">main</a>
        </div>
    </div>
</nav>
<div class="navbar navbar-default" id="subnav">
    <div class="col-md-12">
        <div class="navbar-header">
        </div>
        <div class="collapse navbar-collapse" id="navbar-collapse2">
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${sessUser.userId == null}">                
                <li><a href="/" role="button">로그인</a></li>                
                </c:if>
                <c:if test="${sessUser.userId != null}">
                <li class="active"><a>${sessUser.userId}</a></li>
                <li><a href="/users/logout" role="button">로그아웃</a></li>
                	<c:if test="${sessUser.userClass eq 'ADM'}">
                		<li><a href="/users/list" role="button">회원관리</a></li>
                	</c:if>
                <li><a href="/users/userUpdateForm" role="button">개인정보수정</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</div>