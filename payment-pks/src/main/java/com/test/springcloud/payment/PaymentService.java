package com.test.springcloud.payment;

import java.text.MessageFormat;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
  private final AppProps appProps;

  public Order pay(Order order) {
    this.calcCost(order);
    this.processPayment(order);
    return order;
  }

  private void calcCost(Order order) {
    order.getItems()
        .forEach(e -> e.setCost(e.getQty() * e.getUnitPrice()));

    double totalCost = order.getItems().stream()
        .map(Item::getCost)
        .reduce(0d, Double::sum);
    order.setTotalCost(totalCost);

    long costLimit = this.appProps.getCost().getLimit();
    if (totalCost > costLimit) {
      String msg = MessageFormat.format("Order value [{0}] exceeds threshold [{1}]", totalCost, costLimit);
      throw new PaymentException(msg);
    }
  }

  private void processPayment(Order order) {
    if (StringUtils.isBlank(order.getCreditCard())) {
      throw new PaymentException("Payment cannot be processed without CreditCard number");
    }

    log.info("Processing payment [{}] for order [{}]", order.getTotalCost(), order.getOrderId());
    order.setPaymentRef(UUID.randomUUID().toString());
  }
}
