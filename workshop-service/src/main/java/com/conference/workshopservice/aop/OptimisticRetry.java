package com.conference.workshopservice.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mechanism for auto-retry for Optimistic Lock
 *
 * if method throws OptimisticLockingFailureException, method will be re-executed automatically until
 * 1. retry time exceeded maxRetryCount         or
 * 2. execution time exceeded maxExecutionTime
 *
 * Pro:
 * - no lock, no block = better performance
 *
 * Con:
 * - if concurrent writes are intensive -> keeps retry... -> eating CPU times...
 *
 *
 * source: https://blog.csdn.net/new_com/article/details/103834871
 *
 * **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OptimisticRetry {

    int maxRetryCount() default 200;                                                                                    // maximum retry

    int maxExecutionTime() default 2 * 60 * 60 * 1000;                                                                  // maximum execution time
}
