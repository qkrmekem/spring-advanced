package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/*
* 실행 시점에 전략 주입
* */
@Slf4j
public class ContextV2Test {

    @Test
    void strategyV1(){
        ContextV2 contextV2 = new ContextV2();
        contextV2.execute(new StrategyLogic1());
        contextV2.execute(new StrategyLogic2());
    }

    /*
    * 익명 내부 클래스 사용
    * */
    @Test
    void strategyV2(){
        ContextV2 contextV2 = new ContextV2();
        contextV2.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1");
            }
        });
        contextV2.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2");
            }
        });
    }

    @Test
    void strategyV3(){
        ContextV2 contextV2 = new ContextV2();
        contextV2.execute(() -> log.info("비즈니스 로직1"));
        contextV2.execute(() -> log.info("비즈니스 로직2"));
    }
}
