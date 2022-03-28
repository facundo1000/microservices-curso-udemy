package com.demo.springbootservicioproductos.model.dao;

import com.demo.commons.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Long> {}
