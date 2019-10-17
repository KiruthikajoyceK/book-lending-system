package com.hcl.booklendingsystem.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.booklendingsystem.dto.BookRequestDetails;
import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.exception.BookIdAndUserIdEmptyException;
import com.hcl.booklendingsystem.service.BookService;
import com.hcl.booklendingsystem.util.BookLendingSystemConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * This class contains the method for add a book for registered user & borrow a
 * book if the book is available.
 * 
 * @author KiruthikaK
 * @since 2019/16/10
 *
 */
@Slf4j
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/books")
public class BookController {

	@Autowired
	BookService bookService;

	/**
	 * This method for add a book by the registered user with Not empty
	 * bookRequestDetails fields.
	 * 
	 * @param bookRequestDetails
	 * @return CommonResponse
	 * 
	 */
	@PostMapping("/")
	public ResponseEntity<CommonResponse> addBook(@Valid @RequestBody BookRequestDetails bookRequestDetails) {

		log.info(BookLendingSystemConstants.BOOK_CONTROLLER_START);
		CommonResponse commonResponse = bookService.addBook(bookRequestDetails);
		commonResponse.setMessage(BookLendingSystemConstants.CREATED);
		commonResponse.setStatusCode(HttpStatus.CREATED.value());
		log.info(BookLendingSystemConstants.BOOK_CONTROLLER_END);
		return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);

	}

	/**
	 * This method for borrow a book by the registered user if the book is available
	 * 
	 * @param bookId
	 * @param userId
	 * @return CommonResponse
	 */
	@PostMapping("/{bookId}/borrow")
	public ResponseEntity<CommonResponse> borrowBook(@PathVariable Integer bookId,
			@RequestParam("userId") Integer userId) {

		log.info(BookLendingSystemConstants.BOOK_CONTROLLER_START);
		if (bookId == 0) {
			throw new BookIdAndUserIdEmptyException(BookLendingSystemConstants.BOOK_ID_EMPTY);
		}
		if (userId == 0) {
			throw new BookIdAndUserIdEmptyException(BookLendingSystemConstants.USER_ID_Empty);
		}
		CommonResponse commonResponse = bookService.borrowBook(bookId, userId);
		commonResponse.setMessage(BookLendingSystemConstants.UPDATED);
		commonResponse.setStatusCode(HttpStatus.OK.value());
		log.info(BookLendingSystemConstants.BOOK_CONTROLLER_END);
		return new ResponseEntity<>(commonResponse, HttpStatus.OK);

	}

}
