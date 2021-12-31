<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, model.reply.ReplyVO"%>
<jsp:useBean id="replyDAO" class="model.reply.ReplyDAO"/>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<% ArrayList<ReplyVO> datas = replyDAO.SelectAll(); %>


<c:forEach var="reply" items="<%=datas %>">
<!-- 삭제 -->
<a href="deleteReply.ucdo?rnum=${reply.rnum}">삭제</a>
<br>
<!-- 수정 -->
<form action="editReply.ucdo" method="post">
	<input type="hidden" name="rnum" value="${reply.rnum}">
	<input type="text" name="rment" value="${reply.rment}">
	<input type="submit" value="수정">
</form>
	${reply.rnum}  <br>
	${reply.rment} <br>
	${reply.rdate} <br>
	${reply.rwriter} <br>
	${reply.r_user} <br>
	${reply.r_post} <br>
	${reply.r_comments} <br>
	<hr>
</c:forEach>
<br><br><br><br><br>

<!-- 생성 -->
<form action="insertReply.ucdo" method="post">
	<input type="hidden" name="rwriter" value="몽">
	<input type="hidden" name="r_user" value="1111">
	<input type="hidden" name="r_post" value="1">
	<input type="hidden" name="r_comments" value="1">
	<input type="text" name="rment" placeholder="리플 입력">
		<input type="submit" value="생성">
</form>

</body>
</html>