package hello.proxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/*
* SpringBootApplication은 기본적으로 현재 클래스 파일이 존재하는 패키지와
* 그 하위 패키지에 컴포넌트 스캔을 진행한다.
* 따라서 @Config도 당연히 스프링 빈으로 등록이 된다
* 하지만 여기서는 버전에 따라서 Config를 다르게 설정해주기 위해
* app패키지 하위에만 컴포넌트 스캔을 진행한다.
*
* 알아두면 실무에서도 유용하게 사용할 수 있으니 기억해두자!!!
* */
//@Import(AppV1Config.class)
@Import({AppV1Config.class, AppV2Config.class})
@SpringBootApplication(scanBasePackages = "hello.proxy.app") //주의
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

}
