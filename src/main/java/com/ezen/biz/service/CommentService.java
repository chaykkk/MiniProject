package com.ezen.biz.service;

import java.util.List;

import com.ezen.biz.dto.CommentVO;

import utils.Criteria;

public interface CommentService {

	// 상품평 저장
	int saveComment(CommentVO vo);

	// 상품평 전체 목록 조회
	List<CommentVO> getCommentList(int pseq);

	// 상품평수 조회
	public int getCountCommentList(int pseq);
	
	// 페이지별로 상품평 조회
	public List<CommentVO> getCommentListWithPaging(Criteria criteria, int pseq);
}