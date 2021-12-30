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
<title>MyPage</title>
<link rel="stylesheet" href="fontawesome/css/all.min.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/templatemo-xtra-blog.css" rel="stylesheet">
<link rel="shortcut icon" href="img/favicon2.ico">
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

#ftsw {
	font-size: 20px;
	font-weight: bold;
}

.signupt {
	text-align: left;
	display: block;
	margin-left: 10px;
}

.myPageset {
	width: 360px;
	text-align: center;
	display: inline-block;
}
</style>
<script src="js/Common.js"></script>
<script type="text/javascript">
	window.onload = function() {

		actRemove();
		var myPage = $('#myPage');
		myPage.addClass("active");

	}
</script>

</head>
<body>
	<mytag:clientSidebar />

	<div class="container-fluid">
		<main class="tm-main"> <!-- Search form --> <mytag:searchPost />
		<div class="row tm-row">
			<div class="col-12">
				<hr class="tm-hr-primary tm-mb-55">

			</div>
		</div>
		<div class="row tm-row">
			<div class="col-lg-8 tm-post-col">
				<div class="tm-post-full">
					<div style="text-align: center;" class="col-12">

						<h2 style="color: #D25A53;">내 정보</h2>
						<br>
						<!-- 프로필사진 -->
						<!-- Profile Image 1422x800 -->

						<div class="col-12">
							<img src="${userInfoData.profile}" alt="${userInfoData.id} 프로필사진"
								class="mb-2 rounded-circle img-thumbnail" width="200px" height="200px">

						</div>
						<br>
						<div class="col-12">
							<button onclick="ChangeProfile()"
								class="tm-btn tm-btn-primary tm-btn-small">사진변경</button>
						</div>
						<br>

						<!-- MyPage -->
						<div class="myPageset mb-5 tm-comment-form">
							<div class="mb-4">
								<span class="signupt">아이디</span> <input class="form-control"
									name="id" type="text" placeholder="ID"
									value="${userInfoData.id}" readonly>
							</div>
							<div class="mb-4">
								<span class="signupt">비밀번호</span> <input class="form-control"
									name="pw" type="password" placeholder="PW"
									value="${userInfoData.pw}" readonly>
							</div>
							<div class="mb-4">
								<span class="signupt">이&nbsp;름</span> <input
									class="form-control" name="name" type="text" placeholder="NAME"
									value="${userInfoData.name}" readonly>
							</div>
						</div>
					</div>


				</div>
			</div>
			<aside class="col-lg-4 tm-aside-col">
				<div class="tm-post-sidebar">
					<hr class="mb-3 tm-hr-primary">
					<h2 class="mb-4 tm-post-title tm-color-primary">Categories</h2>
					<ul class="tm-mb-75 pl-5 tm-category-list">

						<li><a href="showMyPost.pdo" class="tm-color-primary">내 게시글보기</a></li>
						<li><a href="UpdateUser.jsp" class="tm-color-primary">회원정보
								변경</a></li>
						<li><a href="#"
							onclick="checkAlert('deleteUser.ucdo?id=${userInfoData.id}','회원탈퇴 하시겠습니까?')"
							class="tm-color-primary">회원탈퇴</a></li>

					</ul>
					<hr class="mb-3 tm-hr-primary">
					<h2 class="tm-mb-40 tm-post-title tm-color-primary">좋아요 누른 게시글</h2>
					<mytag:likePost info="${UserLikePost}" />
				</div>
			</aside>
		</div>
		<footer class="row tm-row">
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