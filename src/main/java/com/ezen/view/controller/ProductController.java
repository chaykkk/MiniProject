package com.ezen.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ezen.biz.dto.ProductVO;
import com.ezen.biz.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	// 상품 상세정보 조회
	@RequestMapping(value="/product_detail", method=RequestMethod.GET)
	public String productDetailAction(ProductVO vo, Model model) {
		
		// 상품 상세조회(vo에 pseq 포함되어있음)
		ProductVO product = productService.getProduct(vo);
		
		// JSP에 결과 전달
		model.addAttribute("productVO", product);
		
		// JSP 화면 호출
		return "product/productDetail";
	}
	
	// 상품 종류별 상품 목록 조회
	@RequestMapping(value="/category", method=RequestMethod.GET)
	public String productKindAction(ProductVO vo, Model model) {
		List<ProductVO> kindList = productService.getProductListByKind(vo.getKind());
		
		model.addAttribute("productKindList", kindList);
		
		return "product/productKind";
	}
}
