<!-- <script type="text/javascript">
window.onload = function newcheck() {
	var today = new Date();
	var year = today.getFullYear();
	var month = ('0' + (today.getMonth() + 1)).slice(-2);
	var day = ('0' + today.getDate()).slice(-2);

	var dateString = year + '-' + month + '-' + day;
	
	pdate = new Date();
	var year = pdate.getFullYear();
	var pmonth = ('0' + (pdate.getMonth() + 1)).slice(-2);
	var pday = ('0' + pdate.getDate()).slice(-2);
	
	var ptimeString = phours + ':' + pminutes  + ':' + pseconds;
	var pdateString = pyear + '-' + pmonth + '-' + pday	+ ptimeString;
	
	// var pdateCompare = new Date(pdateString);
	pdate.setDate((pdate.getDate()*1)+1);
	console.log(pdate);
	var todayCompare = new Date(dateString);
	if(pdate<todayCompare){
		document.getElementById("newBox").style.display = "none";
	}
}
</script>
 -->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="info" required="true" type="java.util.ArrayList"%>

<c:forEach var="pl" items="${info}">

	<article class="col-12 col-md-6 tm-post">
		<hr class="tm-hr-primary">
		<a href="selectOne.pdo?pnum=${pl.pnum}"
			class="effect-lily tm-post-link tm-pt-60">

			<div class="tm-post-link-inner">
				<img src="${pl.path}" onerror="this.src='img/defaultImage.png'" alt="포스트사진" class="img-fluid"
				style="height: 500px; object-fit: cover;">
			</div> 
			<c:choose>
				<c:when test="${pl.isNew()}">
					<!-- 현재시간 -1일 전에 써진 글 만 newtag 붙임-->
					<span class="position-absolute tm-new-badge" id="newBox"> new </span>
				</c:when>
			</c:choose>
			<h2 class="tm-pt-30 tm-color-primary tm-post-title">${pl.title}</h2>
		</a>
		<p class="tm-pt-30">
			${pl.content}
			<!-- 글자 몇개로짜르는건 안해놓음! -->
		</p>
		<div class="d-flex justify-content-between tm-pt-45">
			<span class="tm-color-primary postCate">Category .
				${pl.category}</span> <span class="tm-color-primary postCate">${pl.pdate}</span>
		</div>
		<hr>
		<div class="d-flex justify-content-between">
			<span class="postInfo">${pl.comCnt} comments</span><span
				class="postInfo">${pl.plike} Likes</span><span class="postInfo">${pl.views}
				Views</span> <span class="byUser">Post by ${pl.writer}</span>
		</div>

	</article>
</c:forEach>