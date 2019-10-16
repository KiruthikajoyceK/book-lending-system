package com.hcl.booklendingsystem.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.booklendingsystem.dto.BookRequestDetails;
import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.dto.GetBooksOutput;
import com.hcl.booklendingsystem.service.BookService;
import com.hcl.booklendingsystem.util.BookLendingSystemConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * @author KiruthikaK sairam
 * @since 2019/16/10 This class contains the method for add a book for
 *        registered user
 *
 */
@Slf4j
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/books")
public class BookController {
	public static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

	@Autowired
	BookService bookService;

	/**
	 * 
	 * @param pageNumber
	 * @return list of books
	 */
	@GetMapping("/")
	public ResponseEntity<List<GetBooksOutput>> getBooks(@RequestParam Integer pageNumber) {
		return ResponseEntity.status(HttpStatus.OK).body(bookService.getBooks(pageNumber));

	}

	/**
	 * @param bookRequestDetails
	 * @return CommonResponse
	 * 
	 */
	@PostMapping("/books")
	public ResponseEntity<CommonResponse> addBook(@RequestBody BookRequestDetails bookRequestDetails) {
		LOGGER.info(BookLendingSystemConstants.BOOK_CONTROLLER);

		CommonResponse commonResponse = bookService.addBook(bookRequestDetails);
		commonResponse.setMessage(BookLendingSystemConstants.CREATED);
		commonResponse.setStatusCode(HttpStatus.CREATED.value());

		return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);

	}

}
