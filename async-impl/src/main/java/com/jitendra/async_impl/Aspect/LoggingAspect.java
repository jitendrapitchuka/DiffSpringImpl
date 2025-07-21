package com.jitendra.async_impl.Aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // @Before("execution(* com.jitendra.async_impl.Service.EmailService.*(..))")
    // public void logBeforeMethodExecution(JoinPoint joinPoint) {
    //     // Logic to log before method execution
    //     System.out.println("-----Method execution started-----");
    //     System.out.println("-----Method name: " + joinPoint.getSignature().getName() + "-----");
    // }

    @Around("execution(* com.jitendra.async_impl.Service.EmailService.*(..))")
    public void logBeforeAndAfterMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        // Logic to log before and after method execution
        System.out.println("-----Method execution started-----");
        System.out.println("-----Method name: " + joinPoint.getSignature().getName() + "-----");
        System.out.println("-----Method arguments: " + Arrays.toString(joinPoint.getArgs()) + "-----");
        long startTime = System.currentTimeMillis();
        joinPoint.proceed();
        // After logic can be added here if needed
        long endTime = System.currentTimeMillis();
        System.out.println("-----Method execution finished-----"+
                "-----Execution time: " + (endTime - startTime) + " ms-----");
        
    }

}
