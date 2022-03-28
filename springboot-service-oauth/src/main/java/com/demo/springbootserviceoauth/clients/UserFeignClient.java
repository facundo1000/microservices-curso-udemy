package com.demo.springbootserviceoauth.clients;

import com.demo.springbootserviceuserscommons.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "service-users")
public interface UserFeignClient {
  @GetMapping("/user/search/search-username")
  public User findByUsername(@RequestParam String username);

  @PutMapping("/user/{id}")
  public User update(@RequestBody User user, @PathVariable Long id);
}
