package com.demo.springbootservicegatewayserver.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@Slf4j
public class ExampleGlobalFilter implements GlobalFilter, Ordered {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
      //Metodos Pre
    log.info("Ejecutando Filtro Pre");
    exchange.getRequest().mutate().headers(h -> h.add("token", "123456"));
    return chain
        .filter(exchange)
            // Metodos Post
        .then(
            Mono.fromRunnable(
                () -> {
                  log.info("Ejecutando Filtro Post");
                  Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token"))
                      .ifPresent(
                          value -> {
                            exchange.getResponse().getHeaders().add("token", value);
                          });
                  exchange
                      .getResponse()
                      .getCookies()
                      .add("color", ResponseCookie.from("color", "red").build());
                  // exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
                }));
  }

  @Override
  public int getOrder() {
    return 10;
  }
}
