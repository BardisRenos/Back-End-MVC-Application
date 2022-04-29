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
public class LoggingAdviceRepository {

    Logger log = LoggerFactory.getLogger(LoggingAdviceRepository.class);

    @Pointcut("execution(* com.Weibis.demo.dao.*.*(..))")
    public void myPointCut() {

    }

    @Around(value = "myPointCut()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        log.info("method invoked : "+ className +" and the method : "+methodName+" () ");
        Object obj = pjp.proceed();
        log.info(className + " : "+methodName+"()");

        return obj;
    }

}
