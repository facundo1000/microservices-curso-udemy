package com.demo.springbootservicegatewayserver.filters.factory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ExampleGatewayFilterFactory
    extends AbstractGatewayFilterFactory<ExampleGatewayFilterFactory.Config> {

  public ExampleGatewayFilterFactory() {

    super(Config.class);
  }

  /**
   * Todo lo que esta por encima del "return chain.filter" seria el PRE. Todo lo que venga despues
   * del "return chain.filter" seria el POST. Utilizando el metodo then.
   */
  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      log.info("Ejecutando PRE Gateway Filter Factory: " + config.messagge);

      return chain
          .filter(exchange)
          .then(
              Mono.fromRunnable(
                  () -> {
                    Optional.ofNullable(config.cookieValue)
                        .ifPresent(
                            cookie -> {
                              exchange
                                  .getResponse()
                                  .addCookie(
                                      ResponseCookie.from(config.cookieName, cookie).build());
                            });
                    log.info("Ejecutando POST Gateway Filter Factory: " + config.messagge);
                  }));
    };
  }

  // Metodo que se utiliza para modificar el nombre de la cookie
  @Override
  public String name() {
    return "ExampleCookie";
  }

  // Metodo que se utiliza para configurar el orden de los atributos de la cookie
  @Override
  public List<String> shortcutFieldOrder() {
    return Arrays.asList("messagge", "cookieName", "cookieValue");
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  public static class Config {
    private String messagge;
    private String cookieValue;
    private String cookieName;
  }
}
