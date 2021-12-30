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
<title>Post</title>

<link rel="stylesheet" href="fontawesome/css/all.min.css">
<!-- https://fontawesome.com/ -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/templatemo-xtra-blog.css" rel="stylesheet">
<!-- 파비콘 -->

<link rel="shortcut icon" href="img/favicon2.ico">

<script src="js/Common.js"></script>
<script type="text/javascript">
       window.onload = function(){
          //window.scrollTo({top:500, behavior:"smooth"}); // 스크롤 조절기능(댓글창 이동)
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
	<div class="container-fluid">
		<main class="tm-main"> <!-- Search form --> <mytag:searchPost />
		<div class="row tm-row">
			<div class="col-12">
				<hr class="tm-hr-primary tm-mb-55">
				<!-- 사진 1422x800 -->
				<img src="${singlePost.path}" alt="포스팅사진"
					onerror="this.src='img/defaultImage.png'" width="954" height="700">

			</div>
		</div>
		<br>

		<div class="row tm-row">
			<div class="col-lg-8 tm-post-col">
				<div class="tm-post-full">
					<div class="mb-4">
						<h2 class="pt-2 tm-color-primary tm-post-title" id="title">${singlePost.title}</h2>
						<p class="tm-mb-40">${singlePost.pdate} posted by ${singlePost.writer}</p>
						<p>${singlePost.content}</p>
						<span class="d-block text-right tm-color-primary">Category
							. ${singlePost.category}</span> <br> <br>

						<!-- 좋아요버튼 -->
						<mytag:likeBtn />
						<br> <br>
						<c:choose>
							<c:when test="${userInfoData.id==singlePost.p_user}">
								<div class="text-right">

									<button
										onclick="location.href='editPost.pdo?pnum=${singlePost.pnum}';actChange('#main');"
										class="tm-btn tm-btn-primary tm-btn-small">글 수정</button>
									<button
										onclick="checkAlert('deletePostDB.pdo?pnum=${singlePost.pnum}','게시글을 삭제하시겠어요?')"
										class="tm-btn tm-btn-primary tm-btn-small">글 삭제</button>

								</div>
							</c:when>
						</c:choose>
					</div>

					<!-- Comments -->
					<div>
						<c:set var="index" value="0" />
						<h2 class="tm-color-primary tm-post-title">Comments</h2>
						<hr class="tm-hr-primary tm-mb-45">
						<c:forEach var="datas" items="${postOne_comments}">
							<c:set var="cl" value="${datas.comment}" />
							<div class="commentSet${index}">
								<!-- 변수설정 > index별 멤버변수 접근가능 -->

								<div class="tm-comment tm-mb-45">
									<figure class="tm-comment-figure">
										<img src="userProfile/${cl.c_user}_profile.jpg"
											alt="${cl.c_user} 프로필사진"
											onerror="this.src='userProfile/defaultImage.jpg'"
											class="mb-2 rounded-circle img-thumbnail" width="100px" height="100px">
										<figcaption class="tm-color-primary text-center">${cl.cwriter}</figcaption>
									</figure>
									<div class="cwidth">

										<!-- 이예나 -->
										<!-- secretNum이 비밀댓글이라면 (1:비밀댓글, 0:일반댓글) -->
										<c:if test="${cl.secretNum==1}">
											<c:choose>
												<c:when
													test="${userInfoData.id==cl.c_user||userInfoData.id==singlePost.p_user}">
													<!-- 본인이거나, 작성자인 경우 댓글 내용출력 -->
													<p id="secretOpen">(비밀댓글)</p>
													<p id="pcmsg${index}">${cl.cment}</p>
												</c:when>

												<c:otherwise>
													<!-- 디폴트 -->
													<p id="pcmsg${index}" class="secret">비밀댓글 입니다.</p>
												</c:otherwise>
											</c:choose>

										</c:if>

										<!-- 일반 댓글이라면 댓글 내용출력 -->
										<c:if test="${cl.secretNum==0}">
											<p id="pcmsg${index}">${cl.cment}</p>
										</c:if>



										<!-- 평상시 코멘트내용 -->
										<%-- <p id="pcmsg${index}">${cl.cment}</p> --%>

										<!-- 수정시 textarea나오게 설정 -->
										<form action="editComment.ucdo" method="post"
											class="mb-5 tm-comment-form">
											<div>
												<input type="hidden" name="c_post" id="c_post${index}"
													value="${singlePost.pnum}"> <input type="hidden"
													name="c_user" id="c_user${index}"
													value="${userInfoData.id}"> <input type="hidden"
													name="cwriter" id="cwriter${index}"
													value="${userInfoData.name}"> <input type="hidden"
													name="cnum" id="cnum${index}" value="${cl.cnum}"> <input
													type="hidden" name="pcmsg" value="${index}">
												<textarea id="ucmsg${index}"
													class="crset dnone form-control" name="cment" rows="6"
													onKeyUp="checkByte(this,200)" required>${cl.cment}</textarea>
												<div class="text-right marginTop">
													<a href="javascript:void(0);"
														onclick="msgEditCancle(${index})" id="uCButton${index}"
														class="tm-color-primary dnone">취소</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<a href="javascript:void(0);"
														onclick="msgEditFinish(${index})" id="uButton${index}"
														class="tm-color-primary dnone">댓글수정</a>
													<!-- 
											<button type="submit" id="uButton${index}"
												class="uButton tm-btn tm-btn-primary tm-btn-small Edit">댓글수정</button>
												 -->
													<%-- <button type="submit" id="uButton${index}"
												class="dnone tm-btn tm-btn-primary tm-btn-small">댓글수정</button> --%>
												</div>
											</div>
										</form>
										<!-- 댓글 좋아요버튼 -->
										<p class="text-right" style="color: red" >
											<a href="javascript:void(0);"
											onclick="clikeButton(${index},${cl.cnum},${singlePost.pnum})"><i
												class='far fa-heart'></i></a>&nbsp;<span style="color: red" id="clike${index}">${cl.clikeCnt}</span>
										</p>
										<!-- <p class="text-right dnone" style="color: red">
										<i class='fas fa-heart' id="clikeUp"></i>&nbsp${cl.clikeCnt}
									</p> -->
										<div id="cOption${index}"
											class="d-flex justify-content-between comInfo">
											<!-- 비회원일때 -->
											<c:choose>
												<c:when test="${userInfoData.id==null}">
													<a href="#"
														onclick="checkAlert('Login.jsp','답글을 등록하시려면 로그인을해야합니다.\n로그인창으로 가시겠어요?')"
														class="tm-color-primary">답글</a>
												</c:when>
											</c:choose>
											<!-- 로그인상태일때 답글버튼 활성화 -->
											<c:choose>
												<c:when test="${userInfoData.id!=null}">
													<a href="javascript:void(0);"
														onclick="rmsgInsert(${index})" class="tm-color-primary">답글</a>
												</c:when>
											</c:choose>
											<!-- 로그인세션의 id와 글쓴이의 id가 같을경우만 수정삭제가능 -->
											<c:choose>
												<c:when test="${userInfoData.id==cl.c_user}">
													<a href="javascript:void(0);" onclick="msgEdit(${index})"
														class="tm-color-primary">수정</a>
													<a href="javascript:void(0);"
														onclick="msgDelete(${index},${cl.cnum},${cl.replyCnt},${singlePost.pnum});"
														class="tm-color-primary">삭제</a>
													<%-- <a href="#"
													onclick="checkAlert('deleteComment.ucdo?cnum=${cl.cnum}&replyCnt=${cl.replyCnt}&c_post=${singlePost.pnum}&index=${index}','댓글을 삭제하시겠어요?')"
													class="tm-color-primary">삭제</a> --%>
												</c:when>
											</c:choose>
											<span class="tm-color-primary" id="cdate${index}">
												${cl.cdate}</span>
										</div>
									</div>

								</div>
								<!-- 답글달기 -->
								<c:set var="rindex" value="0" />
								<div class="rwidth tm-comment-reply tm-mb-45 marginLeft dnone"
									id="crInsert${index}">
									<form action="insertReply.ucdo" method="post"
										class="mb-5 tm-comment-form">
										<div class="tm-comment">
											<input type="hidden" name="rwriter"
												value="${userInfoData.name}"> <input type="hidden"
												name="r_user" value="${userInfoData.id}"> <input
												type="hidden" name="r_post" value="${singlePost.pnum}">
											<input type="hidden" name="r_comments" value="${cl.cnum}">
											<input type="hidden" name="prmsg" value="${index}${rindex}">
											<textarea id="ucmsg${index}" class="rset form-control" style="width:500px"
												name="rment" rows="6" onKeyUp="checkByte(this,200)" required></textarea>
										</div>
										<div class="replSize text-right marginTop">
											<button type="submit" 
												class="tm-btn tm-btn-primary tm-btn-small">답글등록</button>
										</div>
									</form>
								</div>



								<!-- 답글(reply) 여기작업 -->
								<div class="tm-comment-reply tm-mb-45">
									<div class="rContent">
										<c:forEach var="rl" items="${datas.rlist}">
											<div class="replySet${index}${rindex}">
												<hr>
												<div class="tm-comment reply">
													<figure class="tm-comment-figure">
														<img src="userProfile/${rl.r_user}_profile.jpg"
															alt="${rl.r_user} 프로필사진"
															onerror="this.src='userProfile/defaultImage.jpg'"
															class="mb-2 rounded-circle img-thumbnail" width="100px" height="100px">
														<figcaption class="tm-color-primary text-center">${rl.rwriter}</figcaption>
													</figure>
													<p id="prmsg${index}${rindex}">${rl.rment}</p>

													<!-- 수정버튼 클릭시 변화되는 코드들 -->
													<form action="editReply.ucdo" method="post"
														class="mb-5 tm-comment-form">
														<div class="tm-comment ">
															<input type="hidden" name="r_post"
																value="${singlePost.pnum}"> <input type="hidden"
																name="rnum" id="rnum${index}${rindex}"
																value="${rl.rnum}"> <input type="hidden"
																name="index" value="">
															<!-- ${index} -->
															<textarea id="urmsg${index}${rindex}"
																class="rset dnone form-control urmsgSet" name="rment"
																rows="6" onKeyUp="checkByte(this,200)" required>${rl.rment}</textarea>
														</div>
														<div class="text-right marginTop">
															<a href="javascript:void(0);"
																onclick="rmsgEditCancle(${index},${rindex})"
																id="uRCButton${index}${rindex}"
																class="tm-color-primary dnone">취소</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

															<a href="javascript:void(0);"
																onclick="rmsgEditFinish(${index},${rindex})"
																id="urButton${index}${rindex}"
																class="tm-color-primary dnone">답글수정</a>

															<!-- <button type="submit" id="urButton${index}${rindex}"
														class="uButton tm-btn tm-btn-primary tm-btn-small">답글수정</button> -->
														</div>
													</form>
												</div>
												<!-- 여기 -->
												<span class="replyInfo">
													<!-- 답글 좋아요버튼 -->
													<p class="text-right" style="color: red" >
											<a href="javascript:void(0);"
											onclick="rlikeButton(${index},${rindex},${rl.rnum},${singlePost.pnum})"><i
												class='fas fa-heart'></i></a>&nbsp;<span style="color: red" id="rlike${index}${rindex}">${rl.rlikeCnt}</span>
										</p>
													</p> <!-- 비회원일 경우 날짜만 보임 --> <c:choose>
														<c:when test="${userInfoData.id==null}">
															<div class="text-right">
																<span class="tm-color-primary rmsgInfo">
																	${rl.rdate}</span>
															</div>
														</c:when>
													</c:choose> <!-- 로그인세션의 id와 글쓴이의 id가 같을경우만 수정삭제가능 --> <c:choose>
														<c:when test="${userInfoData.id==rl.r_user}">
															<div id="rOption${index}${rindex}"
																class="d-flex justify-content-between rmsgOption">
																<a href="javascript:void(0);"
																	onclick="rmsgEdit(${index},${rindex})"
																	class="tm-color-primary">수정</a> <a
																	href="javascript:void(0)"
																	onclick="rmsgDelete(${index},${rindex},${rl.rnum},${singlePost.pnum})"
																	class="tm-color-primary">삭제</a> <span
																	class="tm-color-primary" id="rdate${index}${rindex}">
																	${rl.rdate}</span>
															</div>
															<br>
														</c:when>
													</c:choose>
												</span> <span class="d-block text-right tm-color-primary"></span>
											</div>
											<c:set var="rindex" value="${rindex+1}" />

										</c:forEach>
									</div>
								</div>

								<c:set var="index" value="${index+1}" />
								<hr class="cHr">
							</div>
							<br>
						</c:forEach>


						<!-- 더보기 페이징 -->
						<c:choose>
							<c:when test="${singlePost.comCnt!=0&&ccnt<singlePost.comCnt}">
								<div style="text-align: right">
									<a href="selectOne.pdo?ccnt=${ccnt+5}&pnum=${singlePost.pnum}"
										id="ccnt">더보기</a>
								</div>
							</c:when>
						</c:choose>
						<br>
						<c:choose>
							<c:when test="${userInfoData!=null}">
								<form action="insertComment.ucdo" method="post"
									class="mb-5 tm-comment-form">
									<input type="hidden" name="c_post" value="${singlePost.pnum}">
									<input type="hidden" name="c_user" value="${userInfoData.id}">
									<input type="hidden" name="ccnt" value="${ccnt}">
									<input type="hidden" name="cwriter"
										value="${userInfoData.name}"> <input type="hidden"
										name="pcmsg" value="0">

									<h2 class="tm-color-primary tm-post-title mb-4">Your
										comment</h2>

									<!-- 비밀댓글 선택  -->
									<p id="comSecret">
										비밀댓글&nbsp;&nbsp;<input type="checkbox" name="secretNum" value="1">
									</p>

									<div class="mb-4">
										<!-- id="crset" -->
										<textarea class="crset form-control" name="cment" rows="6"
											onKeyUp="checkByte(this,200)" required></textarea>
									</div>

									<div class="text-right">
										<button type="submit"
											class="tm-btn tm-btn-primary tm-btn-small">댓글등록</button>
									</div>
								</form>
							</c:when>
						</c:choose>

						<c:choose>
							<c:when test="${userInfoData==null}">
								<button
									onclick="checkAlert('Login.jsp','댓글을 등록하시려면 로그인을해야합니다.\n로그인창으로 가시겠어요?')"
									class="tm-btn tm-btn-primary tm-btn-small">댓글등록</button>
							</c:when>
						</c:choose>

					</div>
				</div>
			</div>
			<aside class="col-lg-4 tm-aside-col">
				<div class="tm-post-sidebar">
					<hr class="mb-3 tm-hr-primary">
					<h2 class="mb-4 tm-post-title tm-color-primary">Categories</h2>
					<ul class="tm-mb-75 pl-5 tm-category-list">
						<li><a href="post.pdo?category=chi" class="tm-color-primary">치킨</a></li>
						<li><a href="post.pdo?category=piz" class="tm-color-primary">피자</a></li>
						<li><a href="post.pdo?category=ham" class="tm-color-primary">햄버거</a></li>
						<li><a href="post.pdo?category=kor" class="tm-color-primary">한식</a></li>
						<li><a href="post.pdo?category=cha" class="tm-color-primary">중식</a></li>
						<li><a href="post.pdo?category=jap" class="tm-color-primary">일식</a></li>
					</ul>
					<hr class="mb-3 tm-hr-primary">
					<h2 class="tm-mb-40 tm-post-title tm-color-primary">같은 카테고리
						인기글</h2>
					<mytag:likePost info="${categoryDatas}" />
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