package com.springcloud.inventory.service;

import com.springcloud.inventory.Order;
import com.springcloud.inventory.OrderWithUrl;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Inventory2Service {
  private final RestService restService;

  @Retry(name = "slowService", fallbackMethod = "fallbackSlow")
  public Order makeRestCallSlow(OrderWithUrl input) throws Exception {
    return this.restService.makeRestCallSlow(input);
  }

  private Order fallbackSlow(OrderWithUrl input, Exception e) {
    log.error("In fallbackSlow @ Retry; [{}]", e.getMessage());
    Order order = input.getOrder();
    order.setPaymentRef("DEFAULT FALLBACK SLOW AFTER RETRY");
    return order;
  }

  @Retry(name = "slowService", fallbackMethod = "fallback")
  public int calculate(int a, int b) {
    log.info("Trying to execute...");
    throw new RuntimeException("Calculation failed...");
  }

  private int fallback(int a, int b, Exception e) {
    log.info("Falling back...");
    return 100;
  }
}
