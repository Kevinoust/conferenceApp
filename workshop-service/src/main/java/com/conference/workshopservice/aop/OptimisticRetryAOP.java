package com.conference.workshopservice.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

@Aspect
@Component
@Order(10000)
public class OptimisticRetryAOP {
    private static final Logger LOGGER = LoggerFactory.getLogger(OptimisticRetryAOP.class);

    @Pointcut("@annotation(com.conference.workshopservice.aop.OptimisticRetry) || @within(com.conference.workshopservice.aop.OptimisticRetry)")
    public void optimisticRetryPointcut() {
    }

    @Around("optimisticRetryPointcut()")
    public Object doConcurrentOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;

        Class<?> targetClass = joinPoint.getTarget().getClass();                                                        // proxy target class
        Method method = targetClass.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());          // target method
        Optional<OptimisticRetry> optimisticRetryOptional = getOptimisticRetry(method.getAnnotations());                // annotation
        if(optimisticRetryOptional.isEmpty()) {
            return joinPoint.proceed();
        }

        OptimisticRetryConfig optimisticRetryConfig = new OptimisticRetryConfig(optimisticRetryOptional.get());         // get optimisticRetry config

        int numAttempts = 0;
        int maxAttempts = optimisticRetryConfig.maxRetryCount;
        long startTime = System.currentTimeMillis();
        long maxExecuteTime = optimisticRetryConfig.maxExecuteTime;
        do {
            LOGGER.info("OptimisticRetry! attempt:" + numAttempts);
            numAttempts++;
            try {
                return joinPoint.proceed();
            } catch (OptimisticLockingFailureException e) {
                long executedTime = System.currentTimeMillis() - startTime;
                if(executedTime > maxExecuteTime) {
                    throw new OptimisticLockingFailureException("Optimistic retry exceeded maximum execution time");
                }
            }
        } while(numAttempts <= maxAttempts);

        throw new OptimisticLockingFailureException("Optimistic retry exceeded maximum retry count");
    }

    private Optional<OptimisticRetry> getOptimisticRetry(Annotation[] annotations) {
        for(Annotation annotation : annotations) {
            if(annotation.annotationType().getName().equals(OptimisticRetry.class.getName())) {
                return Optional.of((OptimisticRetry)annotation);
            }
        }
        return Optional.empty();
    }

    private static class OptimisticRetryConfig {
        private int maxRetryCount;
        private long maxExecuteTime;

        public OptimisticRetryConfig(int maxRetryCount, long maxExecuteTime) {
            this.maxRetryCount = maxRetryCount;
            this.maxExecuteTime = maxExecuteTime;
        }

        public OptimisticRetryConfig(OptimisticRetry optimisticRetry) {
            this.maxRetryCount = optimisticRetry.maxRetryCount();
            this.maxExecuteTime = optimisticRetry.maxExecutionTime();
        }
    }
}
