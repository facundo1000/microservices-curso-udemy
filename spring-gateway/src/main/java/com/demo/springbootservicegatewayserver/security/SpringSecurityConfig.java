package com.demo.springbootservicegatewayserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {

  @Autowired private JwtAuthenticationFilter authenticationFilter;

  @Bean
  public SecurityWebFilterChain configure(ServerHttpSecurity http) {
    return http.authorizeExchange()
        .pathMatchers("/api/security/oauth/**")
        .permitAll()
        .pathMatchers(
            HttpMethod.GET,
            "/api/product/list",
            "/api/items/list",
            "/api/users/user",
            "/api/items/item/{id}/quantity/{quantity}",
            "/api/product/list/product/{id}")
        .permitAll()
        .pathMatchers(HttpMethod.GET, "/api/users/user/{id}")
        .hasAnyRole("ADMIN", "USER")
        .pathMatchers("/api/product/**", "/api/items/**", "/api/user/user/**")
        .hasAuthority("ADMIN")
        // .hasRole("ADMIN")
        .anyExchange()
        .authenticated()
        .and()
        .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .csrf()
        .disable()
        .build();
  }
}
