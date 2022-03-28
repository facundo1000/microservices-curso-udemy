package com.demo.springbootserviceitem;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Esta clase no se usa fue creada para ver el uso del Circuit Breaker de manera programatica, pero
 * luego se pasa a la utilizacion de application.yml para la configuracion del mismo. Utilizar el
 * archivo.yml o el archivo.properties es la manaera correcta.
 */
@Configuration
public class AppConfig {

  @Bean("clientRest")
  @LoadBalanced
  public RestTemplate registerRestTemplate() {
    return new RestTemplate();
  }

  @Bean
  public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
    return factory ->
        factory.configureDefault(
            id ->
                new Resilience4JConfigBuilder(id)
                    .circuitBreakerConfig(
                        CircuitBreakerConfig.custom()
                            .slidingWindowSize(10)
                            .failureRateThreshold(50)
                            .waitDurationInOpenState(Duration.ofSeconds(10L))
                            .permittedNumberOfCallsInHalfOpenState(5)
                            .slowCallRateThreshold(50L)
                            .slowCallDurationThreshold(Duration.ofSeconds(2L))
                            .build())
                    .timeLimiterConfig(
                        TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(6L)).build())
                    .build());
  }
}
