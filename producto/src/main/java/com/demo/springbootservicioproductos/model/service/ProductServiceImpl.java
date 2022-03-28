package com.demo.springbootservicioproductos.model.service;

import com.demo.commons.entities.Product;
import com.demo.springbootservicioproductos.model.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

  @Autowired private ProductDao productDao;

  @Override
  @Transactional(readOnly = true)
  public List<Product> findAll() {
    return productDao.findAll();
  }

  @Override
  public Product findById(Long id) {
    return productDao
        .findById(id)
        .orElseThrow(() -> new RuntimeException("ID " + id + " not found"));
  }

  @Override
  @Transactional // sin en read-only, ya que es de escritura #added
  public Product save(Product product) {
    return productDao.save(product);
  }

  @Override
  public void deleteById(Long id) {
    Optional<Product> product = productDao.findById(id);
    if (product.isEmpty()) {
      throw new IllegalStateException(String.format("The ID %d NOT match  with any product", id));
    }
    productDao.deleteById(id);
  }
}
