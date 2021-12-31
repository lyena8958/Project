<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="kor">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SignUp</title>
<link rel="stylesheet" href="fontawesome/css/all.min.css">
<!-- https://fontawesome.com/ -->

<!-- 파비콘 -->
<link rel="shortcut icon" href="img/favicon2.ico">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/templatemo-xtra-blog.css" rel="stylesheet">


<!-- CSS -->
<style type="text/css">
@import url("css/signUp.css");
</style>


<!-- 자바스크립트 -->
	<!-- JQuery -->
<script src="js/jquery-3.6.0.min.js"></script>
	<!-- action --> 
<script src="js/Common.js"></script>  
	<!-- 이메일 중복체크 -->
<script src="js/emailCheck.js"></script>
	<!-- 유효성검사 --> 
<script src="SignUp2.js"></script> 


</head>
<body>
	<mytag:nonClientSidebar />
	
	<div class="container-fluid">
		<main class="tm-main"> <!-- Search form --> <mytag:searchPost />
		<div class="row tm-row">
			<div class="col-12" id="conteiner">
				<hr class="tm-hr-primary tm-mb-55">
				<h2 id="Header">회원가입</h2>
				<br>

				<form action="signUp.ucdo" method="post"
					class="fset mb-5 tm-comment-form" name="join">
					<div class="mb-4">
						<!-- 이예나: error추가, class check -->
						<p class="signupt">
							아이디
						</p>
						<input class="form-control" name="id" id="ssid" type="text" placeholder="ID" maxlength=15> <span>@</span>
						<select name="mail" id="smail" class="selectEmail">
							<option selected>이메일 선택</option>
							<option>gmail.com</option>
							<option>daum.net</option>
							<option>naver.com</option>
							<option>kakao.com</option>
						</select> 
						<br>
												
						<input type="button" onclick="emptyID('signUp')"
							class="s-btn tm-btn-primary s-btn-small checkID" id="confirm" value="중복인증">						

					</div>
					<div class="mb-4">
						<!-- 이예나: error추가 class check -->
						<p class="signupt" id="pwHeader">
							비밀번호 <span id="pwError"></span>
						</p>
						<input class="form-control check" name="pw" id="pw"
							type="password" placeholder="PW" maxlength=20>
					</div>
					<div class="mb-4">
						<!-- 이예나: error추가  class check-->
						<p class="signupt">
							비밀번호 확인<span id="pwCheckError"></span>
						</p>
						<input class="form-control check" name="pwCheck" id="pwCheck"
							type="password" placeholder="PW" maxlength=20>
					</div>
					<div class="mb-4">
						<!-- 이예나: error추가  class check-->
						<p class="signupt">
							이&nbsp;름<span id="nameError"></span>
						</p>
						<input class="form-control check" name="name" id="name"
							type="text" placeholder="NAME" maxlength=20>
					</div>
					<div class="mb-4" id="consent">
						<p>
							<span id="consentHeader">개인정보 수집/이용동의</span><span
								id="consentError"></span>
						</p>
						<div>
							<span id="consentBox"> <span id="ftsw">개인정보 처리방침</span><span></span>
								<br> ■ 개인정보의 수집 및 이용목적 회사는 수집한 개인정보를 다음의 목적을 위해 활용합니다. <br>
								- 서비스 제공에 관한, 서비스 제공에 따른 요금정산 콘텐츠 제공 , 구매 및 요금 결제 <br> - 고객
								관리: 고객 서비스 이용에 따른 본인확인 , 개인 식별 , 불량회원의 부정 이용 방지와 비인가 사용 방지 , 가입
								의사 확인 , 불만처리 등 민원처리 , 고지사항 전달 <br> - 마케팅 및 광고에 활용 : 이벤트 등
								광고성 정보 전달, 접속 빈도 파악 또는 회원의 서비스 이용에 대한 통계 <br> <br> ■
								수집하는 개인정보 항목 회사는 정보주체가 고객서비스(상담신청, 상담, 서비스 신청 등)를 이용하기 위하여 고객의
								개인 정보를 제공할 때의 서비스 제공을 위한 필수 정보와, 보다 향상된 서비스 제공을 위한 선택정보를 온라인상
								입력방법을 통하여 수집하고 있습니다. 수집하는 개인정보의 범위는 아래와 같습니다. <br> 개인정보
								수집항목 - 필수항목: 이름, 전화번호 , 이메일, 주소 <br> -자동수집 항목 : 접속로그( 주소,
								전화번호, 결제내역 ) 개인정보 수집방법 정보주체는 웹사이트 또는 서면 절차를 통하여 개인정보처리방침과 이용약관
								각각의 내용을 확인하고 ‘동의’ 또는 ‘동의하지 않는다’ 문구를 선택 할 수 있습니다. 정보 주체가 ‘동의’ 문구를
								선택한 경우에는 개인정보 수집에 동의한 것으로 봅니다.
							</span>
						</div>
						<span id="checkBox"> <label><input type="radio"
								id="check" name="check" value="동의">동의</label> <label><input
								type="radio" id="noneCheck" name="check" value="비동의"
								checked="checked">비동의</label>
						</span>
					</div>
					<div class="text-right">
						<input type="submit" id="signUpBtn"
							class="tm-btn tm-btn-primary tm-btn-small" value="sign-up">
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