package com.hcl.booklendingsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse {
	private Integer statusCode;
	private String message;
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
	
	

}
