package com.ezen.view.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.biz.dto.AdminVO;
import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.dto.OrderVO;
import com.ezen.biz.dto.ProductVO;
import com.ezen.biz.dto.QnaVO;
import com.ezen.biz.dto.SalesQuantity;
import com.ezen.biz.service.AdminService;
import com.ezen.biz.service.MemberService;
import com.ezen.biz.service.OrderService;
import com.ezen.biz.service.ProductService;
import com.ezen.biz.service.QnaService;

import utils.Criteria;
import utils.PageMaker;

@Controller
@SessionAttributes("admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private QnaService qnaService;
	
	// 관리자 로그인 화면 표시
	@GetMapping(value="/admin_login_form")
	public String adminLoginView() {
		
		return "admin/main";
	}
	
	// 관리자 로그인 처리
	@PostMapping(value="/admin_login")
	public String adminLogin(AdminVO vo, Model model) {
		// (1) 관리자 ID 인증
		int result = adminService.adminCheck(vo);
		
		// (2) 정상관리자이면
		//     -- 관리자 정보 조회
		//     -- 상품목록 화면으로 이동
		if (result == 1) {
			model.addAttribute("admin", adminService.getAdmin(vo.getId()));
			
			return "redirect:admin_product_list";
		} else {
		
			// (3) 비정상관리자이면
			//     -- 메시지를 설정하고 로그인 화면으로 이동
			if (result == 0) {
				model.addAttribute("message", "비밀번호를 확인해주세요");
			} else {
				model.addAttribute("message", "아이디를 확인해주세요");
			}
		
			return "admin/main";
		}
	}
	
	// 관리자 로그아웃
	@RequestMapping(value="/admin_logout")
	public String adminLogout(SessionStatus status) {
		status.setComplete();
		return "admin/main";
	}
	
	/* 페이징 처리 전 소스
	// 상품 목록 표시
	@RequestMapping(value="admin_product_list")
	public String adminProductList(
			@RequestParam(value="key", defaultValue="") String name, // 상품명으로 검색
			Model model) {
		// (1) 전체 상품목록 표시
		List<ProductVO> prodList = productService.getlistProduct(name);
		
		// (2) 내장 객체에 상품 목록 저장
		model.addAttribute("productList", prodList);
		model.addAttribute("productListSize", prodList.size());
		
		// (3) 화면 호출
		return "admin/product/productList";	
	}
	*/
	
	// 상품 목록 표시
	@RequestMapping(value="admin_product_list")
	public String adminProductList(
			@RequestParam(value="key", defaultValue="") String name, // 상품명으로 검색
			Criteria criteria, Model model) {
		
		// (1) 페이지별 상품 목록 조회
		List<ProductVO> prodList = productService.getListProductWithPaging(criteria, name);
		
		// (2) 화면에 표시할 페이지 버튼 정보 설정(PageMaker 객체 이용)
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(productService.getcountProductList(name));
		
		// (3) 내장 객체에 상품 목록 저장
		model.addAttribute("productList", prodList);
		model.addAttribute("productListSize", prodList.size());
		model.addAttribute("pageMaker", pageMaker);
		
		// (4) 화면 호출
		return "admin/product/productList";	
	}
	
	// 상품 등록 화면 출력
	@PostMapping(value="/admin_product_write_form")
	public String adminProductWriteView(Model model) {
		String kindList[] = {"힐", "부츠", "샌달", "슬리퍼", "스니커즈", "세일"};
		model.addAttribute("kindList", kindList);
		
		return "admin/product/productWrite";
	}
	
	// 상품 등록
	@RequestMapping(value="/admin_product_write")
	public String adminProductWrite(
			@RequestParam(value="product_image") MultipartFile uploadFile,
			ProductVO vo, HttpSession session) {
		
		if (!uploadFile.isEmpty()) {
			String fileName = uploadFile.getOriginalFilename();
			vo.setImage(fileName);
			
			// 이미지 파일을 이동할 실제경로 구하기
			String image_path = session.getServletContext()
								.getRealPath("WEB-INF/resources/product_images/");
			
			try {
				uploadFile.transferTo(new File(image_path + fileName));
			} catch(IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		productService.insertProduct(vo);
		
		return "redirect:admin_product_list";
	}
	
	// 상품 상세보기
	@RequestMapping(value="/admin_product_detail")
	public String adminProductDetail(ProductVO vo, Model model) {
		ProductVO product = productService.getProduct(vo);
		model.addAttribute("productVO", product);
		
		String kindList[] = {"", "힐", "부츠", "샌달", "슬리퍼", "스니커즈", "세일"};
		int index = Integer.parseInt(product.getKind());
		model.addAttribute("kind", kindList[index]);
		
		return "admin/product/productDetail";
	}
	
	// 상품 수정화면 요청
	@RequestMapping(value="/admin_product_update_form")
	public String adminProductUpdateView(ProductVO vo, Model model) {
		ProductVO product = productService.getProduct(vo);
		model.addAttribute("productVO", product);
		
		String kindList[] = {"힐", "부츠", "샌달", "슬리퍼", "스니커즈", "세일"};
		model.addAttribute("kindList", kindList);
		
		return "admin/product/productUpdate";
	}
	
	// 상품 수정 실행
	@PostMapping(value="/admin_product_update")
	public String adminProductUpdate(
			@RequestParam(value="product_image") MultipartFile uploadFile,
			@RequestParam(value="nonmakeImg") String org_image,
			ProductVO vo, HttpSession session) {
		if (!uploadFile.isEmpty()) { // 상품 이미지를 수정한 경우
			String fileName = uploadFile.getOriginalFilename();
			vo.setImage(fileName);
			
			// 이미지 파일을 이동할 실제경로 구하기
			String image_path = session.getServletContext()
								.getRealPath("WEB-INF/resources/product_images/");
			
			try {
				uploadFile.transferTo(new File(image_path + fileName));
			} catch(IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		} else { // 상품의 기존 이미지 사용
			vo.setImage(org_image);
		}
		
		// 베스트 상품, 신규 상품 처리
		if (vo.getUseyn() == null) {
			vo.setUseyn("n");
		} else {
			vo.setUseyn("y");
		}
		
		if (vo.getBestyn() == null) {
			vo.setBestyn("n");
		} else {
			vo.setBestyn("y");
		}
		productService.updateProduct(vo);
		
		return "redirect:admin_product_list";
	}
	
	// 주문리스트 출력
	@RequestMapping(value="admin_order_list")
	public String adminOrderList(
			@RequestParam(value="key", defaultValue="") String mname, Model model) {
		
		List<OrderVO> orderList = orderService.getListOrder(mname);
		model.addAttribute("orderList", orderList);
		
		return "admin/order/orderList";
	}
	
	/*
	// 주문리스트 출력
	@RequestMapping(value="admin_order_list")
	public String adminOrderList(
			@RequestParam(value="key", defaultValue="") String mname, Criteria criteria, Model model) {

		// (1) 페이지별 주문 목록 조회
		List<OrderVO> orderList = orderService.getListOrderWithPaging(criteria, mname);
		
		// (2) 화면에 표시할 페이지 버튼 정보 설정(PageMaker 객체 이용)
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(orderService.countOrderList(mname));
		
		// (3) 내장 객체에 주문 목록 저장
		model.addAttribute("orderList", orderList);
		model.addAttribute("orderListSize", orderList.size());
		model.addAttribute("pageMaker", pageMaker);
		
		// (4) 화면 호출
		return "admin/order/orderList";
	}
	*/
	
	// 주문완료 처리(입금확인)
	// 입력 파라미터 : 입금확인 한 result 필드의 상세주문번호(odseq)의 배열이 전달됨
	@RequestMapping(value="admin_order_save")
	public String adminOrderSave(@RequestParam(value="result") int[] odseq) {
		
		for (int i=0; i<odseq.length; i++) {
			orderService.updateOrderResult(odseq[i]);
		}
		
		return "redirect:admin_order_list";
	}
	
	// 전체 회원 조회
	@RequestMapping(value="admin_member_list")
	public String adminMemberList(
			@RequestParam(value="key", defaultValue="") String name, Model model) {
		
		List<MemberVO> memberList = memberService.listMember(name);
		model.addAttribute("memberList", memberList);
		
		return "admin/member/memberList";
	}
	
	// QnA 전체 조회
	@RequestMapping(value="admin_qna_list")
	public String adminQnaList(Model model) {
		List<QnaVO> qnaList = qnaService.listAllQna();
		model.addAttribute("qnaList", qnaList);
		
		return "admin/qna/qnaList";
	}
	
	// QnA 상세 조회
	@RequestMapping(value="admin_qna_detail")
	public String adminQnaDetail(QnaVO vo, Model model) {
		QnaVO qna = qnaService.getQna(vo.getQseq());
		model.addAttribute("qnaVO", qna);
		
		return "admin/qna/qnaDetail";
	}
	
	// QnA 답변 처리
	@RequestMapping(value="admin_qna_repsave")
	public String adminQnaRepsave(QnaVO vo) {
		qnaService.updateQna(vo);
		
		return "redirect:admin_qna_list";
	}
	
	// 상품별 판매 실적 화면 출력
	@RequestMapping(value="admin_sales_record_form")
	public String adminProductSalesChart() {
		return "admin/order/salesRecords";
	}
	
	// 상품별 판매 실적 조회 및 데이터 전송(JSON 포맷)
	@RequestMapping(value="sales_record_chart")
	@ResponseBody // 데이터 전송
	public List<SalesQuantity> salesRecordChart() {
		List<SalesQuantity> listSales = orderService.getProductSales();
		
		return listSales;
	}
}
