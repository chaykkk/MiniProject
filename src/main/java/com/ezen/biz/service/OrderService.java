package com.ezen.biz.service;

import java.util.List;

import com.ezen.biz.dto.OrderVO;
import com.ezen.biz.dto.SalesQuantity;

import utils.Criteria;

public interface OrderService {

	// 주문 테이블에서 새로 저장할 주문번호 생성
	int selectMaxOseq();

	// 주문 저장 후, 주문번호를 반환한다 -> order_detail에 사용
	int insertOrder(OrderVO vo);

	// 주문 상세 정보 생성
	void insertOrderDetail(OrderVO vo);
	
	// 사용자별 주문 내역 조회	
	public List<OrderVO> getListOrderById(OrderVO vo);
	
	// 사용자별 미처리 주문번호 목록 조회
	public List<Integer> getSeqOrdering(OrderVO vo);
	
	// 주문 전체 조회
	public List<OrderVO> getListOrder(String mname);
	
	// 사용자의 입금처리 후 주문 상태 갱신
	public void updateOrderResult(int odseq);
	
	// 페이지별 주문 목록 조회
	List<OrderVO> getListOrderWithPaging(String mname);
	
	// 총 주문 목록의 개수 조회
	public int countOrderList(String mname);
	
	// 제품 판매 실적 조회
	public List<SalesQuantity> getProductSales();
}