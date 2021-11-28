package com.springcloud.inventory.config;

import javax.annotation.PostConstruct;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ResilienceConfig {
  private final CircuitBreakerRegistry circuitBreakerRegistry;
  private final RetryRegistry retryRegistry;

  @PostConstruct
  public void init() {
    CircuitBreaker slowService = this.circuitBreakerRegistry.circuitBreaker("slowService");
    slowService.getEventPublisher()
        .onStateTransition(this::logIt);

    CircuitBreaker fastService = this.circuitBreakerRegistry.circuitBreaker("fastService");
    fastService.getEventPublisher()
        .onStateTransition(this::logIt);
    log.info("Registered EventListeners for CircuitBreakers...");
  }

  private void logIt(CircuitBreakerOnStateTransitionEvent event) {
    String fromState = event.getStateTransition().getFromState().name();
    String toState = event.getStateTransition().getToState().name();
    String cbName = event.getCircuitBreakerName();
    log.info("CB [{}] StateChange - [{}] -> [{}]", cbName, fromState, toState);
  }
}
