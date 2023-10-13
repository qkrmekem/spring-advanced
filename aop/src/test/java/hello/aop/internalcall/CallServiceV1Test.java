package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV1Test {

    @Autowired
    CallServiceV1 callServiceV1;

    /*
    * 내부 호출 발생
    * */
    @Test
    void external() {
        callServiceV1.external();
    }

    /*
    * 내부 호출 발생 X
    * */
    @Test
    void internal() {
        callServiceV1.internal();
    }
}