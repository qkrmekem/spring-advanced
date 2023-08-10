package hello.advanced.trace.template;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0(){
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        // 비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        // 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("result={}",resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        // 비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        // 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("result={}",resultTime);
    }

    /*
    * 템플릿 메서드 패턴 적용
    * 추상템플릿을 상속받은 클래스를 정의해야 한다는 단점이 있음
    * */
    @Test
    void templateMethodV1() {
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();

        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();

    }

    /*
    * 익명 내부 클래스 활용
    * 객체 인스턴스를 생성하면서 동시에
    * 상속 받은 자식 클래스를 정의
    * SubClassLogic1처럼 직접 지정하는 이름이 없고 클래스 내부에
    * 선언되는 클래스여서 익명 내부 클래스라고 한다.
    * */
    @Test
    void templateMethodV2() {
        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        // TemplateMethodTest$1라는 이름의
        // 익명 내부 클래스를 생성해줌
        log.info("클래스 이름1={}",template1.getClass());
        template1.execute();

        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        log.info("클래스 이름2={}",template1.getClass());
        template2.execute();
    }

    @Test
    void templateMethodV3() {
        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        // TemplateMethodTest$1라는 이름의
        // 익명 내부 클래스를 생성해줌
        log.info("클래스 이름1={}",template1.getClass());
        template1.execute();

        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        log.info("클래스 이름2={}",template1.getClass());
        template2.execute();
    }
}
