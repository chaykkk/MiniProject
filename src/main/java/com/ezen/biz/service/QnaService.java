package com.ezen.biz.service;

import java.util.List;

import com.ezen.biz.dto.QnaVO;

public interface QnaService {

	// 전체 QnA 목록 조회
	List<QnaVO> listQna(String id);

	// 게시글 상세 조회
	QnaVO getQna(int qseq);

	// 게시글 추가
	void insertQna(QnaVO vo);

	// 게시판 전체 조회
	public List<QnaVO> listAllQna();
	
	// 게시판 답변 처리
	public void updateQna(QnaVO vo);
}