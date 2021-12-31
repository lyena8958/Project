<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="kor">
<head>
<meta charset="UTF-8">
<title>infoHelp</title>
<link rel="stylesheet" href="fontawesome/css/all.min.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/templatemo-xtra-blog.css" rel="stylesheet">
<link rel="shortcut icon" href="img/favicon2.ico">


<!-- 자바스크립트 -->
	<!-- JQuery -->
<script src="js/jquery-3.6.0.min.js"></script>
	<!-- 이메일 중복체크 -->
<script src="js/emailCheck.js"></script>


</head>
<body>
	<div class="outer">
		<div class="inner">
			<form action="infoHelp.ucdo" class="fset mb-5 tm-comment-form" name="join">
			<input type="hidden" name="type" value="${param.type}">
				<div class="mb-4">
					<p class="signupt">이메일</p>
					<input class="form-control" name="id" id="ssid" type="text"
						placeholder="ID" maxlength=15> <span>@</span> <select
						name="mail" id="smail" class="selectEmail">
						<option selected>이메일 선택</option>
						<option>gmail.com</option>
						<option>daum.net</option>
						<option>naver.com</option>
						<option>kakao.com</option>
					</select> <br>
					
					<c:if test="${param.type == 'id'}">
						<input type="submit" class="s-btn tm-btn-primary s-btn-small checkID" id="submit" value="확인" id="submit">											
					</c:if>
					<c:if test="${param.type == 'pw'}">
						<input type="button" onclick="emptyID('infoHelp')"
							class="s-btn tm-btn-primary s-btn-small checkID" id="confirm" value="인증하기">	
						<input type="hidden" class="s-btn tm-btn-primary s-btn-small checkID" id="submit1" value="완료" onClick="disabledRemove()" id="submit"
						style="	width : 100%;	margin : auto;	display : block; margin-top : 50px;">
					</c:if>

				</div>
			</form>
		</div>
	</div>

</body>
</html>