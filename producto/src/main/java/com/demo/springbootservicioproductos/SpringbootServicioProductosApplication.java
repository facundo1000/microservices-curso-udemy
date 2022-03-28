package com.demo.springbootservicioproductos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.demo.commons.entities"})
public class SpringbootServicioProductosApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootServicioProductosApplication.class, args);
  }
}
