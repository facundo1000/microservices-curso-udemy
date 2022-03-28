package com.demo.springbootserviceoauth.services;

import com.demo.springbootserviceuserscommons.entity.User;

public interface IUserService {
  public User findByUsername(String username);

  public User update(User user, Long id);
}
