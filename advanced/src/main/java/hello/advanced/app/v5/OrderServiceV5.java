package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final TraceTemplate template;

    public OrderServiceV5(OrderRepositoryV5 orderRepository, LogTrace logTrace) {
        this.orderRepository = orderRepository;
        this.template = new TraceTemplate(logTrace);
    }

    public void orderItem(String itemId) {

        // 제네릭을 사용하는 인터페이스 TraceCallback의 반환값을 컴파일러가
        // 추론하기 위해서는 return을 사용해서 반환 타임을 추론할 수 있도록 명시해줘야함
        template.execute("OrderService.orderItem()", () -> {
            orderRepository.save(itemId);
            return null;
        });

    }
}
