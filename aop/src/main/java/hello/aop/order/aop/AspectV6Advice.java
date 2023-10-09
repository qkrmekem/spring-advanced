package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    /*
    * @Around의 경우에만 ProceedingJoinPoint를 사용할 수 있음
    * ProceedingJoinPoint는
    * */
//    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
//    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        try {
//            // @Before
//            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
//            Object result = joinPoint.proceed();
//            // @AfterReturning
//            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
//            return result;
//        } catch (Exception e) {
//            // @AfterThrowing
//            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
//            throw e;
//        } finally{
//            // @After
//            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
//        }
//    }

    // 조인포인트 실행 전의 로직만 작성할 때
    // joinPoint.proceed 이전의 로직
    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    // 메서드 실행이 정상적으로 반환될 때 실행
    // joinPoint.proceed 이후의 로직
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.allOrder()", returning = "result")
    public void doReturn2(JoinPoint joinPoint, char result) {
        log.info("[return2] {} return={}", joinPoint.getSignature(), result);
    }

    // 메서드 실행이 예외를 반환할 때 실행
    // catch와 같은 역할
    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", ex);
    }

    // finally 부분의 로직과 같은 역할을 함
    @After(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}",joinPoint.getSignature());
    }
}
