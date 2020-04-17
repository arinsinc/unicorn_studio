package com.unicorn.studio.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.unicorn.studio.dao.AuditLogDAO;
import com.unicorn.studio.entity.AuditLog;

@Aspect
@Component
public class UnicornLoggingAspect {
	@Pointcut("execution(* com.unicorn.studio.controller.*.get*(..))")
	public void forControllerGetService() {}
	
	@Pointcut("execution(* com.unicorn.studio.controller.*.new*(..))")
	public void forControllerNewService() {}
	
	@Pointcut("execution(* com.unicorn.studio.controller.*.add*(..))")
	public void forControllerAddService() {}
	
	@Pointcut("execution(* com.unicorn.studio.controller.*.edit*(..))")
	public void forControllerEditService() {}
	
	@Pointcut("execution(* com.unicorn.studio.controller.*.delete*(..))")
	public void forControllerDeleteService() {}
	
	@Pointcut("execution(* com.unicorn.studio.rest.*.get*(..))")
	public void forRestGetService() {}
	
	@Pointcut("execution(* com.unicorn.studio.rest.*.add*(..))")
	public void forRestAddService() {}
	
	@Pointcut("execution(* com.unicorn.studio.rest.*.update*(..))")
	public void forRestUpdateService() {}
	
	@Pointcut("execution(* com.unicorn.studio.rest.*.delete*(..))")
	public void forRestDeleteService() {}
	
	@Pointcut("forControllerGetService() || forControllerNewService() || forControllerAddService()"
			+ "forControllerEditService() || forControllerDeleteService() || forRestGetService()"
			+ "forRestAddService() || forRestUpdateService() || forRestDeleteService()")
	public void auditLog() {}
	
	@Pointcut("forControllerGetService() || forControllerNewService()")
	public void forContollerGetRequest() {}
	
	@Pointcut("forControllerGetService() || forControllerNewService()")
	public void forControllerGetRequest() {}
	
	private AuditLogDAO logEntry;
	
	
	@Before("auditLog()")
	public void addLogEntry(JoinPoint joinPoint) {
		String method = joinPoint.getSignature().toShortString();
		System.out.println("Logging entries to Audit Log......");
		System.out.println("@Before: calling method " + method);
	}
	
//	@Before("forControllerGetRequest()")
//	public void controllerGetEntry(JoinPoint joinPoint) {
//		String method = joinPoint.getSignature().toShortString();
//		AuditLog entry = new AuditLog(method,"GET","controller",true);
//		logEntry.saveLog(entry);
//	}
//	
//	@Before("forControllerAddService()")
//	public void controllerPostEntry(JoinPoint joinPoint) {
//		String method = joinPoint.getSignature().toShortString();
//		AuditLog entry = new AuditLog(method,"POST","controller",true);
//		logEntry.saveLog(entry);
//	}
//	
//	@Before("forControllerEditService()")
//	public void controllerPutEntry(JoinPoint joinPoint) {
//		String method = joinPoint.getSignature().toShortString();
//		AuditLog entry = new AuditLog(method,"PUT","controller",true);
//		logEntry.saveLog(entry);
//	}
//	
//	@Before("forControllerDeleteService()")
//	public void controllerDeleteEntry(JoinPoint joinPoint) {
//		String method = joinPoint.getSignature().toShortString();
//		AuditLog entry = new AuditLog(method,"DELETE","controller",true);
//		logEntry.saveLog(entry);
//	}
//	
//	@Before("forRestGetService()")
//	public void restGetEntry(JoinPoint joinPoint) {
//		String method = joinPoint.getSignature().toShortString();
//		AuditLog entry = new AuditLog(method,"GET","rest",true);
//		logEntry.saveLog(entry);
//	}
//	
//	@Before("forRestAddService()")
//	public void restPostEntry(JoinPoint joinPoint) {
//		String method = joinPoint.getSignature().toShortString();
//		AuditLog entry = new AuditLog(method,"POST","rest",true);
//		logEntry.saveLog(entry);
//	}
//	
//	@Before("forRestUpdateService()")
//	public void restPutEntry(JoinPoint joinPoint) {
//		String method = joinPoint.getSignature().toShortString();
//		AuditLog entry = new AuditLog(method,"PUT","rest",true);
//		logEntry.saveLog(entry);
//	}
//	
//	@Before("forRestDeleteService()")
//	public void restDeleteEntry(JoinPoint joinPoint) {
//		String method = joinPoint.getSignature().toShortString();
//		AuditLog entry = new AuditLog(method,"DELETE","rest",true);
//		logEntry.saveLog(entry);
//	}
	
	
	
	@AfterReturning(
			pointcut="auditLog()",
			returning="result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		String method = joinPoint.getSignature().toShortString();
		System.out.println("@AfterReturning: " + method);
		System.out.println("Result: " + result);
	}
}
