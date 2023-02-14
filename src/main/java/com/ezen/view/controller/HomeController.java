package com.ezen.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.biz.dto.ProductVO;
import com.ezen.biz.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/index")
	public String home(Model model) {
		
		// 신상품 조회 서비스 호출
		List<ProductVO> newProductList = productService.getNewProductList();
		
		// 베스트상품 조회 서비스 호출
		List<ProductVO> bestProductList = productService.getBestProductList();
		
		model.addAttribute("newProductList", newProductList);
		model.addAttribute("bestProductList", bestProductList);
		
		return "index"; // viewResolver를 설정했기 때문에 .jsp 생략 가능/ index.jsp 화면 호출
	}
	
}
