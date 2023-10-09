package hello.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 클래스 단위로 붙이는 어노테이션
// @Target : 해당 어노테이션이 부착될 수 있는 타입을 지정하는 어노테이션
// 여기서 타입이란, 클래스, 메서드, 생성자 등을 뜻함
@Target(ElementType.TYPE)
// 어노테이션의 라이프사이클을 런타임 시점까지 지정
// @Retention : 어노테이션의 라이프 사이클을 설정
// RetentionPolicy.Source : .java파일일 때까지만 어노테이션이 남아있고, 컴파일 되면 사라짐
// RetentionPolicy.Class : .class파일까지는 어노테이션이 남아있고, 런타임 시점에 클래스로더가 클래스를 읽어오면 사라짐
// RetentionPolicy.Runtime : 런타임 시점에도 어노테이션이 남아있음
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassAop {
}
