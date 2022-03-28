package com.demo.springbootserviceitem.controllers;

import com.demo.commons.entities.Product;
import com.demo.springbootserviceitem.models.Item;

import com.demo.springbootserviceitem.service.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RefreshScope
@RestController
@Slf4j
public class ItemController {
  @Autowired private CircuitBreakerFactory factory;

  @Qualifier("serviceFeign")
  @Autowired
  private ItemService itemService;

  @Autowired private Environment environment;

  @Value("${configuracion.texto}")
  private String text;

  @GetMapping("/list")
  public List<Item> list(
      @RequestParam(name = "nombre", required = false) String nombre,
      @RequestHeader(name = "token-request", required = false) String token) {
    System.out.println(nombre);
    System.out.println(token);
    return itemService.findAll();
  }

  // @HystrixCommand(fallbackMethod = "alternateMethod")
  @GetMapping("/item/{id}/quantity/{quantity}")
  public Item getItemQuantityById(
      @PathVariable("id") Long id, @PathVariable("quantity") Integer quantity) {
    return factory
        .create("items")
        .run(() -> itemService.findById(id, quantity), e -> alternateMethod(id, quantity, e));
  }

  // Handler de creacion de un item
  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Product create(@RequestBody Product product) {
    return itemService.save(product);
  }

  // Handler Actualizacion de un Item
  @PutMapping("/update/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Product update(@RequestBody Product product, @PathVariable Long id) {
    return itemService.update(product, id);
  }
  // Handler de eliminacion de item x ID
  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    itemService.delete(id);
  }

  // Utilizacion de @CircuitBreaker
  @CircuitBreaker(name = "items", fallbackMethod = "alternateMethod")
  @GetMapping("/item2/{id}/quantity/{quantity}")
  public Item getItemQuantityById2(
      @PathVariable("id") Long id, @PathVariable("quantity") Integer quantity) {
    return itemService.findById(id, quantity);
  }

  // Utilizacion de @TimeLimiter
  @CircuitBreaker(name = "items", fallbackMethod = "alternateMethod2")
  @TimeLimiter(name = "items", fallbackMethod = "alternateMethod2")
  @GetMapping("/item3/{id}/quantity/{quantity}")
  public CompletableFuture<Item> getItemQuantityById3(
      @PathVariable("id") Long id, @PathVariable("quantity") Integer quantity) {
    return CompletableFuture.supplyAsync(() -> itemService.findById(id, quantity));
  }

  public Item alternateMethod(Long id, Integer quantity, Throwable e) {
    log.info(e.getMessage());
    Item item = new Item();
    Product product = new Product();
    item.setQuantities(quantity);
    product.setId(id);
    product.setName("Notebook Dell");
    product.setPrice(1500.00);
    item.setProduct(product);
    return item;
  }
  // Metodo alternativo como fallbackMethod para coincidir con la clase CompletableFuture
  public CompletableFuture<Item> alternateMethod2(Long id, Integer quantity, Throwable e) {
    log.info(e.getMessage());
    Item item = new Item();
    Product product = new Product();
    item.setQuantities(quantity);
    product.setId(id);
    product.setName("Notebook Dell");
    product.setPrice(1500.00);
    item.setProduct(product);
    return CompletableFuture.supplyAsync(() -> item);
  }

  // Handler informacion del servidor de configurador
  @GetMapping("/get-config")
  public ResponseEntity<?> getConfig(@Value("${server.port}") String port) {
    log.info(text);
    Map<String, String> json = new HashMap<>();
    json.put("texto", text);
    json.put("puerto", port);
    if (environment.getActiveProfiles().length > 0
        && environment.getActiveProfiles()[0].equals("dev")) {
      json.put("autor.nombre", environment.getProperty("configuracion.autor.nombre"));
      json.put("autor.email", environment.getProperty("configuracion.autor.email"));
    }
    return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
  }
}
