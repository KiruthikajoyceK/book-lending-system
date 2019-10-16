package com.hcl.booklendingsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
  private Integer statusCode;
  private String message;
  private Integer userId;
public Integer getStatusCode() {
	return statusCode;
}
public void setStatusCode(Integer statusCode) {
	this.statusCode = statusCode;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
  
  
  
}
