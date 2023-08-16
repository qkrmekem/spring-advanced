package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    // 타겟의 정보는 MethodInvocation안에 이미 들어가 있음
    // 프록시 팩토리를 생성하는 시점에 타겟 정보를 넘겨야함
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

//        Object result = method.invoke(target, args);
        Object result = invocation.proceed();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy종료 resultTime={}",resultTime);
        return result;
    }
}
