<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="kor">
<head>
<script src="js/jquery-3.6.0.min.js"></script>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>UpdateUser</title>
<link rel="stylesheet" href="fontawesome/css/all.min.css">

<!-- 파비콘 -->
<link rel="shortcut icon" href="img/favicon2.ico">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/templatemo-xtra-blog.css" rel="stylesheet">
<style type="text/css">
.tm-comment-form{
	max-width : 100%;
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
#pwError, #nameError{
	/* width: 500px; */
	display: inline-block;
	color: red;
	font-size: 11px;
	font-weight: normal;
	margin-left: 10px;
}


</style>
<script src="js/Common.js"></script>
<script type="text/javascript">
window.onload = function(){
	 
	    actRemove();
		var myPage = $('#myPage');
		myPage.addClass("active");
		
		var userUpdate = document.userForm;
		
		var pwLimit = /^[a-zA-Z0-9~!@#$%^&*()_-]{10,20}/; // a~Z, 0~9, ~!@, ~!@#$%^&*()_- 를 10~20자 이내 입력가능
		var nameLimit = /^[ㄱ-ㅎㅏ-ㅣ가-힣0-9a-zA-Z_-]{1,10}$/; // 한글, a-Z, 0~9 _ - 를 1~10자 이내 입력가능
		
		var error = document.querySelectorAll('.error');
		var errorStr = ["10~20자의 영문,숫자, ~!@#$%^&*()_-만 사용 가능합니다.", "1~10자의 한글, 영문, 숫자 (_),(-)만 입력 가능합니다."];
		var errorId = ["pw", "name"];
		var input = document.querySelectorAll('.check');
		
		//-------------------------------------------------------------------------------------------
		console.log("error : "+error[0]);
		console.log("error : "+error[1]);
		console.log("errorStr : "+errorStr);
		console.log("errorid : "+errorId);
		console.log("input : "+input[0]);
		console.log("input : "+input[1]);
		
		function innerReset(error){
			for(var i = 0; i < error.length; i++){
				error[i].innerHTML = "";
				console.log("반복문 "+error[i]);
			}
		}
		
		innerReset(error);// 오류문구 초기화
		 
		// pw
		userUpdate.pw.onkeyup = function(){
			 innerReset(error);// 오류문구 초기화
	         
	         if (!pwLimit.test(input[0].value)) {
	            document.getElementById(errorId[0]+"Error").innerHTML = errorStr[0];
	         }
	         
		 }
		 // 성명
		 userUpdate.name.onkeyup = function(){
			 innerReset(error);// 오류문구 초기화
			 //var nameLimit = /^[ㄱ-ㅎㅏ-ㅣ가-힣0-9a-zA-Z_-]{1,10}$/;
	         
	         if (!nameLimit.test(input[1].value)) {
	            document.getElementById(errorId[1]+"Error").innerHTML = errorStr[1];
	         }
	         
		 } 
		
		 userUpdate.onsubmit = function(){
			innerReset(error);
			
			for (var i = 0; i < input.length - 1; i++) { // -1 == submit제외 
		           var nullStr = [" 비밀번호를", " 성함을"];
		           if (!input[i].value) {
		              document.getElementById(errorId[i]+"Error").innerHTML = nullStr[i]+ " 입력해 주세요.";
		              input[i].focus(); // 포커스 이동
		              return false; // 종료 (포커스 이동유지를 위해 false 종료)
		              break;
		           }
		    } 
			
			 // pw
			 if (!pwLimit.test(input[0].value)) {
	            document.getElementById(errorId[0]+"Error").innerHTML = errorStr[0];
	            userUpdate.pw.focus(); // 포커스 이동
	            return false;
	         }
			 // 이름
		 	 if (!nameLimit.test(input[1].value)) { 
	            document.getElementById(errorId[1]+"Error").innerHTML = errorStr[1];
	            userUpdate.name.focus(); // 포커스 이동
	            return false;
	         }
			 alert('회원정보 변경이 완료되었습니다.');
		}

}
</script>
</head>
<body>
	<mytag:clientSidebar/>
	<div class="container-fluid">
		<main class="tm-main"> <!-- Search form -->
		<div class="row tm-row">
			<div class="col-12">
				<form method="GET" class="form-inline tm-mb-80 tm-search-form">
					<input class="form-control tm-search-input" name="query"
						type="text" placeholder="Search..." aria-label="Search">
					<button class="tm-search-button" type="submit">
						<i class="fas fa-search tm-search-icon" aria-hidden="true"></i>
					</button>
				</form>
			</div>
		</div>
		<div class="row tm-row">
			<div style="text-align: center;" class="col-12">
				<hr class="tm-hr-primary tm-mb-55">
				<h2 style="color: #D25A53;">회원정보변경</h2>
				<br>
				<!-- signUp form -->
				<form action="updateUser.ucdo" method="post"
					style="display: inline-block;" class="mb-5 tm-comment-form" name="userForm">
					<div class="mb-4">
						<span class="signupt">아이디</span> <input class="form-control" style="width: 360px"
							 name="id" type="text" placeholder="ID" value="${userInfoData.id}" readonly>
							 <!--  -->
					</div>
					<div class="mb-4">
						<span class="signupt" id="pwHeader">비밀번호<span id="pwError" class="error"></span></span>
						<input class="form-control check" style="width: 360px"
							name="pw" type="text" placeholder="PW" value="${userInfoData.pw}" maxlength=20>
					</div>					
					<div class="mb-4">
						<span class="signupt" id="nameHeader">이&nbsp;름<span id="nameError" class="error"></span></span>
						<input class="form-control check" style="width: 360px"
							name="name" type="text" placeholder="NAME" value="${userInfoData.name}" maxlength=10>
					</div>
					<div class="text-right">
						<button type="submit" class="tm-btn tm-btn-primary tm-btn-small">정보수정</button>
					</div>
				</form>
			</div>

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