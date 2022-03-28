package com.demo.springbootserviceoauth.services;

import brave.Tracer;
import com.demo.springbootserviceoauth.clients.UserFeignClient;
import com.demo.springbootserviceuserscommons.entity.User;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements IUserService, UserDetailsService {

  @Autowired private UserFeignClient client;
  @Autowired private Tracer tracer;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    try {

      User user = client.findByUsername(username);

      List<GrantedAuthority> authorities =
          user.getRoles().stream()
              .map(role -> new SimpleGrantedAuthority(role.getName()))
              .peek(authority -> log.info("Role: " + authority.getAuthority()))
              .collect(Collectors.toList());

      log.info("Authenticated User: " + username);

      return new org.springframework.security.core.userdetails.User(
          user.getUsername(), user.getPassword(), user.getEnable(), true, true, true, authorities);

    } catch (FeignException e) {
      String error = "Log-in error, user " + username + " not found in the system";
      log.error(error);
      tracer.currentSpan().tag("error.mensaje", error + ":" + e.getMessage());
      throw new UsernameNotFoundException(
          String.format("Log-in error, user %s not found in the system", username));
    }
  }

  @Override
  public User findByUsername(String username) {
    return client.findByUsername(username);
  }

  @Override
  public User update(User user, Long id) {
    return client.update(user, id);
  }
}
