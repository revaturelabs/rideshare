package com.revature.rideshare;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Logging {

	// Example of logging all DAO method invocation along w/ their args
//	@Before("execution(* com.revature.rideshare.dao..* (..))")
	public void logRepoMethods(JoinPoint jp) {
		Logger logger = LoggerFactory.getLogger(jp.getTarget().getClass());

		MethodSignature sig = (MethodSignature) jp.getSignature();

		StringBuilder sb = new StringBuilder("Starting " + sig.getName() + "(");

		String[] paramNames = sig.getParameterNames();
		Object[] paramValues = jp.getArgs();

		if (paramNames != null && paramNames.length > 0 && paramValues != null) {
			sb.append("\n");
			for (int i = 0; i < paramNames.length; i++) {
				sb.append("\t" + paramNames[i] + " : " + paramValues[i].toString() + "\n");
			}
		}

		sb.append(")");
		logger.info(sb.toString());
	}

}
