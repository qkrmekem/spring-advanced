package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
* 프록시 내부 호출 대안 1
* 자기 자신 주입
* */
@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    /*
    * 스프링애서는 객체를 생성하는 단계와
    * setter를 처리하는 단계가 나뉘어 있어
    * 자기자신을 주입하려면 setter를 활용해야함
    * */
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        log.info("callServiceV1 setter={}", callServiceV1.getClass());
        this.callServiceV1 = callServiceV1;
    }

    public void external() {
        log.info("call external");
        callServiceV1.internal(); // 외부 메서드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
