package com.ezen.biz.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.biz.dto.CartVO;

@Repository
public class CartDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// 장바구니 담기
	public void insertCart(CartVO vo) {
		mybatis.insert("cartMapper.insertCart", vo);
	}
	
	// 장바구니 목록 조회
	public List<CartVO> viewCart(String id) {
		return mybatis.selectList("cartMapper.viewCart", id);
	}
	
	// 장바구니 취소
	public void deleteCart(int cseq) {
		mybatis.delete("cartMapper.deleteCart", cseq);
	}
	
	// 장바구니 항목 처리결과(result) 업데이트
	public void updateCart(int cseq) {
		mybatis.update("cartMapper.updateCart", cseq);
	}
}
