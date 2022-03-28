package com.demo.springbootserviceitem.service;

import com.demo.commons.entities.Product;
import com.demo.springbootserviceitem.models.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {

  @Autowired private RestTemplate clientRest;

  @Override
  public List<Item> findAll() {
    List<Product> products =
        Arrays.asList(clientRest.getForObject("http://service-product/list", Product[].class));
    return products.stream()
        .filter(Objects::nonNull)
        .map(p -> new Item(p, 1))
        .collect(Collectors.toList());
  }

  @Override
  public Item findById(Long id, Integer quantities) {
    /** Se utiliza un Map de Java para poder pasar el ID */
    Map<String, String> pathVariables = new HashMap<>();
    pathVariables.put("id", id.toString());
    Product product =
        clientRest.getForObject(
            "http://service-product/list/product/{id}", Product.class, pathVariables);
    return new Item(product, quantities);
  }

  /**
   * Se consume una donde enviamos mediante un endpoint (una ruta URL) el objeto producto, en el
   * cuerpo de la respuesta 1.La ruta// 2.El tipo del request (POST,GET,etc) 3.El objeto que
   * contiene el "tipo" producto, en este caso = body 4.El tipo de dato en el cual queremos recibir
   * este objeto
   */
  @Override
  public Product save(Product product) {
    HttpEntity<Product> body = new HttpEntity<>(product);
    ResponseEntity<Product> response =
        clientRest.exchange("http://service-product/create", HttpMethod.POST, body, Product.class);
    return response.getBody();
  }

  @Override
  public Product update(Product product, Long id) {
    Map<String, String> pathVariables = new HashMap<>();

    pathVariables.put("id", id.toString());

    HttpEntity<Product> body = new HttpEntity<>(product);
    ResponseEntity<Product> response =
        clientRest.exchange(
            "http://service-product/update/{id}",
            HttpMethod.PUT,
            body,
            Product.class,
            pathVariables);

    return response.getBody();
  }

  @Override
  public void delete(Long id) {
    Map<String, String> pathVariables = new HashMap<>();
    pathVariables.put("id", id.toString());
    clientRest.delete("http://service-product/delete/{id}",pathVariables);
  }
}
