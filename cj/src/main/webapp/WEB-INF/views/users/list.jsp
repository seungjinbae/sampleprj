<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>    
<!DOCTYPE html>
<html lang="kr">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Java Web Programming</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link href="/css/styles.css" rel="stylesheet">
    <script>
    	function goPage(page){
        	/*
        	page = page-1;
        	location.href="/users/list?page="+page;
        	*/

        	$("#page").val(page-1);
        	$("#iForm").target = "_self";
        	$("#iForm").action = "/users/list";
    		$("#iForm").submit();
        }

        function schList(){
            $("#iForm").submit();
        }
    </script>
</head>
<body>
<%@ include file="/include/navi.jsp" %>

<div class="container" id="main">
   <div class="col-md-10 col-md-offset-1">
      <div class="panel panel-default">
      	  <a href="/users/form" class="btn btn-success" role="button">회원 등록</a>
      	  <div class="col-md-10 col-md-offset-1">
      	  	<form id="iForm" name="iForm" method="post" action="list">
      	  		<input type="hidden" id="page" name="page" value="${query.page} }">
	      	  	<input type="text" id="sch_value" name="sch_value" value="${query.sch_value}" placeholder="회원명">
	      	  	<a href="javascript:;" onclick="schList()" class="btn btn-success" role="button">검색</a>	
      	  	</form>
      	  </div>
          <table class="table table-hover">
              <thead>
                <tr>
                    <th>#</th><th>등급</th><th>사용자 아이디</th> <th>이름</th> <th>이메일</th><th></th>
                </tr>
              </thead>
              <tbody>
              <c:forEach var="user" items="${users.content}">
                <tr>
                    <th scope="row"></th><th>${user.userClass.getValue()}</th><td>${user.userId}</td> <td>${user.name}</td> <td>${user.email}</td><td><a href="/users/${user.id}/edit" class="btn btn-success" role="button">수정</a></td>
                </tr>              
              </c:forEach>
              </tbody>
          </table>
          ${pagelist}
        </div>
    </div>
</div>

<%@ include file="/include/footer.jsp" %>
	</body>
</html>