package com.springcloud.inventory.service;

import com.springcloud.inventory.Order;
import com.springcloud.inventory.OrderWithUrl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestService {
  private final RestHelper restHelper;

  public Order makeRestCall(OrderWithUrl input) {
    return this.restHelper.makeRestCall(input.getUrl(), input.getOrder());
  }

  @CircuitBreaker(name = "slowService", fallbackMethod = "fallbackSlow")
  Order makeRestCallSlow(OrderWithUrl input) throws Exception {
    return this.restHelper.makeRestCall(input.getUrl(), input.getOrder());
  }

  private Order fallbackSlow(OrderWithUrl input, Exception e) throws Exception {
    log.error("In fallbackSlow; [{}]", e.getMessage());
    throw e;
  }

  @CircuitBreaker(name = "fastService", fallbackMethod = "fallbackFast")
  @Retry(name = "fastService", fallbackMethod = "fallbackFast")
  public Order makeRestCallFast(OrderWithUrl input) {
    return this.restHelper.makeRestCall(input.getUrl(), input.getOrder());
  }

  private Order fallbackFast(OrderWithUrl input, Exception e) {
    log.error("In fallbackFast; [{}]", e.getMessage());
    Order order = input.getOrder();
    order.setPaymentRef("DEFAULT FALLBACK FAST");
    return order;
  }
}
