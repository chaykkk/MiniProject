package com.ezen.biz.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.biz.dto.CommentVO;

import utils.Criteria;

@Repository
public class CommentDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// 상품평 저장
	public int saveComment(CommentVO vo) {
		return mybatis.insert("commentMapper.saveComment", vo);
	}
	
	// 상품평 전체 목록 조회
	public List<CommentVO> commentList(int pseq) {
		return mybatis.selectList("commentMapper.commentList", pseq);
	}
	
	// 상품평수 조회
	public int countCommentList(int pseq) {
		return mybatis.selectOne("commentMapper.countCommentList", pseq);
	}
	
	// 페이지별로 상품평 조회
	public List<CommentVO> commentListWithPaging(Criteria criteria, int pseq) {
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("criteria", criteria);
		map.put("pseq", pseq);
		
		return mybatis.selectList("commentMapper.commentListWithPaging", map);		
	}
}
