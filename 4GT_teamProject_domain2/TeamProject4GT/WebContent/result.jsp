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
<title>${word},${condition} 검색 결과</title>
<link rel="stylesheet" href="fontawesome/css/all.min.css">
<!-- https://fontawesome.com/ -->

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
</style>
<script src="js/Common.js"></script>
<!-- jQuery -->
<script type="text/javascript">

		 window.onload = function(){
			 
			 actRemove();
			 var main = $('#main'); // main , showPost, selectList 에 넣어야함, 이 친구들은 myActive로 넣어야함
			 main.addClass("myActive");
			 
			 
		 }

</script>


</head>
<body>
	<c:choose>
		<c:when test="${userInfoData!=null}">
			<mytag:clientSidebar />
		</c:when>
	</c:choose>
	<c:choose>
		<c:when test="${userInfoData==null}">
			<mytag:nonClientSidebar />
		</c:when>

	</c:choose>

	<main class="tm-main"> <!-- Search form --> <mytag:searchPost />
	<div class="container-fluid">
		<div class="row tm-row">
			<div class="col-lg-8 tm-post-col">
				<div class="tm-post-full">${condition}검색 결과</div>
			</div>
		</div>
		<div class="row tm-row">
			<mytag:postView info="${PostList}" />
		</div>
		<mytag:paging isFirst="${isFirst}" url="findpost.pdo"
			isLast="${isLast}" pagingIndex="${pagingIndex}" />
	</div>

	<footer class="row tm-row">
		<hr class="col-12">
		<div class="col-md-6 col-12 tm-color-gray">
			Design: <a rel="nofollow" target="_parent"
				href="https://templatemo.com" class="tm-external-link">TemplateMo</a>
		</div>
		<div class="col-md-6 col-12 tm-color-gray tm-copyright">
			Copyright 2020 Xtra Blog Company Co. Ltd.</div>

	</footer>
	</div>
	</main>

	<a href="tagtest.jsp?pnum=1">실험용쥐</a>
	<script src="js/jquery.min.js"></script>
	<script src="js/templatemo-script.js"></script>
</body>
</html>