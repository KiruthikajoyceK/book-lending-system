package com.hcl.booklendingsystem.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.booklendingsystem.dto.BookRequestDetails;
import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.entity.BookRequest;
import com.hcl.booklendingsystem.exception.UserException;
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
	
	/**
	 * This method will accept bookId and userId and throw UserExcption in case if either of them is null
	 * else call requestBook method in service layer and get the saved BookRequest object.
	 * @param bookId
	 * @param userId
	 * @return ResponseEntity of CommonResponse 
	 */
	@PostMapping("/books/{bookId}/request")
	public ResponseEntity<CommonResponse> bookRequest(@PathVariable("bookId")Integer bookId,@RequestParam("userId")Integer userId) {
		log.info(BookLendingSystemConstants.BOOK_REQUEST_DEBUG_START_CONTROLLER);
		if(bookId ==null || userId==null) {
			throw new UserException(BookLendingSystemConstants.BOOK_ID_USER_ID_MANDATORY_EXCEPTION);
		}
		CommonResponse commonResponse=new CommonResponse();
		Optional<BookRequest> bookRequestOptional = bookService.requestBook(bookId,userId);
		if(bookRequestOptional.isPresent()&&bookRequestOptional.get().getBookRequestId()!=null) {
			commonResponse.setMessage(BookLendingSystemConstants.REQUEST_SENT_SUCESS);
			commonResponse.setStatusCode(HttpStatus.OK.value());
		}else {
			commonResponse.setMessage(BookLendingSystemConstants.FAILURE);
			commonResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		log.info(BookLendingSystemConstants.BOOK_REQUEST_DEBUG_END_CONTROLLER);
		return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);

	}
}
