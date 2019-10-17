package com.hcl.booklendingsystem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hcl.booklendingsystem.util.BookLendingSystemConstants;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookRequestDetails {
	@NotNull(message = BookLendingSystemConstants.USER_ID_NULL)
	private Integer userId;
	@NotBlank(message = BookLendingSystemConstants.BOOK_NAME_EMPTY)
	private String bookName;
	@NotBlank(message = BookLendingSystemConstants.AUTHOR_NAME_EMPTY)
	private String authorName;

}
