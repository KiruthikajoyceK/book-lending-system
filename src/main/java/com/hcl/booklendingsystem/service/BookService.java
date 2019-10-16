package com.hcl.booklendingsystem.service;

import com.hcl.booklendingsystem.dto.BookRequestDetails;
import com.hcl.booklendingsystem.dto.CommonResponse;

public interface BookService {

	public CommonResponse addBook(BookRequestDetails bookRequestDetails);

}
