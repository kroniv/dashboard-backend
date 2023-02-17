package com.example.dashboard.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "regions")
public class Region {

  @Id
  private String id;
  @Column(name = "region_name", nullable = false)
  private String name;

}
