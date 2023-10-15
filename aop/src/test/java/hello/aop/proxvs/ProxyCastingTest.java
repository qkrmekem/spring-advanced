package hello.aop.proxvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

public class ProxyCastingTest {

    @Test
    void jkdProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // 프록시 팩토리는 기본적으로 jdk 동적 프록시와 cglib 둘 다 사용
        proxyFactory.setProxyTargetClass(false); // jdk동적 프록시 사용

        // 프록시를 인터페이스로 캐스팅 - 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // jdk 동적 프록시를 구현체로 캐스팅 시도 - 실패, ClassCastException 예외 발생
        // jdk 동적 프록시와 구현체 모두 MemberService(인터페이스)를 구현한 것으로 두 객체간에 부모 자식관계가 성립되지 않음
        Assertions.assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });
    }

    @Test
    void cglib() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // 프록시 팩토리는 기본적으로 jdk 동적 프록시와 cglib 둘 다 사용
        proxyFactory.setProxyTargetClass(true); // cglib만 사용

        // 프록시를 인터페이스로 캐스팅 - 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // cglib 프록시를 구현체로 캐스팅 시도 - 성공
        // cglib 프록시가 구현체를 상속 받아 생성되므로 부모 자식 관계 성립
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }
}
