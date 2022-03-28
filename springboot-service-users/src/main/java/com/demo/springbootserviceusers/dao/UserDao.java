package com.demo.springbootserviceusers.dao;

import com.demo.springbootserviceuserscommons.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "user")
public interface UserDao extends JpaRepository<User, Long> {
  /**
   * A traves del nombre del metodo (Query method name) se ejecutará la consulta JPQL select u from
   * Usuario where u.username =?1
   */
  @RestResource(path = "search-username")
  public User findByUsername(@Param("username") String username);

  @Query("select u from User u where u.username=?1")
  public User getByUsername(String username);
}
