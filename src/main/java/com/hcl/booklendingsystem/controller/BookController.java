package com.hcl.booklendingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.booklendingsystem.dto.BookRequestDetails;
import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.service.BookService;
import com.hcl.booklendingsystem.util.BookLendingSystemConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * @author KiruthikaK
 * @since 2019/16/10 This class contains the method for add a book for
 *        registered user
 *
 */
@Slf4j
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class BookController {

	@Autowired
	BookService bookService;

	/**
	 * @param bookRequestDetails
	 * @return CommonResponse
	 * 
	 */
	@PostMapping("/books")
	public ResponseEntity<CommonResponse> addBook(@RequestBody BookRequestDetails bookRequestDetails) {
		log.info(BookLendingSystemConstants.BOOK_CONTROLLER);

		CommonResponse commonResponse = bookService.addBook(bookRequestDetails);
		commonResponse.setMessage(BookLendingSystemConstants.CREATED);
		commonResponse.setStatusCode(HttpStatus.CREATED.value());

		return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);

	}

}
