/**
 *  상품등록화면 표시 요청
 */
function go_wrt() {
	$("#prod_form").attr("action", "admin_product_write_form");
	$("#prod_form").submit();
}

// price3(순익) 필드 입력 : price2(판매가) - price1(원가)를 계산하여 입력
function go_ab() {
	var price2 = $("#price2").val().replace(/,/g, ''); // replace() -> ,를 빈 문자열로 대치
	var price1 = $("#price1").val().replace(/,/g, '');
	var result = price2 - price1;
	
	$("#price3").val(result);
}

// 숫자 데이터에서 3자리마다 ,를 찍어주기
function NumFormat(t) {
 
    num = t.value;
    // 숫자 이외의 문자 제거
    num = num.replace(/\D/g, '');
    // 숫자 3자리 마다 콤마 제거
    len = num.length - 3;
    while (len > 0) {
        num = num.substr(0, len) + "," + num.substr(len);
        len -= 3;
    }
   
    t.value = num;
   
    return t;
}


// 상품 조건 검색
function go_search() {
	$("#prod_form").attr("action", "admin_product_list");
	$("#prod_form").submit();
}

// 상품등록 입력항목 체크 및 URL 요청
function go_save() {
	if ($("kind").val() == "") {
		alert("상품분류를 등록해주세요");
		$("#kind").focus();
		return false;
	}
	if ($("#name").val() == "") {
		alert("상품명을 등록해주세요");
		$("#name").focus();
		return false;
	}
	if ($("#price1").val() == "") {
		alert("원가를 등록해주세요");
		$("#price1").focus();
		return false;
	}
	if ($("#price2").val() == "") {
		alert("판매가를 등록해주세요");
		$("#price2").focus();
		return false;
	}
	if ($("#price3").val() == "") {
		alert("순익을 등록해주세요");
		$("#price3").focus();
		return false;
	}
	if ($("#content").val() == "") {
		alert("상세설명을 등록해주세요");
		$("#content").focus();
		return false;
	} 
	if ($("#product_image").val() == "") {
		alert("상품이미지를 등록해주세요");
		$("#product_image").focus();
		return false;
	} else {
		// price1, price2, price3에서 콤마 제거
		var price1 = document.getElementById("price1");
		var price2 = document.getElementById("price2");
		var price3 = document.getElementById("price3");
		
		price1.value = remove_comma(price1);
		price2.value = remove_comma(price2);
		price3.value = remove_comma(price3);
	
		$("#write_form").attr("enctype", "multipart/form-data");
		$("#write_form").attr("action", "admin_product_write");
		$("#write_form").submit();
	}
}

// 콤마 제거
function remove_comma(input) {
	return input.value.replace(/,/g, '');
}

// 상품 상세보기 요청
function go_detail(pseq) {
	$("#prod_form").attr("action", "admin_product_detail?pseq=" + pseq).submit();
}

// 상품 수정화면 요청
function go_mod(pseq) {
	$("#detail_form").attr("action", "admin_product_update_form?pseq=" + pseq).submit();
}

// 상품 수정 실행
function go_mod_save(pseq) {
	if ($("kind").val() == "") {
		alert("상품분류를 등록해주세요");
		$("#kind").focus();
		return false;
	}
	if ($("#name").val() == "") {
		alert("상품명을 등록해주세요");
		$("#name").focus();
		return false;
	}
	if ($("#price1").val() == "") {
		alert("원가를 등록해주세요");
		$("#price1").focus();
		return false;
	}
	if ($("#price2").val() == "") {
		alert("판매가를 등록해주세요");
		$("#price2").focus();
		return false;
	}
	if ($("#price3").val() == "") {
		alert("순익을 등록해주세요");
		$("#price3").focus();
		return false;
	}
	if ($("#content").val() == "") {
		alert("상세설명을 등록해주세요");
		$("#content").focus();
		return false;
	} 
	if ($("#product_image").val() == "") {
		alert("상품이미지를 등록해주세요");
		$("#product_image").focus();
		return false;
	} else {
		// price1, price2, price3에서 콤마 제거
		var price1 = document.getElementById("price1");
		var price2 = document.getElementById("price2");
		var price3 = document.getElementById("price3");
		
		price1.value = remove_comma(price1);
		price2.value = remove_comma(price2);
		price3.value = remove_comma(price3);
	
		$("#update_form").attr("enctype", "multipart/form-data");
		$("#update_form").attr("action", "admin_product_update?pseq=" + pseq).submit();
	}
}

// 상세보기에서 목록으로 이동
function go_list() {
	$("#detail_form").attr("action", "admin_product_list").submit();
}

// 상품목록에서 전체보기 이동
function go_total() {
	$("#key").val("");
	$("#prod_form").attr("action", "admin_product_list").submit();
}