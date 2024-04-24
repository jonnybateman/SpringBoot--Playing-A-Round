package com.cqueltech.playingaround.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {

  /*
   * Define the entity's fields
   */

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "role")
  private String role;

  // Owner side of the User to Role (one-to-many) relationship  
  @ManyToOne(fetch = FetchType.LAZY,
             cascade = {CascadeType.ALL})
  @JoinColumn(name = "username")
  private User user;

  /*
   * Define class constructors
   */

  public Role(String role) {
    this.role = role;
  }

  public Role() {
  }

  /*
   * Define class getter and setter methods
   */

   public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
