package com.hcl.booklendingsystem.service;

import java.util.List;

import com.hcl.booklendingsystem.dto.GetBooksOutput;

public interface BookService {

	public List<GetBooksOutput> getBooks(Integer pageNumber);



}
