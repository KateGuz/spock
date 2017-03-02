package ua.spock.spock.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutionTimeLogger {

    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
      //  Logger logger = LoggerFactory.getLogger(joinPoint.getClass());
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        long start = System.currentTimeMillis();
        logger.info( "{}.{}() processing started", className, methodName);

        Object out = joinPoint.proceed();

        long executeTime = System.currentTimeMillis() - start;
        logger.info("{}.{}() processing completed in {} ms", className, methodName, executeTime);
        return out;
    }
}
