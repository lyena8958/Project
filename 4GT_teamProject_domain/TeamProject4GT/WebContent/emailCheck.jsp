<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="kor">
<head>
<meta charset="UTF-8">
<title>Email 인증번호 발송</title>
<link rel="stylesheet" href="fontawesome/css/all.min.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/templatemo-xtra-blog.css" rel="stylesheet">
<link rel="shortcut icon" href="img/favicon2.ico">
<style type="text/css">

@import url("css/4GT_CSS.css");

</style>
</head>
<body>

	<div class="outer">
		<div class="inner">

			<mytag:EmailCodeCheck code="${code_check}" type="${type}"/>

		</div>
	</div>

</body>
</html>