package com.springcloud.inventory.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProps {
  private final Qty qty = new Qty();

  @Data
  public static class Qty {
    /**
     * Maximum quantity allowed for each item.
     */
    private long limit;
    /**
     * Adjustment allowed for each item quantity.
     */
    private long adjustment;
    /**
     * Flag to indicate if quantity check is enabled.
     */
    private boolean enabled;
  }
}
