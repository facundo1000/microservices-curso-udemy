package com.demo.springbootserviceoauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;
import java.util.Base64;

@RefreshScope
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired private BCryptPasswordEncoder passwordEncoder;

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private InfoAditionalToken aditionalToken;

  @Autowired private Environment environment;

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients
        .inMemory()
        .withClient(environment.getProperty("config.security.oauth.client.id"))
        .secret(
            passwordEncoder.encode(environment.getProperty("config.security.oauth.client.secret")))
        .scopes("read", "write") // alcance de la aplicacion cliente
        .authorizedGrantTypes("password", "refresh_token")
        .accessTokenValiditySeconds(3600)
        .refreshTokenValiditySeconds(3600);
  }

  // endpoint - POST -> /oauth/token
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    tokenEnhancerChain.setTokenEnhancers(Arrays.asList(aditionalToken, accessTokenConverter()));
    endpoints
        .authenticationManager(authenticationManager)
        .tokenStore(tokenStore())
        .accessTokenConverter(accessTokenConverter())
        .tokenEnhancer(tokenEnhancerChain);
  }

  // para crear el token y almacenarlo
  @Bean
  public JwtTokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
    tokenConverter.setSigningKey(
        Base64.getEncoder()
            .encodeToString(environment.getProperty("config.security.oauth.jwt.key").getBytes()));
    return tokenConverter;
  }
}
