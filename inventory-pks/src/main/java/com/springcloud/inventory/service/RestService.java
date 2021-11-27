package com.springcloud.inventory.service;

import com.springcloud.inventory.Order;
import com.springcloud.inventory.OrderWithUrl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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

  @CircuitBreaker(name = "SLOW", fallbackMethod = "fallbackSlow")
  public Order makeRestCallSlow(OrderWithUrl input) {
    return this.restHelper.makeRestCall(input.getUrl(), input.getOrder());
  }

  public Order fallbackSlow(OrderWithUrl input, Exception e) {
    log.error("In fallbackSlow; [{}]", e.getMessage());
    Order order = input.getOrder();
    order.setPaymentRef("DEFAULT FALLBACK SLOW");
    return order;
  }

  @CircuitBreaker(name = "FAST", fallbackMethod = "fallbackFast")
  public Order makeRestCallFast(OrderWithUrl input) {
    return this.restHelper.makeRestCall(input.getUrl(), input.getOrder());
  }

  public Order fallbackFast(OrderWithUrl input, Exception e) {
    log.error("In fallbackFast; [{}]", e.getMessage());
    Order order = input.getOrder();
    order.setPaymentRef("DEFAULT FALLBACK FAST");
    return order;
  }
}
