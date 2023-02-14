package com.ezen.biz.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.biz.dto.OrderVO;
import com.ezen.biz.dto.SalesQuantity;

@Repository	
public class OrderDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	// 주문 테이블에서 새로 저장할 주문번호 생성
	public int selectMaxOseq() {
		return mybatis.selectOne("orderMapper.selectMaxOseq");
	}
	
	// 새로운 주문 생성
	public void insertOrder(OrderVO vo) {
		mybatis.insert("orderMapper.insertOrder", vo);
	}
	
	// 주문 상세 정보 생성
	public void insertOrderDetail(OrderVO vo) {
		mybatis.insert("orderMapper.insertOrderDetail", vo);
	}
	
	// 사용자별 주문 내역 조회
	public List<OrderVO> listOrderById(OrderVO vo) {
		return mybatis.selectList("orderMapper.listOrderById", vo);
	}
	
	// 사용자별 미처리 주문번호 목록 조회
	public List<Integer> selectSeqOrdering(OrderVO vo) {
		return mybatis.selectList("orderMapper.selectSeqOrdering", vo);
	}
	
	// 주문 전체 조회
	public List<OrderVO> listOrder(String mname) {
		return mybatis.selectList("orderMapper.listOrder", mname);
	}
	
	// 사용자의 입금처리 후 주문 상태 갱신
	public void updateOrderResult(int odseq) {
		mybatis.update("orderMapper.updateOrderResult", odseq);
	}
	
	// 페이지별 주문 목록 조회
	public List<OrderVO> listOrderWithPaging(String mname) {
		return mybatis.selectList("orderMapper.listOrderWithPaging", mname);
	}
	
	// 총 주문 목록의 개수 조회
	public int countOrderList(String mname) {
		return mybatis.selectOne("orderMapper.countOrderList", mname);
	}
	
	// 제품 판매 실적 조회
	public List<SalesQuantity> getProductSales() {
		return mybatis.selectList("orderMapper.listProductSales");
	}
}
