package com.demo.springbootserviceitem.models;

import com.demo.commons.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
  private Product product;
  private Integer quantities;

  public Double getTotal() {
    return product.getPrice() * quantities.doubleValue();
  }
}
