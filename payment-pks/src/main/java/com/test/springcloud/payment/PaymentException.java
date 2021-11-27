package com.test.springcloud.payment;

public class PaymentException extends RuntimeException {
  private static final long serialVersionUID = 3050929978823738127L;

  public PaymentException(String msg) {
    super(msg);
  }
}
