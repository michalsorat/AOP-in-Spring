package com.example.aop.aspect;

import com.example.aop.exception.DatabaseConnectionException;
import com.example.aop.exception.NetworkTimeoutException;
import com.example.aop.service.DatabaseExceptionHandler;
import com.example.aop.service.NetworkExceptionHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AutomaticErrorHandlingAspect {

    @AfterThrowing(pointcut = "@annotation(com.example.aop.aspect.annotation.AutomaticErrorHandling)", throwing = "exception")
    public void handleErrors(JoinPoint joinPoint, Exception exception) {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("AUTOMATIC ERROR HANDLING");
        if (exception instanceof DatabaseConnectionException) {
            DatabaseExceptionHandler.handleDatabaseError();
        } else if (exception instanceof NetworkTimeoutException) {
            NetworkExceptionHandler.handleNetworkError();
        } else {
            logDefaultError(joinPoint, exception);
        }
        System.out.println("-----------------------------------------------------------------------------");
    }

    private void logDefaultError(JoinPoint joinPoint, Exception exception) {
        System.err.println("Error occurred in method: " + joinPoint.getSignature().getName());
        System.err.println("Exception details: " + exception.getMessage());
    }
}
