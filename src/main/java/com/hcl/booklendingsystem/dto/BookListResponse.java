package com.hcl.booklendingsystem.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookListResponse {
 private Integer statusCode;
 private String message;
 List<GetBooksOutput> bookList;
}
