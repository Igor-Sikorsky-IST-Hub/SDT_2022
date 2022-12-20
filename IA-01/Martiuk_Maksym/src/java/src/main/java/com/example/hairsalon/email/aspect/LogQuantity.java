package com.example.hairsalon.email.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LogQuantity {

    @Pointcut("@annotation(MonitorQuantityOfMails)")
    public void annotatedMethods() {
    }

    @AfterReturning(pointcut = "annotatedMethods()", returning = "result")
    public void logQuantity(Object result) {
        log.info("Hello from aspect, yours count is {}", result);
    }

}
