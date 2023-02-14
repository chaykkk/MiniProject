package com.ezen.biz.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.biz.dto.AddressVO;
import com.ezen.biz.dto.MemberVO;

@Repository
public class MemberDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// 회원id를 조건으로 회원 조회
	public MemberVO getMember(String id) {
		return mybatis.selectOne("memberMapper.getMember", id);
	}
	
	// 회원ID 존재 여부 확인
	public int confirmID(String id) {
		String pwd = mybatis.selectOne("memberMapper.confirmID", id);
		
		if (pwd != null) {
			return 1; // id 존재
		} else {
			return -1; // id 존재하지 않음
		}
	}
	
	// 회원 로그인 확인
	public int loginID(MemberVO vo) {
		int result = -1;
		String pwd = mybatis.selectOne("memberMapper.confirmID", vo.getId());
		
		// 테이블에서 조회한 pwd와 사용자가 입력한 pwd 비교
		if (pwd == null) {
			result = -1; // 아이디가 존재하지 않음
		} else if (pwd.equals(vo.getPwd())) {
			result = 1; // 정상 로그인
		} else {
			result = 0; // 비밀번호 불일치
		}
		
		return result;
	}
	
	// 회원가입
	public void insertMember(MemberVO vo) {
		mybatis.insert("memberMapper.insertMember", vo);
	}
	
	// 동이름으로 우편번호 조회
	public List<AddressVO> selectAddressByDong(String dong) {
		return mybatis.selectList("memberMapper.selectAddressByDong", dong);
	}
	
	// 이름, 이메일로 아이디 찾기
	public String selectIdByNameEmail(MemberVO vo) { // 이름, 이메일을 받아야해서 memverVO로 설정
		return mybatis.selectOne("memberMapper.selectIdByNameEmail", vo);
	}
	
	// 아이디, 이름, 이메일로 비밀번호 찾기 
	public String selectPwdById(MemberVO vo) {
		return mybatis.selectOne("memberMapper.selectPwdById", vo);
	}
	
	// 비밀번호 변경
	public void changePwd(MemberVO vo) {
		mybatis.update("memberMapper.changePwd", vo);
	}
	
	// 전체회원조회
	public List<MemberVO> listMember(String name) {
		return mybatis.selectList("memberMapper.listMember", name);
	}
}
