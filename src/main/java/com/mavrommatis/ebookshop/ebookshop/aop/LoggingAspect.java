package com.mavrommatis.ebookshop.ebookshop.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect class responsible for logging method execution details
 * in the service layer of the application.
 *
 * <p>This aspect captures:</p>
 * <ul>
 *   <li>Method entry with arguments</li>
 *   <li>Execution time of each method</li>
 *   <li>Any exceptions thrown during method execution</li>
 * </ul>
 *
 * <p>It is applied to all public methods of classes under the
 * {@code com.mavrommatis.ebookshop.ebookshop.service} package and its subpackages.</p>
 *
 * <p>Use this aspect to gain observability, debugging support, and insight
 * into performance bottlenecks.</p>
 */
@Aspect
@Component
public class LoggingAspect {

    /** Logger instance for structured logging of method executions */
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Pointcut that matches all public methods within the {@code service} package and its subpackages.
     *
     * <p>This includes {@code service.basic}, {@code service.details}, etc.</p>
     */
    @Pointcut("execution(public * com.mavrommatis.ebookshop.ebookshop.service..*(..))")
    public void allServiceMethods() {
        // Marker method - no implementation needed
    }

    /**
     * Logs detailed information about the execution of service layer methods.
     *
     * <p>Logs the following:</p>
     * <ul>
     *   <li>Method name and class</li>
     *   <li>Input arguments</li>
     *   <li>Execution time in milliseconds</li>
     *   <li>Exceptions, if thrown</li>
     * </ul>
     *
     * <p>This advice wraps around the method call, providing full control over pre- and post-execution logic.</p>
     *
     * @param joinPoint the current method execution context
     * @return the result of the original method execution
     * @throws Throwable rethrows any exceptions that occur during method execution
     */
    @Around("allServiceMethods()")
    public Object logExecutionDetails(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringType().getSimpleName() + "." + signature.getName();
        Object[] args = joinPoint.getArgs();

        log.info("➡️ Entering method: {} with arguments: {}", methodName, Arrays.toString(args));

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;

            log.info("✅ Method {} executed in {} ms", methodName, duration);
            return result;

        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - start;
            log.error("❌ Exception in method {} after {} ms: {}", methodName, duration, ex.getMessage(), ex);
            throw ex;
        }
    }
}