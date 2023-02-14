package com.ezen.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.CommentDAO;
import com.ezen.biz.dto.CommentVO;

import utils.Criteria;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDAO commentDao;
		
	@Override
	public int saveComment(CommentVO vo) {
		return commentDao.saveComment(vo);
	}

	@Override
	public List<CommentVO> getCommentList(int pseq) {
		return commentDao.commentList(pseq);
	}

	@Override
	public int getCountCommentList(int pseq) {
		return commentDao.countCommentList(pseq);
	}
	
	@Override
	public List<CommentVO> getCommentListWithPaging(Criteria criteria, int pseq) {
		return commentDao.commentListWithPaging(criteria, pseq);
	}

}
