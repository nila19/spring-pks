package com.springcloud.inventory.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropsWatcher {
  private final AppProps appProps;
  private AppProps.Qty oldQty;

  @Scheduled(fixedDelay = 10000)
  public void watchProps() {
    AppProps.Qty newQty = this.appProps.getQty();
    log.info("Qty => " + newQty);

    if (this.oldQty == null || !this.oldQty.canEqual(newQty)) {
      log.info("Qty CHANGED => " + newQty);
      this.oldQty = newQty;
    }
  }
}
