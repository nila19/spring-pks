package com.test.springcloud.payment;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProps {
  private final Cost cost = new Cost();

  @Data
  public static class Cost {
    /**
     * Maximum cost allowed for each item in the order.
     */
    private long limit;
  }
}
