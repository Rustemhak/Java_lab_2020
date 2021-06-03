package ru.itis.javalab.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.javalab.redis.RedisService;

@Component
@Aspect
public class MethodCountAspect {
    @Autowired
    private RedisService redisService;

    @Pointcut("execution(public * ru.itis.javalab.services..*(..))")
    public void allServicesPointcut() {
    }

    @Around("allServicesPointcut()")
    public Object addCount(ProceedingJoinPoint joinPoint) {
        StringBuilder methodContract = new StringBuilder("public ");
        methodContract.append(joinPoint.getTarget().getClass().getName()).append(" ");
        methodContract.append(joinPoint.getSignature().getName()).append(" ");
        Object[] args = joinPoint.getArgs();
        if (args.length == 0) {
            methodContract.append("();");
        } else {
            for (Object arg : args) {
                Class<?> argumentClass = arg.getClass();
                String argumentClassName = argumentClass.getName();
                String argumentSimpleClassName = argumentClass.getSimpleName().toLowerCase();
                methodContract
                        .append(", ")
                        .append(argumentClassName)
                        .append(" ")
                        .append(argumentSimpleClassName);
            }
            methodContract.append(");");
        }
        String methodContractToString = methodContract.toString();
        if (redisService.hasKey(methodContractToString)) {
            int count = Integer.parseInt(redisService.getElementByKey(methodContractToString));
            count++;
            redisService.set(methodContractToString, Integer.toString(count));
        } else {
            redisService.set(methodContractToString, "1");
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            return null;
        }
    }
}
