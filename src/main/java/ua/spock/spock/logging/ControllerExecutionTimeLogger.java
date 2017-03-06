package ua.spock.spock.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ControllerExecutionTimeLogger extends ExecutionTimeLogger{


    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    private void controller() {}

    @Pointcut("execution(public * *(..))")
    public void publicMethods() {
    }

    @Around("controller() && publicMethods()")
    public Object logControllerExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
       return logExecutionTime(joinPoint);
    }
}
