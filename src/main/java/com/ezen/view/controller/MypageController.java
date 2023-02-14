package com.ezen.view.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.biz.dto.CartVO;
import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.dto.OrderVO;
import com.ezen.biz.service.CartService;
import com.ezen.biz.service.OrderService;

@Controller
public class MypageController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
	// 장바구니 담기
	@PostMapping(value="/cart_insert")
	public String insertCart(CartVO vo, HttpSession session) {
		// 세션에 사용자 정보가 저장되어 있는지 확인(로그인 여부)
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) { // 로그인이 안되어 있는 경우
			return "member/login";
		} else {
			vo.setId(loginUser.getId());
			cartService.insertCart(vo);
			
			return "redirect:cart_list";
		}
	}
	
	// 장바구니 목록 조회
	@GetMapping(value="/cart_list")
	public String listCart(HttpSession session, Model model) {
		// 로그인 확인 여부
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "member/login";
		} else {
			List<CartVO> cartList = cartService.getCartList(loginUser.getId());
			// 장바구니 총액 계산
			int totalAmount = 0;
			for(CartVO vo : cartList) {
				totalAmount += vo.getQuantity() * vo.getPrice2();
			}
			
			model.addAttribute("cartList", cartList);
			model.addAttribute("totalPrice", totalAmount);
			
			return "mypage/cartList";
		}
	}
	
	// 장바구니 삭제
	@PostMapping(value="/cart_delete")
	public String deleteCart(@RequestParam(value="cseq") int[] cseq) {
		
		for(int i=0; i<cseq.length; i++) {
			cartService.deleteCart(cseq[i]);
		}
		
		return "redirect:cart_list";
	}
	
	// 주문처리
	@PostMapping(value="/order_insert")
	public String orderInsert(OrderVO order, HttpSession session, Model model) {
		// 로그인 확인 여부
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "member/login";
		} else {
			// order 객체에 사용자 ID 설정
			order.setId(loginUser.getId());
			
			int oseq = orderService.insertOrder(order);
			model.addAttribute("oseq", oseq);
			
			return "redirect:order_list";
		}
	}
	
	// 사용자별 주문내역 조회
	@RequestMapping(value="order_list")
	public String orderListAction(HttpSession session, OrderVO order, Model model,
						@RequestParam(value="oseq") int oseq) {
		// 로그인 확인 여부
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "member/login";
		} else {
			// 진행중인 주문내역 조회
			order.setId(loginUser.getId());
			order.setOseq(oseq);
			order.setResult("1"); // 처리결과: 배송전
			List<OrderVO> orderList = orderService.getListOrderById(order);
			
			// 주문총액 계산
			int totalAmount = 0;
			for(OrderVO vo : orderList) {
				totalAmount += vo.getQuantity() * vo.getPrice2();
			}
			
			model.addAttribute("orderList", orderList);
			model.addAttribute("totalPrice", totalAmount);
			
			return "mypage/orderList";
		}
	}
	
	// 사용자 미처리 주문내역 요약정보 조회
	@RequestMapping(value="/mypage")
	public String myPageView(HttpSession session, OrderVO vo, Model model) {
		// 로그인 확인 여부
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "member/login";
		} else {
			// (1) 사용자의 주문번호 목록 조회
			vo.setId(loginUser.getId());
			vo.setResult("1"); // 처리결과: 미처리
			List<Integer> oseqList = orderService.getSeqOrdering(vo);
			
			// (2) 각 주문번호에 대해 주문목록 조회 및 요약정보 생성
			List<OrderVO> summaryList = new ArrayList<OrderVO>(); // 주문요약정보 저장
			for(int oseq : oseqList) {
				OrderVO order = new OrderVO();
				
				// 각 주문번호의 주문내역 조회
				order.setId(loginUser.getId());
				order.setOseq(oseq);
				order.setResult("1"); // 처리결과: 배송전
				List<OrderVO> orderList = orderService.getListOrderById(order);
				
				// 각 주문정보의 요약 생성
				OrderVO summary = new OrderVO();
				summary.setOseq(orderList.get(0).getOseq()); // 주문번호
				summary.setIndate(orderList.get(0).getIndate()); // 주문일자
				
				// 상품명 요약
				if (orderList.size() >= 2) {
					summary.setPname(orderList.get(0).getPname() + " 외" +
							(orderList.size() -1) + "건");
				} else {
					summary.setPname(orderList.get(0).getPname());
				}
				
				// 각 주문별 합계금액
				int amount = 0;
				for(int i=0; i<orderList.size(); i++) {
					amount += orderList.get(i).getQuantity() * orderList.get(i).getPrice2();
				}
				summary.setPrice2(amount);
				
				// 요약정보를 리스트에 추가
				summaryList.add(summary);
			}
			
			// (3) 주문정보를 화면에 전달
			model.addAttribute("title", "진행중인 주문내역");
			model.addAttribute("orderList", summaryList);
		}
		
		return "mypage/mypage";
	}
	
	// 주문내역에서 주문상세 정보 조회
	@RequestMapping(value="/order_detail")
	public String orderDetail(OrderVO vo, HttpSession session, Model model) {
		// 로그인 확인 여부
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "member/login";
		} else {
			// (0) 주문번호별 주문목록 조회
			vo.setId(loginUser.getId());
			vo.setResult("");
			List<OrderVO> orderList = orderService.getListOrderById(vo);
			
			// (1) 주문자 정보 생성
			OrderVO orderDetail = new OrderVO();
			orderDetail.setIndate(orderList.get(0).getIndate());
			orderDetail.setOseq(orderList.get(0).getOseq());
			orderDetail.setMname(orderList.get(0).getMname());
			
			// (2) 주문 총액 계산
			int totalAmount = 0;
			for(int i=0; i<orderList.size(); i++) {
				totalAmount += orderList.get(i).getQuantity() * orderList.get(i).getPrice2();
			}
			
			// (3) 화면에 출력할 정보 저장
			model.addAttribute("title", "My page(주문 상세 정보)");
			model.addAttribute("orderDetail", orderDetail);
			model.addAttribute("totalPrice", totalAmount);
			model.addAttribute("orderList", orderList);
			
			return "mypage/orderDetail";
		}
	}
	
	// 총 주문내역 보기
	@RequestMapping(value="/order_all")
	public String orderAllView(OrderVO vo, HttpSession session, Model model) {
		// 로그인 확인 여부
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			// (1) 사용자의 전체 주문번호 확인
			vo.setId(loginUser.getId());
			vo.setResult(""); // 처리결과: 모든 result
			List<Integer> oseqList = orderService.getSeqOrdering(vo);
			
			// (2) 각 주문번호에 대해 주문목록 조회 및 요약정보 생성
			List<OrderVO> summaryList = new ArrayList<OrderVO>(); // 주문요약정보 저장
			for(int oseq : oseqList) {
				OrderVO order = new OrderVO();
				
				// 각 주문번호의 주문내역 조회
				order.setId(loginUser.getId());
				order.setOseq(oseq);
				order.setResult(""); // 처리결과: 모든 result
				List<OrderVO> orderList = orderService.getListOrderById(order);
				
				// 각 주문정보의 요약 생성
				OrderVO summary = new OrderVO();
				summary.setOseq(orderList.get(0).getOseq()); // 주문번호
				summary.setIndate(orderList.get(0).getIndate()); // 주문일자
				
				// 상품명 요약
				if (orderList.size() >= 2) {
					summary.setPname(orderList.get(0).getPname() + " 외" +
							(orderList.size() -1) + "건");
				} else {
					summary.setPname(orderList.get(0).getPname());
				}
				
				// 각 주문별 합계금액
				int amount = 0;
				for(int i=0; i<orderList.size(); i++) {
					amount += orderList.get(i).getQuantity() * orderList.get(i).getPrice2();
				}
				summary.setPrice2(amount);
				
				// 요약정보를 리스트에 추가
				summaryList.add(summary);
			}
			
			// 결과를 화면에 전달
			model.addAttribute("title", "Mypage(총 주문내역)");
			model.addAttribute("orderList", summaryList);
			
			return "mypage/mypage";
		}
	}
	
}
