<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>커스텀태그 테스트 페이지</title>
<script src="js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="fontawesome/css/all.min.css">
<!-- https://fontawesome.com/ -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/templatemo-xtra-blog.css" rel="stylesheet">
<!-- 파비콘 -->

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

.feeling_div {
	display: flex;
	justify-content: center;
	align-items: center;
}

.button-like {
	margin-left: 20px;
}

.like_a {
	background-color: white;
	border: 2px solid #D25A53;
	padding: 10px 20px;
	border-radius: 2px;
	color: #D25A53;
}

.likeActive {
	background-color: #D25A53;
	color: white;
}

.crset {
	resize: none;
	width: 460px;
	height: 152px;
}

.cwidth {
	width: 460px;
}

.dnone {
	display: none;
}
</style>



</head>
<body>
	<h1>커스텀태그 모르모트...</h1>
	
	<mytag:profileImageEdit/>
	<mytag:likeBtn singlePost="${param.pnum}" />
	<p>param.pnum == ${param.pnum}</p>
	<p>pnum == ${pnum}</p>
</body>
</html>