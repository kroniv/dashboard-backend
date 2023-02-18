package com.example.dashboard.entities;


import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;


@Entity
@Data
public class Privilege {

  @Id
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "privileges")
  private Collection<Role> roles;

  public Privilege(String name) {
    this.name = name;
  }
}
