package hello.advanced.app.v1;

import hello.advanced.trace.hellotrace.HelloTraceV1;
import hello.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {
    private final HelloTraceV1 trace;

    public void save(String itemId) {
        TraceStatus status = null;
        try{
            status = trace.begin("OrderRepository.request()");
            // 저장 로직
            if (itemId.equals("ex")) {
                throw new IllegalArgumentException("예외 발생!");
            }

            sleep(1000); // 1초간 상품 저장하는 가정
            trace.end(status);
        } catch (Exception ex){
            trace.exception(status, ex);
            throw ex; // 예외를 꼭 다시 던져주어야 한다.
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
