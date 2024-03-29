package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/*
* 프록시 패턴과 데코레이터 패턴 모두 프록시를 사용
* 그 둘은 사용하는 의도에 따라 나뉘는데
* 프록시 패턴은 접근 제어(캐시, 권한 확인 등)
 * 데코레이터는 부가 기능 수행(파라미터 값이나 반환 값 수정 등)
* */
@Slf4j
public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.extecute();
        client.extecute();
        client.extecute();
    }

    // 클라이언트 -> subject -> cacheProxy -> realSubject
    // 결론적으로 클라이언트는 인터페이스인 subject를 의존
    @Test
    void cacheProxyTest() {
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
        long start = System.currentTimeMillis();
        client.extecute();
        client.extecute();
        client.extecute();
        long end = System.currentTimeMillis() - start;
        long result = end - start;
        log.info("총 소요 시간 ={}",result);
    }
}
