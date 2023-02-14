/**
 *  장바구니에 담기 요청 전송
 */
function go_cart() {
	if ($("#quantity").val() == "") {
		alert("수량을 입력해주세요");
		$("#quantity").focus();
		return false;
	} else if ($("#quantity").val() > 30) {
		alert("수량이 너무 많습니다");
		$("#quantity").focus();
		return false;
	} else {
		$("#theform").action = "cart_insert";
		$("#theform").submit();
	}
}

// 장바구니 상품 삭제
function go_cart_delete() {
   var count = 0; // 체크된 항목 수
   var len = document.formm.cseq.length;
   
   // 삭제할 항목이 하나인 경우
   if (len == undefined) {
      if (document.formm.cseq.checked == true) {
         count++;
      }
   }
   
   for(var i=0; i<len; i++) {
      if (document.formm.cseq[i].checked == true) {
         count++;
      }
   }
   
   if(count == 0){
      alert("삭제할 항목을 선택해주세요");
   } else{
	  document.getElementById("theform").action = "cart_delete";
	  document.getElementById("theform").submit();
   
   }
}

// 장바구니 내역을 주문 정보에 저장 요청
function go_order_insert() {
	document.getElementById("theform").action = "order_insert";
	document.getElementById("theform").submit();
	//document.formm.action = "order_insert";
	//document.formm.submit();
}