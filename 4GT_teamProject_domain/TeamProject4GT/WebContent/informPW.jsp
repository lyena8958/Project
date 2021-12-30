<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

</head>
<body>
	<div class="outer">
		<div class="inner">
 											
						<div class="mb-4">
							<span>비밀번호</span><input class="form-control" type="text" value="${findUser.pw}" readonly>
						</div>												
						<div class="mb-4 btw">
							<a class="txtleft" href="findInfo.jsp?type=id">ID찾기</a>							
						</div>
								
		</div>
	</div>

</body>
</html>