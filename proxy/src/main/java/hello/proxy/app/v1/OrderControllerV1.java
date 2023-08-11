package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// 스프링은 @Controller 또는 @RequestMapping이 있으면 스프링 컨트롤러로 인식
// @RequestMapping과 @ResponseBody는 인터페이스에 사용해도 됨
// 여기서는 빈을 수동으로 등록한다고 했으므로 @Controller 같은
// 컴포넌트 어노테이션을 사용하지 않음
// @RequestMapping은 컴포넌트 어노테이션이 아님
@RequestMapping
@ResponseBody
public interface OrderControllerV1 {

    // 로그 적용시에 사용
    @GetMapping("/v1/request")
    // 구현체에서는 @RequestParam("itemId")을 안넣어도 되지만
    // 버전에 따라서 인터페이스에서는 이게 안먹힐 수도 있으므로
    // 인터페이스에서는 @RequestParam("itemId")와 같이 파라미터를 명시 해줘야함
    String request(@RequestParam("itemId") String itemId);

    // 로그 미적용 시 사용
    @GetMapping("v1/no-log")
    String noLog();
}
