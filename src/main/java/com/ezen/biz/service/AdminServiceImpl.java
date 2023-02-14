package com.ezen.biz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.AdminDAO;
import com.ezen.biz.dto.AdminVO;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDAO adminDao;
	
	@Override
	public int adminCheck(AdminVO vo) {
		int result = -1;
		
		// Admin 테이블에서 pwd 조회
		String pwd_in_db = adminDao.adminCheck(vo.getId());
		
		if (pwd_in_db == null) {
			result = -1; // 아이디 없음
		} else if (pwd_in_db.equals(vo.getPwd())) {
			result = 1; // 로그인 성공
		} else {
			result = 0; // 비밀번호 오류
		}
		
		return result;
	}

	@Override
	public AdminVO getAdmin(String id) {
		return adminDao.getAdmin(id);
	}

}
