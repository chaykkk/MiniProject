package com.ezen.view.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.dto.QnaVO;
import com.ezen.biz.service.QnaService;

@Controller
public class QnaController {

	@Autowired
	private QnaService qnaService;
	
	// 게시글 목록 조회
	@RequestMapping(value="/qna_list")
	public String qnaList(HttpSession session, QnaVO vo, Model model) {
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		// 로그인 여부 확인
		if(loginUser == null) {
			return "member/login";
		} else {
			List<QnaVO> qnaList = qnaService.listQna(loginUser.getId());
			model.addAttribute("qnaList", qnaList);
			
			return "qna/qnaList";
		}
	}
	
	// 게시글 등록 화면 표시
	@GetMapping(value="/qna_write_form")
	public String qnaWriteView(HttpSession session) {
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		// 로그인 여부 확인
		if(loginUser == null) {
			return "member/login";
		} else {
			return "qna/qnaWrite";
		}
	}
	
	// 게시글 등록
	@PostMapping(value="/qna_write")
	public String qnaWrite(QnaVO vo, HttpSession session) {
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		// 로그인 여부 확인
		if(loginUser == null) {
			return "member/login";
		} else {
			vo.setId(loginUser.getId());
			qnaService.insertQna(vo);
			
			return "redirect:qna_list";
		}
	}
	
	// 게시글 상세보기
	@RequestMapping(value="/qna_view")
	public String qnaView(HttpSession session, QnaVO vo, Model model) {
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
	
		// 로그인 여부 확인
		if (loginUser == null) {
			return "member/login";
		} else {
			QnaVO qna = qnaService.getQna(vo.getQseq());
			model.addAttribute("qnaVO", qna);
			
			return "qna/qnaView";
		}
	}
}
