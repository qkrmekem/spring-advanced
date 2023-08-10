package hello.advanced.trace.callback;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;


public class TraceTemplate {

    private final LogTrace trace;

    public TraceTemplate(LogTrace trace) {
        this.trace = trace;
    }

    // <T> 제네릭 타입을 선언할 때 사용
    // T 선언된 타입을 실제로 사용하는 부분에서 사용
    // <T>는 클래스 단위나 메서드 단위에서 붙을 수 있다.
    public <T> T execute(String message, TraceCallback<T> callback) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);
            // 로직 호출
            T result = callback.call();

            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

}
