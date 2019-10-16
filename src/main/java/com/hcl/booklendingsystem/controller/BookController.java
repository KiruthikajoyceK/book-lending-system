package com.hcl.booklendingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.booklendingsystem.dto.GetBooksOutput;
import com.hcl.booklendingsystem.service.BookService;

@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/books")
public class BookController {

	@Autowired
	BookService bookService;

	@GetMapping("/")
	public ResponseEntity<List<GetBooksOutput>> getBooks(@RequestParam Integer pageNumber) {
		return ResponseEntity.status(HttpStatus.OK).body(bookService.getBooks(pageNumber));

	}
}
