package hello.aop.proxvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import hello.aop.proxvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) // jdk 동적 프록시 사용
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"}) // cglib 사용
@SpringBootTest // cglib가 기본적으로 사용됨
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go() {
        log.info("memberService class={}", memberService.getClass());
        log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }
}
