package com.conference.attendeeticketservice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAOP {
    @Pointcut("execution(* com.conference.attendeeticketservice.controller..*(..))")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getClass());
        logger.info("{} start", joinPoint.getSignature().getName());
    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getClass());
        logger.info("{} end", joinPoint.getSignature().getName());
    }
}