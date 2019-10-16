package com.hcl.booklendingsystem.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hcl.booklendingsystem.dto.BookRequestDetails;
import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.service.BookService;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

	@InjectMocks
	BookController bookController;

	@Mock
	BookService bookService;

	BookRequestDetails bookRequestDetails;

	CommonResponse commonResponse;

	@Before
	public void setup() {
		bookRequestDetails = new BookRequestDetails();
		bookRequestDetails.setUserId(1);
		bookRequestDetails.setAuthorName("kiruthika");
		bookRequestDetails.setBookName("java");

		commonResponse = new CommonResponse();
		commonResponse.setStatusCode(201);
	}

	@Test
	public void testAddBook() {
		Mockito.when(bookService.addBook(bookRequestDetails)).thenReturn(commonResponse);
		ResponseEntity<CommonResponse> actual = bookController.addBook(bookRequestDetails);
		ResponseEntity<CommonResponse> expected = new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
		assertEquals(expected.getStatusCode().value(), actual.getStatusCodeValue());

	}
}
