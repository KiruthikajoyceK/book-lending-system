package com.hcl.booklendingsystem.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hcl.booklendingsystem.dto.BookListResponse;
import com.hcl.booklendingsystem.dto.BookRequestDetail;
import com.hcl.booklendingsystem.dto.BookRequestDetails;
import com.hcl.booklendingsystem.dto.BorrowRequest;
import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.dto.GetBooksOutput;
import com.hcl.booklendingsystem.entity.BookRequest;
import com.hcl.booklendingsystem.exception.BookIdAndUserIdEmptyException;
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
	Integer bookId;
	Integer userId;
	BookRequestDetail bookRequestDetail;
	BookListResponse bookListResponse;

	GetBooksOutput getBooksOutput;
	List<GetBooksOutput> getBooksOutputList;

	@Before
	public void setup() {
		bookRequestDetail = new BookRequestDetail();
		bookRequestDetail.setUserId(1);
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
		bookRequestDetail = new BookRequestDetail();
		bookRequestDetail.setUserId(1);

		getBooksOutput = new GetBooksOutput();
		getBooksOutput.setAuthorName("SAIRAM");
		getBooksOutput.setBookId(1);
		getBooksOutput.setBookName("JAVA");
		getBooksOutput.setBookStatus("AVAILABLE");
		getBooksOutput.setUserId(1);

		getBooksOutputList = new ArrayList<>();
		getBooksOutputList.add(getBooksOutput);

		bookListResponse = new BookListResponse();
		bookListResponse.setStatusCode(500);

	}

	@Test
	public void testAddBook() {
		Mockito.when(bookService.addBook(bookRequestDetails)).thenReturn(commonResponse);
		ResponseEntity<CommonResponse> actual = bookController.addBook(bookRequestDetails);
		ResponseEntity<CommonResponse> expected = new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
		assertEquals(expected.getStatusCode().value(), actual.getStatusCodeValue());
	}

	@Test(expected = UserException.class)
	public void testBookRequest() {
		BookRequestDetail bookRequestDetail = new BookRequestDetail();
		bookRequest.setUserId(1);
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

		BorrowRequest borrowRequest = new BorrowRequest();
		borrowRequest.setUserId(1);
		Mockito.when(bookService.borrowBook(bookId, userId)).thenReturn(commonResponse);

		Mockito.when(bookService.borrowBook(bookId, borrowRequest.getUserId())).thenReturn(commonResponse);
		ResponseEntity<CommonResponse> actual = bookController.borrowBook(bookId, borrowRequest);
		ResponseEntity<CommonResponse> expected = new ResponseEntity<>(commonResponse, HttpStatus.OK);
		assertEquals(expected.getStatusCode().value(), actual.getStatusCodeValue());

	}

	@Test(expected = BookIdAndUserIdEmptyException.class)
	public void testBorrowBook1() {
		BorrowRequest borrowRequest = new BorrowRequest();
		borrowRequest.setUserId(0);
		ResponseEntity<CommonResponse> actual = bookController.borrowBook(bookId, borrowRequest);
		ResponseEntity<CommonResponse> expected = new ResponseEntity<>(commonResponse, HttpStatus.OK);
		assertEquals(expected.getStatusCode().value(), actual.getStatusCodeValue());

	}

	@Test
	public void testGetBooks() {
		Mockito.when(bookService.getBooks(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(Optional.of(getBooksOutputList));

		ResponseEntity<BookListResponse> actual = bookController.getBooks(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyInt());

		Assert.assertEquals(1, actual.getBody().getBookList().size());
	}

	@Test(expected = UserException.class)
	public void testGetBooks1() {
		
		Mockito.when(bookService.getBooks("s", "j", -10))
				.thenReturn(Optional.of(getBooksOutputList));

		ResponseEntity<BookListResponse> actual = bookController.getBooks("s", "j",
				-10);
		ResponseEntity<BookListResponse> expected = new ResponseEntity<>(bookListResponse, HttpStatus.OK);

		Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());

	}

	@Test
	public void testGetBooks2() {

		Mockito.when(bookService.getBooks(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(Optional.empty());

		ResponseEntity<BookListResponse> actual = bookController.getBooks(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyInt());
		ResponseEntity<BookListResponse> expected = new ResponseEntity<>(bookListResponse, HttpStatus.OK);

		Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());

	}

	@Test(expected = BookIdAndUserIdEmptyException.class)
	public void testBorrowBook2() {
		BorrowRequest borrowRequest = new BorrowRequest();
		bookId = 0;
		ResponseEntity<CommonResponse> actual = bookController.borrowBook(bookId, borrowRequest);
		ResponseEntity<CommonResponse> expected = new ResponseEntity<>(commonResponse, HttpStatus.OK);
		assertEquals(expected.getStatusCode().value(), actual.getBody().getStatusCode().intValue());

	}
	

	@Test
	public void testBorrowRequest() {

		Mockito.when(bookService.requestBook(bookId, bookRequest.getUserId())).thenReturn(Optional.of(bookRequest));
		ResponseEntity<CommonResponse> actual = bookController.bookRequest(bookId, bookRequestDetail);
		ResponseEntity<CommonResponse> expected = new ResponseEntity<>(commonResponse, HttpStatus.OK);
		assertEquals(expected.getStatusCode().value(), actual.getStatusCodeValue());

	}

	@Test(expected = UserException.class)
	public void testExpectedUserException() {
		bookId = null;
		bookRequest.setUserId(null);
		ResponseEntity<CommonResponse> actual = bookController.bookRequest(bookId, bookRequestDetail);
		ResponseEntity<CommonResponse> expected = new ResponseEntity<>(commonResponse, HttpStatus.OK);
		assertEquals(expected.getStatusCode().value(), actual.getStatusCodeValue());

	}

	@Test
	public void testBorrowRequestOptional() {
		bookRequest.setUserId(null);
		ResponseEntity<CommonResponse> actual = bookController.bookRequest(bookId, bookRequestDetail);
		ResponseEntity<CommonResponse> expected = new ResponseEntity<>(commonResponse, HttpStatus.OK);
		assertEquals(expected.getStatusCode().value(), actual.getStatusCodeValue());

	}

}
