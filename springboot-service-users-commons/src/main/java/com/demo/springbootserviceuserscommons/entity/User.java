package com.demo.springbootserviceuserscommons.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  // El parametro 'unique' permite que los valores dentro de la tabla
  @Column(unique = true, length = 20)
  private String username;

  @Column(length = 60)
  private String password;

  private Boolean enable;
  private String name;
  private String lastname;

  @Column(unique = true, length = 100)
  private String email;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "users_to_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"),
      uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})})
  private List<Role> roles;

  private Integer tries;

  public User(
      String username,
      String password,
      Boolean enable,
      String name,
      String lastname,
      String email,
      List<Role> roles) {
    this.username = username;
    this.password = password;
    this.enable = enable;
    this.name = name;
    this.lastname = lastname;
    this.email = email;
    this.roles = roles;
  }

  public User() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public Integer getTries() {
    return tries;
  }

  public void setTries(Integer tries) {
    this.tries = tries;
  }

  private static final long serialVersionUID = 2394100967071052779L;
}
