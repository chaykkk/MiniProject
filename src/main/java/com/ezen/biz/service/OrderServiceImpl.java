package com.ezen.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.OrderDAO;
import com.ezen.biz.dto.CartVO;
import com.ezen.biz.dto.OrderVO;
import com.ezen.biz.dto.SalesQuantity;

import utils.Criteria;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDAO orderDao;
	@Autowired
	private CartService cartService;
	
	@Override
	public int selectMaxOseq() {
		return orderDao.selectMaxOseq();
	}

	@Override
	public int insertOrder(OrderVO vo) {
		
		// (1) 신규 주문번호 할당
		int oseq = selectMaxOseq(); 
		vo.setOseq(oseq);
		
		// (2) 신규 주문을 주문테이블에 저장
		orderDao.insertOrder(vo); 
		
		
		// (3) 장바구니 목록을 읽어서 order_detail 테이블에 저장
		List<CartVO> listCart = cartService.getCartList(vo.getId());
		
		for(CartVO cart : listCart) {
			OrderVO order = new OrderVO();
			
			order.setOseq(oseq);
			order.setPseq(oseq);
			order.setQuantity(cart.getQuantity());
			
			insertOrderDetail(order);
			
			// 장바구니 테이블 업데이트(처리결과를 '처리완료'로)
			cartService.updateCart(cart.getCseq());
		}
		
		return oseq;
	}

	@Override
	public void insertOrderDetail(OrderVO vo) {
		orderDao.insertOrderDetail(vo);
	}

	@Override
	public List<OrderVO> getListOrderById(OrderVO vo) {
		return orderDao.listOrderById(vo);
	}

	@Override
	public List<Integer> getSeqOrdering(OrderVO vo) {
		return orderDao.selectSeqOrdering(vo);
	}

	// 전체 주문목록 조회
	@Override
	public List<OrderVO> getListOrder(String mname) {
		return orderDao.listOrder(mname);
	}
	
	// 주문완료 처리
	@Override
	public void updateOrderResult(int odseq) {
		orderDao.updateOrderResult(odseq);
	}

	// 페이지별 주문 목록 조회
	@Override
	public List<OrderVO> getListOrderWithPaging(String mname) {
		return orderDao.listOrderWithPaging(mname);
	}

	// 총 주문 목록의 개수 조회
	@Override
	public int countOrderList(String mname) {
		return orderDao.countOrderList(mname);
	}

	// 제품 판매 실적 조회
	@Override
	public List<SalesQuantity> getProductSales() {
		return orderDao.getProductSales();
	}

}
