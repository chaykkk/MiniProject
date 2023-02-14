package com.ezen.biz.service;

import java.util.List;

import com.ezen.biz.dto.CartVO;

public interface CartService {

	// 장바구니 담기
	void insertCart(CartVO vo);

	// 장바구니 목록 조회
	public List<CartVO> getCartList(String id);
	
	// 장바구니 취소
	void deleteCart(int cseq);

	// 장바구니 항목 처리결과(result) 업데이트
	public void updateCart(int cseq);
}