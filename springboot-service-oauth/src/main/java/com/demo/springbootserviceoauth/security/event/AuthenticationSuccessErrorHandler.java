package com.demo.springbootserviceoauth.security.event;

import brave.Tracer;
import com.demo.springbootserviceoauth.services.IUserService;
import com.demo.springbootserviceuserscommons.entity.User;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

  @Autowired private IUserService userService;
  @Autowired private Tracer tracer;

  @Override
  public void publishAuthenticationSuccess(Authentication authentication) {
    if (authentication.getDetails() instanceof WebAuthenticationDetails) {
      return;
    }
    UserDetails user = (UserDetails) authentication.getPrincipal();
    String messagge = "Success Login: " + user.getUsername();
    System.out.println(messagge);
    log.info(messagge);

    User usuario = userService.findByUsername(authentication.getName());
    if (usuario.getTries() != null && usuario.getTries() > 0) {
      usuario.setTries(0);
      userService.update(usuario, usuario.getId());
    }
  }

  @Override
  public void publishAuthenticationFailure(
      AuthenticationException exception, Authentication authentication) {
    String messagge = "Error en el Login: " + exception.getMessage();
    System.out.println(messagge);
    log.info(messagge);

    try {

      StringBuilder errors = new StringBuilder();
      errors.append(messagge);

      User user = userService.findByUsername(authentication.getName());
      if (user.getTries() == null) {
        user.setTries(0);
      }
      log.info("Intentos actual es de: " + user.getTries());

      user.setTries(user.getTries() + 1);

      log.info("Intentos despues es de: " + user.getTries());

      errors.append(" - Intentos de log-in: " + user.getTries());

      if (user.getTries() >= 3) {
        String errorTryMax = " User " + user.getUsername() + " disable cause maximum tries reached ";
        log.error(errorTryMax);
        errors.append(errorTryMax);
        user.setEnable(false);
      }
      userService.update(user, user.getId());

      tracer.currentSpan().tag("error.mensaje", errors.toString());
    } catch (FeignException feignException) {
      log.error(
          String.format("The username %s does not exist in the system", authentication.getName()));
    }
  }
}
