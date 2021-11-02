function userDelete(hnum){
	result = confirm("관리자 권한을 정말 해제하시겠습니까?");
	if(result==true){
		location.href="deleteHRAdmin.do?hnum="+hnum;
	}
	return;
}
function infoDelete(text, uri){
	result = confirm(text);
	if(result==true){
		location.href=uri;
	}
	return;
}
function edit(){
	var datas = document.querySelectorAll(".edit");

	for(var i = 0; i < datas.length; i++){
		datas[i].removeAttribute("readonly");
	}
	document.getElementById("infoText").innerHTML = "수정";
	document.getElementById("update").type ="submit";
	document.getElementById("delete").type ="button";
}

//insertPost.jsp
window.onload = function(){
	var post = document.postForm;
	signUp.onsubmit = function(){

		var pw = signUp.pw;
		var pwCheck = signUp.pwCheck;

		if(signUp.pw.value!=pwCheck.value){
			alert('비밀번호가 일치하지 않습니다.')
			return false;
		}
	}


}

//insertPost.jsp
window.onload = function(){
	var postForm = document.postForm;
	
	postForm.onsubmit = function(){
		
		// 사번 인증 유효성검사
		console.log("name value : "+postForm.name.value);
		if(postForm.name.value==null){
			alert('확인 버튼을 통해 사번 인증을 해주세요.');
			return false;
		}
		
		if(postForm.password.value.length<4){
			alert('비밀번호 4자리 입력해 주세요.');
			return false;
		}
		
	}
}

//insertPost.jsp
function mnumCheck(){
	var mnum = document.postForm.pnum;

	if(mnum.value.length==0){
		alert('사번을 입력해 주세요.');
		return;
	}

	$.ajax({
		type:"post",
		url:"getMem.do",
		data : "mnum="+mnum.value,
		dataType: "json",
		success:function(args){
			console.log("데이터전송완 : "+args[0].name);
			console.log("데이터전송완 : "+args[0]);
			document.postForm.name.setAttribute('value', args[0].name);
		},
		error: function(xhr) {
			console.log(xhr);
			alert('존재하지 않는 사번입니다.');
		}
	})

}
//signUp_HRAdmin
window.onload = function(){
	var signUp = document.signUpForm;
	signUp.onsubmit = function(){

		var pw = signUp.pw;
		var pwCheck = signUp.pwCheck;

		if(signUp.pw.value!=pwCheck.value){
			alert('비밀번호가 일치하지 않습니다.')
			return false;
		}
	}


}

