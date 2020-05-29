package com.unicorn.studio.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UnicornLoggingAspect {
	@Pointcut("execution(* com.unicorn.studio.controller.*.set*(..))")
	public void forControllerSetMethod() {}
	
	@Pointcut("execution(* com.unicorn.studio.controller.*.get*(..))")
	public void forControllerGetMethod() {}
	
	@Pointcut("execution(* com.unicorn.studio.service.*.*(..))")
	public void forRestPackage() {}
	
	@Pointcut("forControllerSetMethod() || forControllerGetMethod()")
	public void forAppFlow() {}
	
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		String method = joinPoint.getSignature().toShortString();
		System.out.println("@Before: calling method" + method);
	}
	
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		String method = joinPoint.getSignature().toShortString();
		System.out.println("@AfterReturning: from method" + method);
		System.out.println("Result: " + result);
	}
}
