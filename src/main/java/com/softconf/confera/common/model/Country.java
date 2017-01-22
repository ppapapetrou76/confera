package com.softconf.confera.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "countries",
    uniqueConstraints = {@UniqueConstraint(columnNames = "name"), @UniqueConstraint(columnNames = "code")})
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
@ApiModel("Country")
public class Country {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty("Country ID")
  private long id;

  @Column(nullable = false)
  @ApiModelProperty("Country name")
  private String name;

  @Column(nullable = false)
  @ApiModelProperty("Two-letter country code")
  private String code;

  @Column(nullable = false)
  @ApiModelProperty("Flag for countries forbidden for applications")
  private boolean allowed;

  public Country() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public boolean isAllowed() {
    return allowed;
  }

  public void setAllowed(boolean allowed) {
    this.allowed = allowed;
  }

}
