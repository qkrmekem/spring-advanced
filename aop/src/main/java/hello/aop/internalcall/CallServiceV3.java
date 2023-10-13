package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/*
* 프록시 내부 호출 대안 3
* 구조 변경
* internal 메서드를 별도의 클래스로 분리
* */
@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {

    private final InternalService internalService;

    public void external() {
        log.info("call external");
        internalService.internal(); // 외부 메서드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
