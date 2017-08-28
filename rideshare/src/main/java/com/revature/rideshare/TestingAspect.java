package com.revature.rideshare;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

//@Aspect
public class TestingAspect {
	
	@Pointcut("execution(* com.revature.rideshare..* (..))")
	public void allMethods() {}
	
	@Pointcut("execution(* com.revature.rideshare.controller..* (..))")
	public void controllerMethods() {}
	
	
	
}
