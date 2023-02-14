package com.ezen.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ezen.biz.dto.AddressVO;
import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.service.MemberService;

@Controller
@SessionAttributes("loginUser")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	// 약정화면 표시
	@RequestMapping(value="/contract", method=RequestMethod.GET)
	public String contractView() {
		return "member/contract";
	}
	
	// 회원가입 화면 표시
	@RequestMapping(value="/join_form", method=RequestMethod.POST)
	public String joinView() {
		return "member/join";
	}
	
	// 로그인 화면 출력
	@GetMapping(value="/login_form")
	public String loginView() {
		return "member/login";
	}
	
	// 사용자 인증
	@PostMapping(value="/login")
	public String loginAction(MemberVO vo, Model model) {
		int result = memberService.loginID(vo);
		
		if (result == 1) { // 로그인 성공
			model.addAttribute("loginUser", memberService.getMember(vo.getId()));
			return "redirect:index";
		} else {
			return "member/login_fail";
		}
	}
	
	// 로그아웃
	@GetMapping(value="/logout")
	public String logout(SessionStatus status) {
		status.setComplete(); // 세션 해지(스프링에선 session.invalidate는 완전히 해지가 안됨)
		
		return "member/login";
	}
	
	// 아이디 중복체크 화면 표시
	@RequestMapping(value="/id_check_form", method=RequestMethod.GET)
	public String idCheckView(MemberVO vo, Model model) {
		// id 중복확인 조회
		int result = memberService.confirmID(vo.getId());
		model.addAttribute("message", result);
		model.addAttribute("id", vo.getId());
		
		return "member/idcheck";
	}
	
	// 아이디 중복체크 실행
	@RequestMapping(value="/id_check_form", method=RequestMethod.POST)
	public String idCheckAction(MemberVO vo, Model model) {
		// id 중복확인 조회
		int result = memberService.confirmID(vo.getId());
		model.addAttribute("message", result);
		model.addAttribute("id", vo.getId());
		
		return "member/idcheck";
	}
	
	// 회원가입 처리
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String joinAction(@RequestParam(value="addr1", defaultValue="") String addr1,
							 @RequestParam(value="addr2", defaultValue="") String addr2,
							  MemberVO vo) {
		
		if (vo.getPhone() == null) {
			vo.setPhone("");
		}
		vo.setAddress(addr1 + " "  + addr2);
		memberService.insertMember(vo);
		
		return "member/login";
	}
	
	// 동이름으로 우편번호 찾기 화면 표시
	@RequestMapping(value="find_zip_num", method=RequestMethod.GET)
	public String findZipNumView() {
		return "member/findZipNum";
	}
	
	// 동이름으로 우편번호 찾기 실행
	@RequestMapping(value="find_zip_num", method=RequestMethod.POST)
	public String findZipNumAction(AddressVO vo, Model model) {
		model.addAttribute("addressList", memberService.selsectAddressByDong(vo.getDong()));
		
		return "member/findZipNum";
	}
	
	// 아이디 찾기 화면 호출
	@GetMapping(value="find_id_form")
	public String findIdFormView() {
		return "member/findIdAndPassword";
	}
	
	// 아이디 찾기 실행
	@PostMapping(value="/find_id")
	public String findIdAction(MemberVO vo, Model model) {
		String id = memberService.selectIdByNameEmail(vo);
		
		if (id != null) { // 이름과 이메일을 조건으로 아이디 조회 성공
			model.addAttribute("message", 1);
			model.addAttribute("id", id);
		} else {
			model.addAttribute("message", -1);
		}
		
		return "member/findResult"; // 아이디 조회 결과 화면 요청
	}
	
	// 비밀번호 찾기 실행
	@PostMapping(value="/find_pwd")
	public String findPwdAction(MemberVO vo, Model model) {
		String pwd = memberService.selectPwdById(vo);
		
		if (pwd != null) {
			model.addAttribute("message", 1);
			model.addAttribute("id", vo.getId());
		} else {
			model.addAttribute("message", -1);
		}
		
		return "member/findPwdResult";
	}
	
	// 비밀번호 변경
	@PostMapping(value="/change_pwd")
	public String changePwd(MemberVO vo) {
		memberService.changePwd(vo);
		
		return "member/changePwdOk";
	}
}
