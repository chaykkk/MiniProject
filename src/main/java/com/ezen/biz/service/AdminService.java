package com.ezen.biz.service;

import com.ezen.biz.dto.AdminVO;

public interface AdminService {

	// 관리자 로그인
	int adminCheck(AdminVO vo);

	// 관리자 정보 조회
	AdminVO getAdmin(String id);

}