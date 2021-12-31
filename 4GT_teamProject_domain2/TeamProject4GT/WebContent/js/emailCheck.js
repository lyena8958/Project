function checkID(id, mail, type) { // 회원 가입 시 ID 중복 체크하는 함수
	console.log(id);
	console.log(mail);
	/* 		var id = document.getElementById("sid").value;
		var mail = document.getElementById("smail").value; */
	$.ajax({ 
		// [요청 데이터 경로]
		type: "GET", // 단순 정보 조회 시에는 GET, 정보가 너무 많거나 insert/update를 할때는 POST
		url: "checkID.ucdo",	 // "checkID.ucdo?id="+id+"&mail="+mail,
		data:{// 위 컨트롤에 데이터 전송
			id : $("#ssid").val(),
			mail : $("#smail").val()
		},
		success: function(data) { 
			var uri = "codeSend.ucdo?id="+id.value+"&mail="+mail.value+"&type="+type;
			//console.log('adasdasd '+data.trim());
			//console.log(data.trim()=="false");

			if(type == 'signUp'){
				if (data.trim()=="false") { // 중복 데이터가 없을 때, trim():문자열 공백제거
					alert("사용 가능한 ID입니다.\n잠시후 이메일 인증 팝업창이 실행됩니다.");
				} else {
					alert("존재하는 ID입니다.");
					return;
				}
			}
			else if(type == 'infoHelp'){
				if (data.trim()=="true") { // 중복 데이터가 없을 때, trim():문자열 공백제거
					alert("이메일 확인이 완료되었습니다.\n잠시후 이메일 인증 팝업창이 실행됩니다.");
				} else {
					alert("존재하지 않은 이메일입니다.");
					return;
				}
			}
			
			window.open(uri, '인증처리 페이지', 'width=800, height=700');
		},
		error: function(xhr) {
			console.log(xhr.status + " : " + xhr.errorText);
			console.log("uri : " + uri);
			alert("에러발생!");
		}
	});
}

function emptyID(type){
	var id = document.getElementById("ssid")
	var mail = document.getElementById("smail")

	// id를 입력하지 않았거나, 이메일을 선택하지 않았다면 alert창 띄움
	if (!id.value || mail.value == "이메일 선택") {
		console.log(mail.value);
		alert('아이디 혹은 이메일을 입력해 주세요.');
		id.focus(); // 포커스 이동
		return false;
	}
	else{// 입력되면 id중복확인
		checkID(id, mail, type); // 함수호출
	}

}
function disabledRemove(){
	document.getElementById("smail").removeAttribute('disabled');
}


