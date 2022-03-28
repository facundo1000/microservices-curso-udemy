package com.demo.commons.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "products")
public class Product implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Double price;

  @Column(name = "create_at")
  @Temporal(TemporalType.DATE)
  private Date createAt;

  @Transient private Integer port;

  public Product() {}

  public Product(String name, Double price, Date createAt, Integer port) {
    this.name = name;
    this.price = price;
    this.createAt = createAt;
    this.port = port;
  }

  public Product(Long id, String name, Double price, Date createAt, Integer port) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.createAt = createAt;
    this.port = port;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Date getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  @Override
  public String toString() {
    return "Product{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", price="
        + price
        + ", createAt="
        + createAt
        + '}';
  }

  private static final long serialVersionUID = 7144856161574898888L;
}
