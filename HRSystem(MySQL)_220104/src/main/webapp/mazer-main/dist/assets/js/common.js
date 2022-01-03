

function userDelete(hnum){
	console.log(hnum);
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

//profile
function profileDownload() {
	var boxs = document.querySelectorAll(".custom-checkbox > input");
	console.log(boxs);
	if (!(boxs[0].checked || boxs[1].checked || boxs[2].checked || boxs[3].checked)) {
		alert('최소 한개 이상 선택해 주세요.');
		return false;
	}
	// 경로 지정시 window.open("profile.do?career="+boxs[1].checked+"&school="+boxs[2].checked+"&license="+boxs[3].checked, "_blank", "width=500,height=600");
	
	// 경로 지정안하고 즉시 다운로드 시  
	location.href="profile.do?career="+boxs[1].checked+"&school="+boxs[2].checked+"&license="+boxs[3].checked;
}
function showMem(){
	var nameSearch = /^=[가-힣]$/;
	var numSearch = /[0-9]/;
	
	var search = document.getElementById("basicInput").value;
	console.log(search);
	console.log(nameSearch.test(search));
	console.log(numSearch.test(search));
	if(nameSearch.test(search)){
		window.open('showMem.do?text='+search,'_blank','width=500,height=600');
	}else if(numSearch.test(search)){
		location.href= "showMem.do?text="+search;
	}else{
		alert('잘못된 검색입니다.'); 
	}
	
	
}
function searchMove(){
	//console.log($("input[name='text']:checked").val());
	opener.location.href="showMem.do?text="+$("input[name='text']:checked").val();
	window.close();
	
}
//insertPost.jsp
window.onload = function(){
	var postForm = document.postForm;

	postForm.onsubmit = function(){
		// 사번 인증 유효성검사
		console.log(postForm.name.value=='');
		if(postForm.name.value==''){
			alert('확인 버튼을 통해 사번 인증을 해주세요.');
			return false;
		}
		if(postForm.category.value=="직접선택"){
			alert('카테고리를 선택해 주세요.');
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
	var mnum = document.postForm.pmem;

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
			//console.log("데이터전송완1 : "+name[0]);
			//console.log("데이터전송완 : "+args);
			document.postForm.name.setAttribute('value', "완료");
			
			//document.postForm.name.setAttribute('value', args);
		},
		error: function(xhr) {
			console.log(xhr);
			alert('존재하지 않는 사번입니다.');
		}
	})

}


// show_Post
function resultSelColor(){
	var resultSel = document.postUForm.result;
	console.log("확인");
	
	if(resultSel.value=="진행중"){
		resultSel.style.color = "red";
	}
	else if(resultSel.value=="종결"){
		resultSel.style.color = "#00cf00";
	}			
}


