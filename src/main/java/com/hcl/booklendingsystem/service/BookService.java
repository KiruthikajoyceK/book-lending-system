package com.hcl.booklendingsystem.service;

import java.util.Optional;
import com.hcl.booklendingsystem.dto.BookRequestDetails;
import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.entity.BookRequest;
import java.util.List;
import com.hcl.booklendingsystem.dto.GetBooksOutput;

public interface BookService {
	public CommonResponse addBook(BookRequestDetails bookRequestDetails);
	public Optional<BookRequest> requestBook(Integer bookId, Integer userId);
	public Optional<List<GetBooksOutput>>getBooks(String bookName,String authorName,Integer pageNumber);

	public CommonResponse borrowBook(Integer bookId, Integer userId);

}
