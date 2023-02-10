package com.ia01.yhnitii.shell.common.logging;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@EnableAspectJAutoProxy
public class LoggingAspect {

    @SneakyThrows
    @Around("@annotation(com.ia01.yhnitii.shell.common.logging.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        log.info("{} method executed in {} ms", joinPoint.getSignature(), executionTime);

        return proceed;
    }

    @AfterThrowing(pointcut = "within(@org.springframework.stereotype.Service *)", throwing = "ex")
    public void logError(JoinPoint joinPoint, Exception ex) {
        log.warn("{} in {} method", ex, joinPoint.getSignature());
    }
}
