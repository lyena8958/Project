<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="kor">
<head>
<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>
<link rel="stylesheet" href="fontawesome/css/all.min.css">
<!-- 파비콘 -->
<link rel="shortcut icon" href="img/favicon2.ico">


<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/templatemo-xtra-blog.css" rel="stylesheet">
<style type="text/css">
@font-face {
	font-family: 'NanumSquareRound';
	src:
		url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/NanumSquareRound.woff')
		format('woff');
	font-weight: normal;
	font-style: normal;
}


.mlogo {
	width: 220px;
}

.btw {
	text-align-last: justify;
}

.fset {
	display: inline-block;
	width: 360px;
}
</style>
<script src="js/Common.js"></script>

<script type="text/javascript">
window.onload = function(){
	 
	 actRemove();
		var loginSignUp = $('#loginSignUp');
		loginSignUp.addClass("active");
	 
	 
}
</script>

</head>
<body>
	<mytag:nonClientSidebar />
	<div class="container-fluid">
		<main class="tm-main"> <!-- Search form --> <mytag:searchPost />
		<div class="row tm-row">
			<div style="text-align: center;" class="col-12">
				<hr class="tm-hr-primary tm-mb-55">
				<!-- login form -->

				<form action="joinUs.ucdo" method="post"
					class="fset mb-5 tm-comment-form">
					<input type="hidden" name = "pnum" value="${param.pnum}">
					<div class="mb-4">
						<input class="form-control" name="id" type="text" placeholder="ID">
					</div>
					<div class="mb-4">
						<input class="form-control" name="pw" type="password"
							placeholder="PW">
					</div>
					<div class="text-right">
						<button type="submit" class="tm-btn tm-btn-primary tm-btn-small" id="login">Login</button>
					</div>
					<div class="mb-4 btw" id="move">
						<a id="infoHelp" style="text-align: left;" href="#" onclick="infoHelp()">ID/PW찾기</a>
						<a class="text-right" id="signUpMove" href="SignUp.jsp">회원가입</a>
					</div>
				</form>

			</div>
		</div>

		<footer class="row tm-row" id="loginFooter">
			<div class="col-md-6 col-12 tm-color-gray">
				Design: <a rel="nofollow" target="_parent"
					href="https://templatemo.com" class="tm-external-link">TemplateMo</a>
			</div>
			<div class="col-md-6 col-12 tm-color-gray tm-copyright">
				Copyright 2020 Xtra Blog Company Co. Ltd.</div>
		</footer> </main>
	</div>
	<script src="js/jquery.min.js"></script>
	<script src="js/templatemo-script.js"></script>
</body>
</html>