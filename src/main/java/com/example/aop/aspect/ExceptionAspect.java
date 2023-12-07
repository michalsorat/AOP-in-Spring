package com.example.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ExceptionAspect {

    @AfterThrowing(pointcut = "execution(* com.example.aop.service.*.*(..))", throwing = "exception")
    public void handleException(JoinPoint joinPoint, Exception exception) {
        String method = joinPoint.getSignature().getName();
        String params = Arrays.toString(joinPoint.getArgs());
        System.out.println("Method [" + method + "] threw an exception with parameters " + params);
        System.out.println("Exception: " + exception.getMessage());
    }
}
