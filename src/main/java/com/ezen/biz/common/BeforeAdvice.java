//package com.ezen.biz.common;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Service;
//
///*
// * 비즈니스 메소드에 적용할 로깅처리 클래스
// */
//@Service
//@Aspect
//public class BeforeAdvice {
//
//	@Before("PointcutCommon.allPointcut()")
//	public void beforeLog(JoinPoint jp) {
//		// 업무처리 메소드의 선언부 정보를 얻어온다.
//		Signature sig = jp.getSignature();
//		String method = sig.getName(); // 메소드 이름을 얻어온다.
//		
//		Object[] args = jp.getArgs(); // 매개변수를 얻어온다.
//		
//		if (args.length == 0) { // 매개변수가 없는 경우
//			System.out.printf("[사전 처리] 메소드명: %s() Args: 없음\n", method);
//		} else {
//			System.out.printf("[사전 처리] 메소드명: %s() Args: %s\n", method, args[0].toString());
//		}
//	}
//}
