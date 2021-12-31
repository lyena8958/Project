<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<%!/* public int getRandom() {
		int random = 0;
		random = (int) Math.floor((Math.random() * (99999 - 10000 + 1))) + 10000;
		return random;
		
	} */%>

	<!-- 회원가입인 경우, 즉시 전송 -->
<%-- <c:if test="${action == signUp }">
	<script>
		var id = document.getElementById("sid")
		var mail = document.getElementById("smail")

		// id를 입력하지 않았거나, 이메일을 선택하지 않았다면 alert창 띄움
		if (!(id.value && mail.value == "이메일 선택")) {
			alert('아이디를 입력해 주세요.');
			id.focus(); // 포커스 이동
			history.go(-1);
		}

		// 입력했더라면, 이동			// id, mail, 랜덤 코드 발급
		location.href = "codeSend.ucdo?id=" + id + "&mail=" + mail; // +"code_check="+ 표현식getRandom()
	</script>
</c:if>
	
	<!-- id/pw찾기 인 경우, 이메일 입력 -->
<c:if test="${action == infoSend }"> --%>
<form action="codeSend.ucdo" method="post" id="emailsend">
	<table>
		<tr>
			<td><input type="text" id="receiver" name="receiver"
				placeholder="E-Mail을 입력하세요"></td>
			<td><input type="submit" id="submit" value="Send"></td>
<%-- 			<td><input type="hidden" readonly="readonly" name="code_check"
				id="code_check" value="<%=getRandom()%>"></td> --%>
		</tr>
	</table>
</form>

<%-- </c:if> --%>