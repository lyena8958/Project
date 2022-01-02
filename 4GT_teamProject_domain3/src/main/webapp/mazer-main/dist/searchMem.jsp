<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="assets/css/bootstrap.css">

<link rel="stylesheet"
	href="assets/vendors/perfect-scrollbar/perfect-scrollbar.css">
<link rel="stylesheet"
	href="assets/vendors/bootstrap-icons/bootstrap-icons.css">
<link rel="stylesheet" href="assets/css/app.css">
<link rel="shortcut icon" href="assets/images/favicon.svg"
	type="image/x-icon">
<script src="assets/js/jquery-3.6.0.min.js"></script>
<script src="assets/js/common.js"></script>
<head>
<meta charset="UTF-8">
<title>사원 검색기록</title>
</head>
<body id="searchWrap">
		<table id="searchContent">
			<tbody id="searchData">
				<tr>
					<th>Check</th>
					<th>사번</th>
					<th>이름</th>
					<th>부서</th>
				</tr>
				<c:forEach var="vo" items="${datas}">
					<tr>
						<td><input type="radio" id="search${vo.mnum}" name="text" value="${vo.mnum}"></td>
						<td>${vo.mnum}</td>
						<td>${vo.mName}</td>
						<td>${vo.teamName}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<button type="submit" class="btn btn-primary me-1 mb-1" onClick="searchMove()">조회</button>
</body>
</html>