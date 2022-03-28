package com.demo.springbootserviceitem.service;

import com.demo.commons.entities.Product;
import com.demo.springbootserviceitem.models.Item;

import java.util.List;

public interface ItemService {
  public List<Item> findAll();

  public Item findById(Long id, Integer quantities);

  public Product save(Product product);

  public Product update(Product product, Long id);

  public void delete(Long id);
}
