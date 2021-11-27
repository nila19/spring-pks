package com.test.springcloud.payment;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
  private static final String JSON = APPLICATION_JSON_VALUE;

  private final PaymentService paymentService;
  private boolean enabled = true;

  @PostMapping(value = "/process", consumes = JSON, produces = JSON)
  public ResponseEntity<Order> process(@RequestBody Order order) {
    if (!this.enabled) {
      throw new PaymentException("Payment processing is disabled!!!");
    }
    return ResponseEntity.ok(this.paymentService.pay(order));
  }

  @PostMapping(value = "/enable")
  public ResponseEntity<Boolean> enable() {
    this.enabled = true;
    return ResponseEntity.ok(this.enabled);
  }

  @PostMapping(value = "/disable")
  public ResponseEntity<Boolean> disable() {
    this.enabled = false;
    return ResponseEntity.ok(this.enabled);
  }
}
