package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        log.info("helloMethod={}", helloMethod);
        // 결과 : public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        // execution은 위의 결과를 이용해 포인트컷 대상을 찾아냄
    }

    @Test
    @DisplayName("가장 정확하게 매칭되는 포인트컷")
    void exactMethod() {
        // execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터 타입) 예외?)
        // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /*
     '*'은 모든 값이 다 들어올 수 있다는 뜻
     '0'은 아무것도 들어오지 않는다는 뜻
     '..'은 아무런 값이 들어오지 않거나, 하나만 들어오거나 여러개가 들어올 수 있다는 뜻
     * */
    @Test
    @DisplayName("가장 많이 생략한 포인트컷")
    void allMatch() {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("이름만 매칭하는 포인트컷")
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("메서드 이름 일부를 *로 대체")
    void nameMatchStar1() {
        pointcut.setExpression("execution(* hel*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("메서드 이름 일부를 *로 대체")
    void nameMatchStar2() {
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("메서드 이름 매치에 실패하는 테스트")
    void nameMatchFalse() {
        pointcut.setExpression("execution(* nono(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("패키지 이름을 정확하게 매칭하는 포인트컷")
    void packageExactMatch1() {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패키지 이름을 *로 대체")
    void packageExactMatch2() {
        pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패키지 매칭에 실패")
    void packageExactFalse() {
        // MemberServiceImpl의 패키지 경로는 아래와 같음
        // package hello.aop.member
        // 그런데 현재 포인트컷에서 지정한 패키지 경로는
        // hello.aop레벨까지에 존재하는 클래스를 매칭함
        pointcut.setExpression("execution(* hello.aop.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("지정 패키지와 그 하위 패키지까지 매칭")
    void packageMatchSubPackage1() {
        // ..은 지정한 패키지와 그 하위 패키지까지 포함시킴
        pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("지정 패키지와 그 하위 패키지까지 매칭")
    void packageMatchSubPackage2() {
        pointcut.setExpression("execution(* hello.aop..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("정확한 타입으로 매칭")
    void typeExactMatch() {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /*
    * execution은 부모타입을 이용해 매칭이 가능함
    * 그래서 타입 매칭이라고 함
    * */
    @Test
    @DisplayName("부모 타입으로 매칭")
    void typeExactMatchSuperType() {

        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    @Test
    @DisplayName("부모 타입으로 메서드 매칭")
    void typeMatchInternal() throws NoSuchMethodException {

        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");

        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
    }

    /* 자바에서는 부모타입에 자식 타입을 담을 경우 부모 타입에 없는 자식 타입의 메서드 등에 접근하지 못함
     * */
    @Test
    @DisplayName("부모 타입에 없는 메서드로 매칭")
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {

        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");

        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("파라미터 타입으로 매칭")
    void argsMatch() {

        pointcut.setExpression("execution(* *(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("파라미터가 없으므로 매칭 실패")
    void argsMatchNoArgs() {

        pointcut.setExpression("execution(* *())");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("정확히 하나의 모든 타입 파라미터 허용")
    void argsMatchStar() {

        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("파라미터 개수,타입과 상관없이 매칭")
    void argsMatchAll() {

        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("특정 파라미터는 정확히 매칭, 그 외는 모든 타입 허용")
    void argsMatchComplex() {

        pointcut.setExpression("execution(* *(String, ..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
