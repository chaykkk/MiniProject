/**
 * #으로 가져오면 id // document로 가져오면 name 항목
 */

// 약관 동의여부 확인
function go_next() {
	if(document.formm.okon1[0].checked == true) { // 동의함에 체크
		document.formm.action = "join_form";
		document.formm.submit(); // 컨트롤러로 전송
	} else if (document.formm.okon1[1].checked == true) {
		alert("약관 동의함에 체크해주세요");
	}
}

// 사용자 ID 입력여부 확인 후 아이디 중복확인 윈도우 오픈
function idcheck() {
	
	// 회원가입 화면에 ID 입력여부 확인
	if($("#id").val() == "") {
		alert("아이디를 입력해주세요");
		$("#id").focus();
		return false;
	}
	
	// ID 중복확인 화면 요청
	var url = "id_check_form?id=" + $("#id").val();
	window.open(url, "_blank_", "toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=350, height=250");
}

// 회원가입 입력여부 확인
function go_save() {
	if($("#id").val() == "") {
		alert("아이디를 입력해주세요");
		$("#id").focus();
		return false;
	}
	if($("#reid").val() == "") {
		alert("아이디 중복확인을 해주세요");
		$("#reid").focus();
		return false;
	}
	if($("#pwd").val() == "") {
		alert("비밀번호를 입력해주세요");
		$("#pwd").focus();
		return false;
	}
	if($("#pwdCheck").val() == "") {
		alert("비밀번호를 한번 더 입력해주세요");
		$("#pwdCheck").focus();
		return false;
	}
	if($("#pwd").val() != $("#pwdCheck").val()) {
		alert("비밀번호가 일치하지 않습니다");
		$("#pwdCheck").focus();
		return false;
	}
	if($("#name").val() == "") {
		alert("이름을 입력해주세요");
		$("#name").focus();
		return false;
	}
	if($("#email").val() == "") {
		alert("이메일을 입력해주세요");
		$("#email").focus();
		return false;
	} else {
		$("#join").action = "join";
		$("#join").submit();
	}
}

// 우편번호 찾기 화면 요청
function post_zip() {
	var url = "find_zip_num";
	
	window.open(url, "_blank_", "toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=700, height=500");	
}

// 아이디 찾기 화면 요청 
function find_id_form() {
	var url = "find_id_form";
	
	window.open(url, "_blank_", "toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=600, height=500");
}

// 아이디 찾기 요청
function findMemberId() {
	if($("#name").val() == "") {
		alert("이름을 입력해주세요");
		$("#name").focus();
		return false;
	} else if ($("#email").val() == "") {
		alert("이메일을 입력해주세요");
		$("#email").focus();
		return false;
	} else {
		var form = $("#findId")
		form.action = "find_id"; // 컨트롤러로 요청 URL 설정
		form.submit();
	}
}

// 비밀번호 찾기 요청
function findPassword() {
	if($("#id2").val() == "") {
		alert("아이디를 입력해주세요");
		$("#id2").focus();
		return false;
	} else if ($("#name2").val() == "") {
		alert("이름을 입력해주세요");
		$("#name2").focus();
		return false;
	} else if ($("#email2").val() == "") {
		alert("이메일을 입력해주세요");
		$("#email2").focus();
		return false;
	} else {
		$("#findPW").action = "find_pwd"; // 비밀번호 찾기 URL 설정
		$("#findPW").submit();
	}
}

// 비밀번호 변경 
function changePassword() {
	if($("#pwd").val() == "") {
		alert("비밀번호를 입력해주세요");
		$("#pwd").focus();
		return false;
	} else if($("#pwdcheck").val() == "") {
		alert("비밀번호를 한번 더 입력해주세요");
		$("#pwdcheck").focus();
		return false;
	} else if($("#pwd").val() != $("#pwdcheck").val()) {
		alert("비밀번호가 일치하지 않습니다");
		$("#pwdcheck").focus();
		return false;
	} else {
		$("#pwd_form").action = "change_pwd"; // 비밀번호 변경 URL 설정
		$("#pwd_form").submit();
	}
}