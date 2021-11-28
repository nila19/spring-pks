package com.springcloud.inventory.service;

import java.text.MessageFormat;
import java.util.UUID;
import javax.annotation.PostConstruct;

import com.springcloud.inventory.InventoryException;
import com.springcloud.inventory.Item;
import com.springcloud.inventory.Order;
import com.springcloud.inventory.config.AppProps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
//@RefreshScope
public class InventoryService {
  private final AppProps appProps;

  @PostConstruct
  public void init() {
    AppProps.Qty qty = this.appProps.getQty();
    log.info("Qty Limit = [{}], Adjustment = [{}]", qty.getLimit(), qty.getAdjustment());
  }

  public Order process(Order order) {
    this.checkInventory(order);
    this.adjustInventory(order);
    return order;
  }

  private void checkInventory(Order order) {
    long limit = this.appProps.getQty().getLimit();
    boolean enabled = this.appProps.getQty().isEnabled();

    boolean largeOrder = order.getItems().stream()
        .map(Item::getQty)
        .anyMatch(e -> e > limit);

    if (enabled && largeOrder) {
      String msg = MessageFormat.format("Item qty exceeds threshold [{0}]", limit);
      throw new InventoryException(msg);
    }
  }

  private void adjustInventory(Order order) {
    log.info("Adjusting inventory; Order [{}]", order.getOrderId());
    order.setInventoryRef(UUID.randomUUID().toString());
  }
}
