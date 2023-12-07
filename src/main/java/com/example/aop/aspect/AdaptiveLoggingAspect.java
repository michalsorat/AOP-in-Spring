package com.example.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AdaptiveLoggingAspect {

    @Around("@annotation(com.example.aop.aspect.annotation.AdaptiveLogging)")
    public Object adaptiveLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("ADAPTIVE LOGGING AND MONITORING");
        long startTime = System.currentTimeMillis();

        Object result;
        try {
            // Proceed with the actual method execution
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            // Log exception information if an exception is thrown
            logException(joinPoint, throwable);
            throw throwable;
        }

        long executionTime = System.currentTimeMillis() - startTime;

        // Adjust log verbosity based on execution time
        if (executionTime > 1000) {
            // Log detailed information for methods taking more than 1 second
            logDetailedInfo(joinPoint, executionTime);
        } else {
            // Log standard information for methods with quicker execution
            logStandardInfo(joinPoint, executionTime);
        }

        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;

        double usedMemoryPercentage = ((double) usedMemory / totalMemory) * 100.0;
        if (usedMemoryPercentage > 70) {
            // Log detailed memory usage for methods taking more than 70% of memory
            logDetailMemoryUsage();
        } else {
            // Log standard memory usage for methods taking less than 70% of memory
            logMemoryUsage();
        }
        System.out.println("-----------------------------------------------------------------------------");

        return result;
    }

    private void logStandardInfo(JoinPoint joinPoint, long executionTime) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Standard log message: Method '" + methodName + "' executed in " + executionTime + " ms");
    }

    private void logDetailedInfo(JoinPoint joinPoint, long executionTime) {
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        System.out.println("Detailed log message: Method '" + methodName + "' executed in " + executionTime + " ms");
        System.out.println("Method parameters: " + Arrays.toString(methodArgs));
    }

    private void logMemoryUsage() {
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;

        double usedMemoryPercentage = ((double) usedMemory / totalMemory) * 100.0;

        System.out.printf("Memory usage: Total=%d bytes, Free=%d bytes, Used=%d bytes, Used Percentage=%.2f%%\n",
                totalMemory, freeMemory, usedMemory, usedMemoryPercentage);
    }

    private void logDetailMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();

        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        long usedMemory = totalMemory - freeMemory;

        double usedMemoryPercentage = ((double) usedMemory / totalMemory) * 100.0;

        // Convert memory values to megabytes or gigabytes for better readability
        double totalMemoryMB = totalMemory / (1024.0 * 1024.0);
        double freeMemoryMB = freeMemory / (1024.0 * 1024.0);
        double usedMemoryMB = usedMemory / (1024.0 * 1024.0);
        double maxMemoryMB = maxMemory / (1024.0 * 1024.0);

        System.out.print("Detailed memory usage:\n");
        System.out.printf("  Total Memory: %.2f MB (%d bytes)\n", totalMemoryMB, totalMemory);
        System.out.printf("  Free Memory : %.2f MB (%d bytes)\n", freeMemoryMB, freeMemory);
        System.out.printf("  Used Memory : %.2f MB (%d bytes, %.2f%%)\n", usedMemoryMB, usedMemory, usedMemoryPercentage);
        System.out.printf("  Max Memory  : %.2f MB (%d bytes)\n", maxMemoryMB, maxMemory);
    }

    private void logException(JoinPoint joinPoint, Throwable throwable) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Exception Log: Method '" + methodName + "' threw exception: " + throwable.getMessage());
    }
}
