package com.demo.springbootserviceitem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

// @EnableCircuitBreaker
@EnableEurekaClient
// @RibbonClient(name = "service-product") // Load Balancer
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients // Simple client generator
@EntityScan({"com.demo.commons.entities"})
public class SpringbootServiceItemApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringbootServiceItemApplication.class, args);
  }
}
