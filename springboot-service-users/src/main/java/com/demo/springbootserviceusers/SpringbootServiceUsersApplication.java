package com.demo.springbootserviceusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan({"com.demo.springbootserviceuserscommons.entity"})
@SpringBootApplication
public class SpringbootServiceUsersApplication {

  public static void main(String[] args) {

    SpringApplication.run(SpringbootServiceUsersApplication.class, args);
  }
}
