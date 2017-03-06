package ua.spock.spock.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class DaoExecutionTimeLogger extends ExecutionTimeLogger{
    @Pointcut("within(@org.springframework.stereotype.Repository *)")
    private void repository() {
    }

    @Pointcut("execution(public * *(..))")
    public void publicMethods() {
    }

    @Around("repository() && publicMethods()")
    public Object logDaoExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecutionTime(joinPoint);
    }
}
