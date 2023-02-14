package com.ezen.view.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.biz.dto.CommentVO;
import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.service.CommentService;

import utils.Criteria;
import utils.PageMaker;

@RequestMapping("/comments")
@RestController // 데이터를 전달하는 컨트롤러(일반 컨트롤러는 화면을 리턴함)
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	// 상품평 저장
	@PostMapping(value="/save")
	public String saveCommentAction(CommentVO vo, HttpSession session) {
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "not_logedin";
		} else {
			vo.setWriter(loginUser.getId()); // 사용자 아이디 저장
			
			// 상품평 저장
			if (commentService.saveComment(vo) > 0) {
				return "success";
			} else {
				return "fail";
			}
		}
	}
	
	/*
	// 상품평 리스트 조회
	@GetMapping(value="/list", produces="application/json; charset=UTF-8")
	public Map<String, Object> commentList(CommentVO vo) {
		Map<String, Object> commentInfo = new HashMap<>();
		
		// 상품평 목록 조회
		List<CommentVO> commentList = commentService.getCommentList(vo.getPseq());
		
		commentInfo.put("total", commentList.size());
		commentInfo.put("commentList", commentList);
		
		return commentInfo;
	}
	*/
	
	// 상품평 리스트 페이징 처리 조회
	@GetMapping(value="/list", produces="application/json; charset=UTF-8")
	public Map<String, Object> commentList(Criteria criteria, CommentVO vo) {
		
		Map<String, Object> commentInfo = new HashMap<>();
		
		// 상품평 목록 조회
		List<CommentVO> commentList = commentService.getCommentListWithPaging(criteria, vo.getPseq());
		
		// 페이지 정보 작성
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(commentService.getCountCommentList(vo.getPseq()));

		commentInfo.put("pageInfo", pageMaker);
		commentInfo.put("total", commentList.size());
		commentInfo.put("commentList", commentList);
		
		return commentInfo;
	}
}
