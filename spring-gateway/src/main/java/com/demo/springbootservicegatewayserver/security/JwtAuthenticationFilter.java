package com.demo.springbootservicegatewayserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

  @Autowired private ReactiveAuthenticationManager authenticationManager;
  /**
   * Mediante el "exchange" podemos obtener el 'request' y podemos obtener el token que nos estan
   * enviando desde el microservicio, desde postman, desde algun cliente en las cabeceras. Tenemos
   * que obtener ese token, tenemos que limpiarlo. Tiene que pasar por un flujo, procesarlo, quitar
   * la palabra 'bearer'.Y al comienzo preguntar si tiene el token.
   */
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
        .filter(authHeader -> authHeader.startsWith("Bearer "))
        .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
        .map(token -> token.replace("Bearer ", ""))
        .flatMap(
            token ->
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(null, token)))
        .flatMap(
            authentication ->
                chain
                    .filter(exchange)
                    .contextWrite(
                        ReactiveSecurityContextHolder.withAuthentication(authentication)));
  }
}
