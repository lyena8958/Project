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

