package com.demo.springbootservicegatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SpringbootServiceGatewayServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootServiceGatewayServerApplication.class, args);
  }
}
