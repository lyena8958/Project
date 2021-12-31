window.onload = function(){
	actRemove();
	var loginSignUp = $('#loginSignUp');
	console.log(loginSignUp);
	loginSignUp.addClass("active");
	
//	form의 name(signUp)으로 → 내부 데이터를 변수에 가져옴 
	var join = document.join;
	// console.log(signUp); 

//	[변수 셋팅]
	// 오류체크 대상
	var input = document.querySelectorAll('.check');

	// 오류ID -> 오류문구 표시할 곳
	var errorId = ["id", "pw", "pwCheck", "name"];

	// 정규식 모음 		ID(5~15자 a~z, A~Z, 0~9, -, _ )		PW(10~20자 a~z, A~Z, 0~9,~!@#$%^&*()_-)	NAME(3~10자)
	//var RegExp = [/^[a-zA-Z0-9-_]{5,15}$/, /^[a-zA-Z0-9~!@#$%^&*()_-]{10,20}/, /^[.]{1,20}$/];

	// 에러문구
	var errorStr = ["5~15자의 영문, 숫자와 특수기호(-),(_)만 사용 가능합니다.", "10~20자의 영문, 숫자와 특수기호 ~!@#$%^&*()_-만 사용 가능합니다.", "1~20자 입력 가능합니다."];

	// 오류ID 구간 전체 불러오기 -> innerReset함수 활용변수
	var error = document.querySelectorAll('.signupt > span');


//-------------------------------------------------------------------------------------------
	
	//console.log(input);
	//console.log(error);

//	오류문구 초기화 메서드
	function innerReset(error){
		for(var i = 0; i < error.length; i++){
			error[i].innerHTML = "";
		}
	}
	for(var i = 0; i < error.length; i++){
		console.log(input[i]);
	}
	/*
//	에러처리 함수
	function writingError(index){
		
		if (!RegExp[index].test(input[index].value)) { 
            document.getElementById(errorId[index]+"Error").innerHTML = errorStr[index];
        }
	}

	innerReset(error); 
	
	// onkeydown이벤트 및 에러함수 호출 
	join.id.onkeydown = writingError(0);
	join.pw.onkeydown = writingError(1);
	join.name.onkeydown = writingError(3);
*/
	innerReset(error);
	
	 join.id.onkeydown = function(){
		 innerReset(error);// 오류문구 초기화
         var idLimit = /^[a-zA-Z0-9-_]{5,15}$/; //정규식(a~z, A~Z, 0~9, -, _만 입력가능)
         
         if (!idLimit.test(input[0].value)) {
            document.getElementById(errorId[0]+"Error").innerHTML = errorStr[0];
         }
         
	 }
	
	 join.pw.onkeydown = function(){
		 innerReset(error);// 오류문구 초기화
		 var pwLimit = /^[a-zA-Z0-9~!@#$%^&*()_-]{10,20}/;
         
         if (!pwLimit.test(input[1].value)) {
            document.getElementById(errorId[1]+"Error").innerHTML = errorStr[1];
         }
         
	 }
	 
	 join.name.onkeydown = function(){
		 innerReset(error);// 오류문구 초기화
		 var nameLimit = /^[.]{1,20}$/;
         
         if (!nameLimit.test(input[3].value)) {
            document.getElementById(errorId[3]+"Error").innerHTML = errorStr[2];
         }
         
	 }
	 

	 
	 
	 
	//-------------------------------------------------------------------------------------------


//	submit시 오류 동작
	join.onsubmit = function() {
		 var idLimit = /^[a-zA-Z0-9-_]{5,15}$/; //정규식(a~z, A~Z, 0~9, -, _만 입력가능)
		 var pwLimit = /^[a-zA-Z0-9~!@#$%^&*()_-]{10,20}/;
		 var nameLimit = /^.{1,20}$/;
		 
		// 오류문구 초기화
		innerReset(error);
		
		  // input 값 비어짐 여부 확인
        for (var i = 0; i < input.length - 1; i++) { // -1 == submit제외 
           var nullStr = [ " 아이디를", " 비밀번호를", " 비밀번호 확인을", " 성함을"];
           if (!input[i].value) {
              document.getElementById(errorId[i]+"Error").innerHTML = nullStr[i]+ " 입력해 주세요.";
              input[i].focus(); // 포커스 이동
              return false; // 종료 (포커스 이동유지를 위해 false 종료)
              break;
           }
        }   
		
/*
		// 오류확인
		for(var i = 0; i < errorId.length; i++){
			// [오류 체크] pwCheck
			if(errorId[i] == 'pwCheckError'){
				if(join.pw.value != join.pwCheck.value){ // pw와 같지않다면 에러문구 추가
					document.getElementById(errorId[i]+"Error").innerHTML = "비밀번호가 일치하지 않습니다.";
					input[i].focus(); // 포커스 이동
					return false; // 종료 (포커스 이동유지를 위해 false 종료)
				}
			}// if문


			// [오류 체크] 전체(id, pw, name)
			if(!RegExp.test(input[i].value)){
				document.getElementById(errorId[i]+"Error").innerHTML = errorStr[i];
				input[i].focus(); // 포커스 이동
				return false; // 종료 (포커스 이동유지를 위해 false 종료)
			}	
		}//for
*/
		 // id
		if (!idLimit.test(input[0].value)) {
            document.getElementById(errorId[0]+"Error").innerHTML = errorStr[0];
            join.id.focus(); // 포커스 이동
            return false;
         }
		 // pw
		 if (!pwLimit.test(input[1].value)) {
            document.getElementById(errorId[1]+"Error").innerHTML = errorStr[1];
            join.pw.focus(); // 포커스 이동
            return false;
         }
         // pwCheck
         if (join.pw.value != join.pwCheck.value) {
            document.getElementById("pwCheckError").innerHTML = "비밀번호가 일치하지 않습니다.";
            join.pwCheck.focus(); // 포커스 이동
            return false;
         }
		 // 이름
	 	 if (!nameLimit.test(input[3].value)) { 
            document.getElementById(errorId[3]+"Error").innerHTML = errorStr[2];
            join.name.focus(); // 포커스 이동
            return false;
         }
		// 개인정보동의
	 	 if(!document.getElementById("check").checked){
	 		document.getElementById("consentError").innerHTML = "개인정보 수집이용 동의를 해주세요.";
            return false;
	 	 }
	}// join.submit
}