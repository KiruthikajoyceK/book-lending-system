package com.hcl.booklendingsystem.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hcl.booklendingsystem.dto.BookRequestDetail;
import com.hcl.booklendingsystem.dto.BookRequestDetails;
import com.hcl.booklendingsystem.dto.BorrowRequest;
import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.entity.BookRequest;
import com.hcl.booklendingsystem.exception.UserException;
import com.hcl.booklendingsystem.service.BookService;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

	@InjectMocks
	BookController bookController;
	@Mock
	BookService bookService;
	BookRequestDetails bookRequestDetails;
	CommonResponse commonResponse;
	BookRequest bookRequest;
	int bookId;
	int userId;
	BookRequestDetail bookRequestDetail;
	@Before
	public void setup() {
		bookRequestDetail=new BookRequestDetail();
		bookRequest.setUserId(1);
		bookRequestDetails = new BookRequestDetails();
		bookRequestDetails.setUserId(1);
		bookRequestDetails.setAuthorName("kiruthika");
		bookRequestDetails.setBookName("java");
		commonResponse = new CommonResponse();
		commonResponse.setStatusCode(201);
		bookRequest = new BookRequest();
		bookRequest.setBookId(1);
		bookRequest.setUserId(1);
		bookRequest.setBookRequestId(1);
		bookRequest.setBookRequestDate(LocalDateTime.now());
		bookId = 1;
		userId = 1;
	}

	@Test
	public void testAddBook() {
		Mockito.when(bookService.addBook(bookRequestDetails)).thenReturn(commonResponse);
		ResponseEntity<CommonResponse> actual = bookController.addBook(bookRequestDetails);
		ResponseEntity<CommonResponse> expected = new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
		assertEquals(expected.getStatusCode().value(), actual.getStatusCodeValue());
	}

	@Test
	public void testBookRequest() {
		Mockito.when(bookService.requestBook(1, 1)).thenReturn(Optional.of(bookRequest));
		ResponseEntity<CommonResponse> response = bookController.bookRequest(1, bookRequestDetail);
		assertNotNull(response);
	}

	@Test(expected = UserException.class)
	public void testUserException() {
		ResponseEntity<CommonResponse> response = bookController.bookRequest(null, null);
		assertNotNull(response);
	}

	@Test
	public void testBorrowBook() {
		BorrowRequest borrowRequest=new BorrowRequest();
		borrowRequest.setUserId(1);
		Mockito.when(bookService.borrowBook(1, 1)).thenReturn(commonResponse);
		ResponseEntity<CommonResponse> actual = bookController.borrowBook(bookId, borrowRequest);
		ResponseEntity<CommonResponse> expected = new ResponseEntity<>(commonResponse, HttpStatus.OK);
		assertEquals(expected.getStatusCode().value(), actual.getStatusCodeValue());

	}

}
