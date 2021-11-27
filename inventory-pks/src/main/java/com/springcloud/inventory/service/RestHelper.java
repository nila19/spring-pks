package com.springcloud.inventory.service;

import com.springcloud.inventory.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestHelper {
  private final RestTemplate restTemplate;

  public Order makeRestCall(String url, Order order) {
    log.info("Making REST call [{}] - [{}]", order.getOrderId(), url);
    try {
      ResponseEntity<Order> response = this.restTemplate.postForEntity(url, order, Order.class);
      return response.getBody();
    } catch (Exception e) {
      log.error("RestHelper failed.. [{}]", e.getMessage());
      throw e;
    }
  }
}
