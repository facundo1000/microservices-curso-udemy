package com.demo.springbootserviceconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class SpringbootServiceConfigServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootServiceConfigServerApplication.class, args);
  }
}
