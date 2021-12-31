<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>

<link rel="stylesheet" href="fontawesome/css/all.min.css">
<!-- https://fontawesome.com/ -->
<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
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
</style>
<script src="js/Common.js"></script>
<!-- jQuery -->
<script type="text/javascript">
	window.onload = function() {

		actRemove();
		var main = $('#contactUs'); // main , showPost, selectList 에 넣어야함, 이 친구들은 myActive로 넣어야함
		main.addClass("active");

	}
</script>



<html lang="en">
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

<div class="container-fluid">
	<main class="tm-main"> <!-- Search form --> <mytag:searchPost />
	<div class="row tm-row">
		<div class="row tm-row tm-mb-45">
			<div class="col-12">
				<hr class="tm-hr-primary tm-mb-55">
				<img src="img/about-01.png" alt="Image" class="img-fluid">
			</div>
		</div>
		<div class="row tm-row tm-mb-40">
			<div class="col-12">
				<div class="mb-4">
					<h2 class="pt-2 tm-mb-40 tm-color-primary tm-post-title">개요</h2>
					<p>저희 조는 이용자들이 서로 소통을 할 수 있는 공유 블로그를 제작하였습니다. 네이버와 티스토리 블로그에
						모티브를 받아 제작하였고, 이용자들이 각자 탐방한 맛집들을 올릴 수 있게 하였습니다. 글은 물론, 사진까지 첨부 할 수
						있게하여 하나의 공유 커뮤니티를 제작하였습니다. 이용자들은 각자 글을 볼 수도, 작성할 수 도 있고, 카테고리별
						모아보기, 검색기능도 구현하여 원하는 맛집들의 정보들을 알 수 있습니다.</p>
					<p>또한 MyPage에 들어가 프로필 사진을 변경하고, 좋아요를 누른 글, 내가 작성한 글 등을 볼 수 있는
						기능을 추가하여, 커뮤니티의 성격을 띄는 공유 블로그를 제작하였습니다.</p>
					<br>
					<h2 class="pt-2 tm-mb-40 tm-color-primary tm-post-title">개발 환경</h2>
					<p>통합개발환경(IDE) : Eclipse photon</p>
					<p>사용언어 : JAVA, HTML, CSS, JavaScript, JSP, Ajax, JSTL</p>
					<p>서버 : ApacheTomcat</p>
					<p>데이터베이스 : OracleDB</p>
					<p>
						버전관리 : <a rel="nofollow"
							href="https://github.com/PracLee/TP_src_4GT" target="_blank">
							Github</a>
					</p>
					<p>개발모형 : 애자일방법론</p>
					<p>소통 : 현장회의, Discord 서버 이용</p>
					<br>
					<h2 class="pt-2 tm-mb-40 tm-color-primary tm-post-title">발전방향</h2>
					<p>추가적인 발전 방향으로는 사용자의 편의성을 추가하기 위해서 위치정보를 받아와서 Map에 마커로 표시할수 있는
						기능을 추가하고, 추가로 주문과 대기 시스템을 합쳐, 기다림없이 음식을 받을 수 있게하는 방향이 있습니다.</p>
				</div>
			</div>
		</div>
		<div class="row tm-row tm-mb-120">
			<div class="col-lg-4 tm-about-col">
				<div class="tm-bg-gray tm-about-pad MVCPart">
					<div class="text-center tm-mt-40 tm-mb-60">
						<i class="fas fa-server fa-4x tm-color-primary"></i>
						
					</div>
					<h2 class="mb-3 tm-color-primary tm-post-title">Model</h2>
					<p class="mb-0 tm-line-height-short">
						Oh Hyun Taek<br>Kim Hyuck Jae
					</p>
				</div>
			</div>
			<div class="col-lg-4 tm-about-col">
				<div class="tm-bg-gray tm-about-pad MVCPart">
					<div class="text-center tm-mt-40 tm-mb-60">
						<i class="fas fa-palette fa-4x tm-color-primary"></i>
					</div>
					<h2 class="mb-3 tm-color-primary tm-post-title">View</h2>
					<p class="mb-0 tm-line-height-short">Lee Tae Ho</p>
					<br>
				</div>
			</div>
			<div class="col-lg-4 tm-about-col">
				<div class="tm-bg-gray tm-about-pad MVCPart">
					<div class="text-center tm-mt-40 tm-mb-60">
						<i class="fas fa-gamepad fa-4x tm-color-primary"></i>
					</div>
					<h2 class="mb-3 tm-color-primary tm-post-title">Controller</h2>
					<p class="mb-0 tm-line-height-short">
						Lee Byoung Jae <br> Lee Ye Na
					</p>
				</div>
			</div>
		</div>

		<hr class="tm-hr-primary  tm-mb-55">
		<!-- 왜 안먹히지.. -->
		<h3 id="group">4GT 조직도</h3>
		<div id="4GT" style="border : 3px solid #D25A53; margin-bottom : 300px; padding-right:15px;">
			<!-- 파트장 -->
			<div class="row tm-row tm-mb-60">
				<div class="col-12"></div>
				<div class="col-lg-6 tm-mb-60 tm-person-col" id="partLeader">
					<div class="media">
						<img src="img/about-bj.jpg" alt="Image" class="img-fluid mr-4 img">
						<div class="media-body">
							<h2 class="tm-color-primary tm-post-title mb-2">이병재</h2>
							<h3 class="tm-h3 mb-3">파트장 / Controller</h3>
							<p class="mb-0 tm-line-height-short">힘들었지만 좋은 팀원들을 만나서 기분좋게
								개발에 임할 수 있었습니다. 가끔씩 치는 심한농담 참아주셔서 다들 감사합니다..!</p>
						</div>
					</div>
				</div>
			</div>

			<!-- 팀장 -->
			<div class="row tm-row tm-mb-60" id="team">
				<div class="col-12"></div>
				<div class="col-lg-6 tm-mb-60 tm-person-col">
					<div class="media tm-person teamLeader">
						<img src="img/about-yn.jpg" alt="Image" class="img-fluid mr-4 img">
						<div class="media-body">
							<h2 class="tm-color-primary tm-post-title mb-2">이예나</h2>
							<h3 class="tm-h3 mb-3">팀장 / C&V..?</h3>
							<p class="mb-0 tm-line-height-short">팀원들과 원활한 커뮤니케이션이 되어, 파트를
								수행할 때 집중할 수 있었습니다. 4GT최고!! 👍
						</div>
					</div>
				</div>
				<br> <br>
				<div class="col-lg-6 tm-mb-60 tm-person-col">
					<div class="media tm-person teamLeader">
						<img src="img/about-ht.JPG" alt="Image" class="img-fluid mr-4 img">
						<div class="media-body">
							<h2 class="tm-color-primary tm-post-title mb-2">오현택</h2>
							<h3 class="tm-h3 mb-3">팀장 / Model</h3>
							<p class="mb-0 tm-line-height-short">코드가 맞는 팀원들 덕분에 즐겁게 작업할 수 있었고, 협업하며 함께 성장하는 경험을 얻어서 좋았습니다!
							 </p>
						</div>
					</div>
				</div>
				<br> <br>
				<!-- 사원 -->
				<div class="col-lg-6 tm-mb-60 tm-person-col">
					<div class="media tm-person">
						<img src="img/about-th.jpg" alt="Image" class="img-fluid mr-4 img">
						<div class="media-body">
							<h2 class="tm-color-primary tm-post-title mb-2">이태호</h2>
							<h3 class="tm-h3 mb-3">사원 / View</h3>
							<p class="mb-0 tm-line-height-short">항상 디코와 카톡으로 질문에 친절히 답해주셔서 감사합니다~! 여러분의 도움으로 이제 완벽히 이해했답니다!</p>
						</div>
					</div>
				</div>
				<div class="col-lg-6 tm-mb-60 tm-person-col">
					<div class="media tm-person">
						<img src="img/about-hj.png" alt="Image" class="img-fluid mr-4 img">
						<div class="media-body">
							<h2 class="tm-color-primary tm-post-title mb-2">김혁재</h2>
							<h3 class="tm-h3 mb-3">사원 / Model</h3>
							<p class="mb-0 tm-line-height-short">처음 프로젝트 시작할때는 많이 부족했지만 팀장님들과 팀원들 덕분에 많이 성장하게 됐고 좋은 경험이였습니다! 잘자요^^</p>
						</div>
					</div>
				</div>
			</div>
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