//package com.ezen.biz.common;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Service;
//
//@Service
//@Aspect
//
//public class AfterReturningAdvice {
//	
//	@AfterReturning(pointcut="PointcutCommon.getPointcut()", returning="returnObj")
//	public void afterLog(JoinPoint jp, Object returnObj) {// jp:메소드정보 저장, returnObj:return 값 저장
//		String method = jp.getSignature().getName();
//		
//		System.out.printf("[사후 처리] 메소드명: %s\n, 리턴값: %s\n", method, returnObj.toString());
//	}
//}
