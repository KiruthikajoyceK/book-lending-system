package com.hcl.booklendingsystem.service;

import java.util.Optional;
import com.hcl.booklendingsystem.dto.BookRequestDetails;
import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.entity.BookRequest;
import java.util.List;
import com.hcl.booklendingsystem.dto.GetBooksOutput;

public interface BookService {
	public List<GetBooksOutput> getBooks(Integer pageNumber);
	public CommonResponse addBook(BookRequestDetails bookRequestDetails);
	public  Optional<BookRequest> requestBook(Integer bookId, Integer userId);

}
