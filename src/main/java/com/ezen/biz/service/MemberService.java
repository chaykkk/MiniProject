package com.ezen.biz.service;

import java.util.List;

import com.ezen.biz.dto.AddressVO;
import com.ezen.biz.dto.MemberVO;

public interface MemberService {

	// 회원id를 조건으로 회원 조회
	MemberVO getMember(String id);

	// 회원 존재 여부 확인
	int confirmID(String id);

	// 회원가입
	void insertMember(MemberVO vo);

	// 동이름으로 주소 찾기
	List<AddressVO> selsectAddressByDong(String dong);
	
	// 사용자 인증
	int loginID(MemberVO vo);
	
	// 이름, 이메일로 아이디 찾기
	String selectIdByNameEmail(MemberVO vo);
	
	// 아이디, 이름, 이메일로 비밀번호 찾기 
	String selectPwdById(MemberVO vo) ;
	
	// 비밀번호 변경
	void changePwd(MemberVO vo);
	
	// 전체 회원 조회
	public List<MemberVO> listMember(String name);
}