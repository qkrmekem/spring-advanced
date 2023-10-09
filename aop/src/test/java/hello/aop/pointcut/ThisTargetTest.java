package hello.aop.pointcut;

import hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/*
* application.properties
* spring.aop.proxy-target-class=true : 기본값, 무조건 CGLIB 사용
* spring.aop.proxy-target-class=false : 인터페이스가 있을경우 JDK 동적 프록시, 없을 경우 CGLIB 사용
* */
@Slf4j
@Import(ThisTargetTest.ThisTargetAspect.class)
//@SpringBootTest(properties = "spring.aop.proxy-target-class=false") // JDK 동적프록시
@SpringBootTest(properties = "spring.aop.proxy-target-class=true") // CGLIB
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {

        // 부모타입 허용
        @Around("this(hello.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // 부모타입 허용
        @Around("target(hello.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // JDK 동적 프록시를 사용할 경우 포인트컷 매칭에 실패함
        // 그 이유는 스프링 빈으로 등록된 프록시 객체와
        // MembserServiceImpl 두 객체 모두 MembserService를 구현한 구체 클래스로
        // MembserServiceImpl과 프록시 객체는 부모 자식 관계가 아니므로 매칭되지 않는다.
        @Around("this(hello.aop.member.MemberServiceImpl)")
        public Object doThis(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(hello.aop.member.MemberServiceImpl)")
        public Object doTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }


}
