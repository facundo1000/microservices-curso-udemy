package com.demo.springbootservicioproductos.model.controllers;

import com.demo.commons.entities.Product;
import com.demo.springbootservicioproductos.model.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
public class ProductController {
  /** Para saber cual es el puerto se debe utilizar la clase Environmet */
  @Autowired private Environment environment;

  @Autowired private IProductService iProductService;

  @Value("${server.port}")
  private Integer port;

  @GetMapping("/list")
  public List<Product> list() {
    return iProductService.findAll().stream()
        .map(
            product -> {
              product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
              // product.setPort(port);
              return product;
            })
        .collect(Collectors.toList());
  }

  @GetMapping("/list/product/{id}")
  public Product getProductById(@PathVariable("id") Long id) throws InterruptedException {
    if (id.equals(10L)) {
      throw new IllegalStateException("Product NOT Found");
    }
    if (id.equals(7L)) {
      TimeUnit.SECONDS.sleep(5L);
    }
    Product product = iProductService.findById(id);
    product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
    // product.setPort(port);
    return product;
  }

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Product create(@RequestBody Product product) {
    return iProductService.save(product);
  }

  @PutMapping("/update/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public Product edit(@RequestBody Product product, @PathVariable("id") Long id) {
    Product productDb = iProductService.findById(id);
    productDb.setName(product.getName());
    productDb.setPrice(product.getPrice());
    productDb.setCreateAt(product.getCreateAt());
    return iProductService.save(productDb);
  }

  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable("id") Long id) {
    Product productDb = iProductService.findById(id);
    iProductService.deleteById(id);
  }
}
