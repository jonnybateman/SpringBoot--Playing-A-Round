package com.cqueltech.playingaround.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  /*
   * Define the class fields
   */

  @Id
  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "active")
  private int active;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "handicap")
  private Float handicap;

  // To declare a side as not responsible for the relationship, the attribute mappedBy
  // is used. mappedBy refers to the property name of the association on the owner side
  // (Role is the owner side). In our case, this is 'user'.
  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
  private Set<Role> roles = new HashSet<>();

  /*
   * Define class constructors
   */

  public User() {}

  public User(String username,
              String password,
              int active,
              String firstName,
              String lastName,
              Float handicap) {
    this.username = username;
    this.password = password;
    this.active = active;
    this.firstName = firstName;
    this.lastName = lastName;
    this.handicap = handicap;
  }

  /*
   * Define class getter and setter methods
   */

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

  public int getActive() {
    return active;
  }

  public void setActive(int active) {
    this.active = active;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Float getHandicap() {
    return handicap;
  }

  public void setHandicap(Float handicap) {
    this.handicap = handicap;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

}
