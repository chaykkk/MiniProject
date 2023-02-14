package com.ezen.biz.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.biz.dto.QnaVO;

@Repository
public class QnaDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	// 전체 QnA 목록 조회
	public List<QnaVO> listQna(String id) {
		return mybatis.selectList("qnaMapper.listQna", id);
	}
	
	// 게시글 상세 조회
	public QnaVO getQna(int qseq) {
		return mybatis.selectOne("qnaMapper.getQna", qseq);
	}
	
	// 게시글 추가
	public void insertQna(QnaVO vo) {
		mybatis.insert("qnaMapper.insertQna", vo);
	}
	
	// 게시판 전체 조회
	public List<QnaVO> listAllQna() {
		return mybatis.selectList("qnaMapper.listAllQna");
	}
	
	// 게시판 답변 처리
	public void updateQna(QnaVO vo) {
		mybatis.update("qnaMapper.updateQna", vo);
	}
}
