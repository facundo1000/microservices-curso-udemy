package com.demo.springbootserviceitem.clients;

import com.demo.commons.entities.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Con esta annotation se define y se declara que esta interfaz es un cliente Feign y se le indica
 * el nombre del microservicio al que se quiere conectar a traves del atributo name (que esta en
 * application.properties del microservicio a conectar) y la url
 */
@FeignClient(name = "service-product")
public interface ProductClientRest {
  /**
   * Se utiliza la misma anotacion que en el Controlador; en el controlador la utilizamos para
   * mapear nuestros metodos handler a endpoins, a rutas urls. Mientras que en el 'Feign Client',
   * indicamos la ruta, este endpoint, para CONSUMIR el servicio, el API Rest y obtener los datos
   * del JSON, pero comvertidos a nuestros objetos, en este caso al product
   */
  @GetMapping("/list")
  public List<Product> list();

  @GetMapping("/list/product/{id}")
  public Product getProductById(@PathVariable("id") Long id);

  @PostMapping("/create")
  public Product create(@RequestBody Product product);

  @PutMapping("/update/{id}")
  public Product update(@RequestBody Product product, @PathVariable Long id);

  @DeleteMapping("/delete/{id}")
  public void delete(@PathVariable Long id);
}
