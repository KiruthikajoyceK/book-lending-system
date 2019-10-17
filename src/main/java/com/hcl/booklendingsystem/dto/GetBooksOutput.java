package com.hcl.booklendingsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetBooksOutput {

	private Integer bookId;
	private String bookName;
	private String authorName;
	private Integer userId;
	private String bookStatus;


}
