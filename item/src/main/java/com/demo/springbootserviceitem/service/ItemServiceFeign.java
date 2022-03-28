package com.demo.springbootserviceitem.service;

import com.demo.commons.entities.Product;
import com.demo.springbootserviceitem.clients.ProductClientRest;
import com.demo.springbootserviceitem.models.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Con @Primary indicamos que es la implementacion por defecto, en el @Autowired (ItemService)
 * cuando no se indica lo contrario
 */
@Service("serviceFeign")
public class ItemServiceFeign implements ItemService {

  @Autowired private ProductClientRest clientFeign;

  @Override
  public List<Item> findAll() {
    return clientFeign.list().stream()
        .filter(Objects::nonNull)
        .map(p -> new Item(p, 1))
        .collect(Collectors.toList());
  }

  @Override
  public Item findById(Long id, Integer quantities) {
    return new Item(clientFeign.getProductById(id), quantities);
  }

  @Override
  public Product save(Product product) {
    return clientFeign.create(product);
  }

  @Override
  public Product update(Product product, Long id) {
    return clientFeign.update(product, id);
  }

  @Override
  public void delete(Long id) {
    clientFeign.delete(id);
  }
}
