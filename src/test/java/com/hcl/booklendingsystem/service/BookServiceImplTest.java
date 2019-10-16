package com.hcl.booklendingsystem.service;

import static org.junit.Assert.assertEquals;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.hcl.booklendingsystem.dto.BookRequestDetails;
import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.entity.Author;
import com.hcl.booklendingsystem.entity.Book;
import com.hcl.booklendingsystem.entity.User;
import com.hcl.booklendingsystem.exception.UserNotFoundException;
import com.hcl.booklendingsystem.repository.AuthorRepository;
import com.hcl.booklendingsystem.repository.BookRepository;
import com.hcl.booklendingsystem.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

	@InjectMocks
	BookServiceImpl bookServiceImpl;
	@Mock
	BookRepository bookRepository;
	@Mock
	AuthorRepository authorRepository;

	Pageable paging;
	Book book;
	List<Book> books;
	Author author;

	Page<Book> Pagebooks;

	@Mock
	UserRepository userRepository;

	BookRequestDetails bookRequestDetails;

	CommonResponse commonResponse;

	User user;

	@Before
	public void setUp() {
		paging = PageRequest.of(0, 10);
		book = new Book();
		book.setAuthorId(1);
		book.setBookId(1);
		book.setBookName("java");
		book.setBookStatus("available");
		book.setUserId(1);

		author = new Author();
		author.setAuthorId(1);
		author.setAuthorName("SAIRAM");

		books = new ArrayList<>();
		books.add(book);

	}

	@Test
	public void testGetBooks() {
//		Mockito.when(bookRepository.findAll(paging)).thenReturn(Pagebooks);
		Mockito.when(authorRepository.findById(book.getAuthorId())).thenReturn(Optional.of(author));
		Assert.assertEquals(1, bookServiceImpl.getBooks(0).size());

	}

	@Before
	public void setup() {
		bookRequestDetails = new BookRequestDetails();
		bookRequestDetails.setUserId(1);
		bookRequestDetails.setAuthorName("kiruthika");
		bookRequestDetails.setBookName("java");

		user = new User();
		user.setUserId(1);

		book = new Book();
		book.setBookName("java");

		commonResponse = new CommonResponse();
	}

	@Test
	public void testAddBook() {

		Author author = new Author();

		author.setAuthorName("kiruthika");

		Mockito.when(userRepository.findById(bookRequestDetails.getUserId())).thenReturn(Optional.of(user));
		Mockito.when(authorRepository.findByAuthorName(author.getAuthorName())).thenReturn(Optional.of(author));

		commonResponse = bookServiceImpl.addBook(bookRequestDetails);

		assertEquals(bookRequestDetails.getBookName(), book.getBookName());

	}

	@Test(expected = UserNotFoundException.class)
	public void testAddBook1() {
		Mockito.when(userRepository.findById(bookRequestDetails.getUserId())).thenReturn(Optional.empty());

		commonResponse = bookServiceImpl.addBook(bookRequestDetails);

		assertEquals(bookRequestDetails.getBookName(), book.getBookName());

	}

	@Test
	public void testAddBook2() {

		Mockito.when(userRepository.findById(bookRequestDetails.getUserId())).thenReturn(Optional.of(user));

		Mockito.when(authorRepository.findByAuthorName(Mockito.anyString())).thenReturn(Optional.empty());

		commonResponse = bookServiceImpl.addBook(bookRequestDetails);

		assertEquals(bookRequestDetails.getBookName(), book.getBookName());

	}
}
