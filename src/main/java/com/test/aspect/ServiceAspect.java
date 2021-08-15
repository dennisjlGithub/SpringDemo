package com.test.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

//@Component
//@Aspect
public class ServiceAspect {

	private final String POINT_CUT = "execution(* com.test..*.*(..))";

	@Pointcut(POINT_CUT)
	private void pointcut() {
		System.out.println("Aspect --> pointcut()");
	}
	
	@Before("pointcut()")
	public void preDoBusiness(JoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("AfterReturning " + methodName);
		System.out.println("********************************************");
		System.out.println("**************preDoBusiness****************");
		System.out.println("********************************************");
	}
	
	@After("pointcut()")
    public void doAfter() throws Throwable {
		System.out.println("Aspect --> doAfter()");
	}
	
	@Around("pointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		System.out.println("Aspect --> doAround()");
		return proceedingJoinPoint.proceed();
	}
	
	@AfterReturning(value = "pointcut()", returning = "entity")
    public void postDoBusiness(JoinPoint joinPoint, Object entity) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("AfterReturning " + methodName);
		System.out.println("********************************************");
		System.out.println("**************postDoBusiness****************");
		System.out.println("********************************************");
	}
}