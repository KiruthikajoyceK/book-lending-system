package com.hcl.booklendingsystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hcl.booklendingsystem.dto.BookListResponse;
import com.hcl.booklendingsystem.dto.BookRequestDetails;
import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.dto.GetBooksOutput;
import com.hcl.booklendingsystem.entity.BookRequest;
import com.hcl.booklendingsystem.exception.UserException;
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

	@Autowired
	BookService bookService;


	/**
	 * @param bookRequestDetails
	 * @return CommonResponse
	 * 
	 */
	@PostMapping("/")
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
		return new ResponseEntity<>(commonResponse, HttpStatus.OK);

	}
	/**
	 * This method will accept bookName,authorName and pageNumber and throw UserExcption in case if pageNumber is null
	 * else call searchBook method in service layer and get filtered list of books .
	 * @param bookName
	 * @param authorName
	 * @param pageNumber
	 * @return ResponseEntity of BookListResponse 
	 */
	@GetMapping("/")
	public ResponseEntity<BookListResponse> getBooks(@RequestParam(value = "bookName",required = false)String bookName,@RequestParam(value="authorName",required = false)String authorName,@RequestParam("pageNumber") Integer pageNumber) {
		log.info(BookLendingSystemConstants.BOOK_SEARCH_DEBUG_START_CONTROLLER);
		BookListResponse bookListResponse=new BookListResponse();
		if(pageNumber ==null  || pageNumber<0) {
			throw new UserException(BookLendingSystemConstants.INVALID_INPUTS);
		}
		Optional<List<GetBooksOutput>> bookListOptional = bookService.getBooks(bookName,authorName,pageNumber);
		if(bookListOptional.isPresent()) {
			bookListResponse.setMessage(BookLendingSystemConstants.SUCCESS);
			bookListResponse.setStatusCode(HttpStatus.OK.value());
			bookListResponse.setBookList(bookListOptional.get());
		}else {
			bookListResponse.setMessage(BookLendingSystemConstants.FAILURE);
			bookListResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		log.info(BookLendingSystemConstants.BOOK_SEARCH_DEBUG_END_CONTROLLER);
		return new ResponseEntity<>(bookListResponse, HttpStatus.OK);

	}
}
