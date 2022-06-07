package com.example.WeibisWeb.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdviceController {

    Logger log = LoggerFactory.getLogger(LoggingAdviceController.class);

    @Pointcut("execution(* com.example.WeibisWeb.demo.controller.*.*(..))")
    public void myPointCut() {

    }

    @Around(value = "myPointCut()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        log.info("Method that is invoked : "+ className +" and the method : "+methodName+"()");
        Object obj = pjp.proceed();
        log.info("As a response is invoked : "+className + " : "+methodName+"()");

        return obj;
    }
}
