package com.demo.springbootserviceoauth.security;

import com.demo.springbootserviceoauth.services.IUserService;
import com.demo.springbootserviceuserscommons.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InfoAditionalToken implements TokenEnhancer {

  @Autowired private IUserService userService;

  @Override
  public OAuth2AccessToken enhance(
      OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    Map<String, Object> info = new HashMap<>();
    User user = userService.findByUsername(authentication.getName());
    info.put("Name: ", user.getName());
    info.put("Lastname: ", user.getLastname());
    info.put("Email: ", user.getEmail());
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
    return accessToken;
  }
}
