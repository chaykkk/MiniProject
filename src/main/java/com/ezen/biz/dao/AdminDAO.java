package com.ezen.biz.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.biz.dto.AdminVO;

@Repository
public class AdminDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// 관리자 로그인
	public String adminCheck(String id) {
		return mybatis.selectOne("adminMapper.adminCheck", id);
	}
	
	// 관리자 정보 조회
	public AdminVO getAdmin(String id) {
		return mybatis.selectOne("adminMapper.getAdmin", id);
	}
}
