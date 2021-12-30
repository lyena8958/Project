<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ attribute name="code" required="true"%>
<%@ attribute name="type" required="true"%>
<script type="text/javascript">
	function checkCode(type) {
		var v1 = document.getElementById('checkEmail').code_check.value;
		var v2 = document.getElementById('checkEmail').code.value;
		//console.log(v2.length);
		// 오류문구 초기화
		document.getElementById('checkMailCode').innerHTML = "";

		// 텍스트 입력값이 5자 이상일 때만 코드체크
		if (v2.length >= 5) {
			if (v1 != v2) {// 사용자 입력값과 발급번호와 일치하지 않다면 - 오류문구 출력
				document.getElementById('checkMailCode').style.color = "red";
				document.getElementById('checkMailCode').innerHTML = "잘못된 인증번호";
			} else {// 일치하다면 이와 같이 처리
				//console.log('13123');
				console.log("ㅇㅁㄴㅇㅁㅇㄴ "+ type);
				//id/pw찾기인경우
				if (type == "infoHelp") {
					//console.log(opener.document.getElementById("submit1").type)
					opener.document.getElementById("submit1").type = "submit"; //confirm
				}

				// 부모창에게 데이터 전달 - 부모의 태그 id에 접근하여 value 작성
				//opener.document.getElementById("idCheck").value = "true";

				// 부모창 id 입력방지(readonly)
				opener.document.getElementById("ssid").setAttribute("readonly",
						true);
				// 부모창 mail select 선택방지(disabled) 
				opener.document.getElementById("smail").setAttribute(
						"disabled", true);

				// 부모창 인증체크 버튼 remove
				opener.document.getElementById("confirm").remove();
				
				
				alert('인증이 완료되었습니다.');
				window.close();

				//document.getElementById('checkMailCode').style.color = "blue";
				/*alert('인증이 완료되었습니다.\n엔터를 클릭해주세요.');
				var FinishCheck = document.getElementById('FinishCheck');
				FinishCheck.type = "submit";
				console.log(FinishCheck);
				console.log(document.getElementById('form1'));
				console.log(document.form1);
				document.form1.submit();			*/
				//document.getElementById('checkMailCode').innerHTML = "인증이 완료되었습니다";
				//makeReal();
			}
		}
	}
	/*function makeReal() {
		var FinishCheck = document.getElementById('FinishCheck');
		FinishCheck.type = "submit";
	}
	function makeNull() {
		var FinishCheck = document.getElementById('FinishCheck');
		FinishCheck.type = "hidden";
	}*/
</script>
<form action="#" id="checkEmail">
	<!-- 태호님 나중에 CSS 외부 시트로 변경해주세용 ^▽^ -->
	<span id="checkMailCode" class="checkMailset"></span>
	<table>

		<tr>			
			<td><input type="text" name="code" id="code" class="mailCheckset form-control"
				onkeyup="checkCode('${type}')" placeholder="인증번호를 입력하세요."></td>
			<td><input type="hidden" readonly="readonly" name="code_check"
				id="code_check" value="${code}" maxlength="5"></td>
		</tr>
		

	</table>

</form>