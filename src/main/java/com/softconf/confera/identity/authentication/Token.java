package com.softconf.confera.identity.authentication;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("User authentication token")
public class Token {

  @ApiModelProperty("Token value")
  private String token;

  public Token() {
  }

  public Token(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}