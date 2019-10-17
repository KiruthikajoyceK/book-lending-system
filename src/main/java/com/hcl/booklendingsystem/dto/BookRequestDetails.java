package com.hcl.booklendingsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookRequestDetails {

	private Integer userId;
	private String bookName;
	private String authorName;

	
}
