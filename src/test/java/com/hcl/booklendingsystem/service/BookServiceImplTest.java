package com.hcl.booklendingsystem.service;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.hcl.booklendingsystem.dto.BookRequestDetails;
import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.entity.Author;
import com.hcl.booklendingsystem.entity.Book;
import com.hcl.booklendingsystem.entity.BookHistory;
import com.hcl.booklendingsystem.entity.BookRequest;
import com.hcl.booklendingsystem.entity.User;
import com.hcl.booklendingsystem.exception.BookNotFoundException;
import com.hcl.booklendingsystem.exception.UserNotFoundException;
import com.hcl.booklendingsystem.repository.AuthorRepository;
import com.hcl.booklendingsystem.repository.BookHistoryRepository;
import com.hcl.booklendingsystem.repository.BookRepository;
import com.hcl.booklendingsystem.repository.BookRequestRepository;
import com.hcl.booklendingsystem.repository.UserRepository;
/**
 * 
 * @author krithika
 * @author sairam
 * @author sreeshma
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

	@InjectMocks
	BookServiceImpl bookServiceImpl;
	@Mock
	BookRepository bookRepository;
	@Mock
	AuthorRepository authorRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	BookRequestRepository bookRequestRepository;
	Pageable paging;
	Book book;
	List<Book> books;
	Author author;
	Page<Book> Pagebooks;

	@Mock
	BookHistoryRepository bookHistoryRepository;

	BookRequestDetails bookRequestDetails;
	CommonResponse commonResponse;
	User user;
	BookRequest bookRequest;
	int bookId;
	int userId;
	BookHistory bookHistory;

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
		bookRequestDetails = new BookRequestDetails();
		bookRequestDetails.setUserId(1);
		bookRequestDetails.setAuthorName("kiruthika");
		bookRequestDetails.setBookName("java");
		user = new User();
		user.setUserId(1);
		user.setEmail("sree@gmail.com");
		user.setPassword("sree123");
		user.setUserId(1);
		book = new Book();
		book.setBookName("java");
		book.setBookId(1);

		bookId = 1;
		userId = 1;

		bookHistory = new BookHistory();
		bookHistory.setUserId(userId);
		bookHistory.setBookHistoryId(1);
		bookHistory.setBookId(1);

		commonResponse = new CommonResponse();
		bookRequest = new BookRequest();
		bookRequest.setBookId(1);
		bookRequest.setUserId(1);
		bookRequest.setBookRequestId(1);
		bookRequest.setBookRequestDate(LocalDateTime.now());

	}

	@Test
	public void testGetBooks() {
//		Mockito.when(bookRepository.findAll(paging)).thenReturn(Pagebooks);
//		Mockito.when(authorRepository.findById(book.getAuthorId())).thenReturn(Optional.of(author));
		Assert.assertEquals(0,
				bookServiceImpl.getBooks(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()).get().size());
	}

	@Test
	public void testGetBooks2() {
		Pagebooks = new PageImpl<Book>(books);
//		Mockito.when(bookRepository.findAll(paging)).thenReturn(Pagebooks);

//		Mockito.when(authorRepository.findById(book.getAuthorId())).thenReturn(Optional.of(author));
		Mockito.when(bookRepository.searchBookByAuthorNameAndBookName("s", "j", paging)).thenReturn(books);
//		Mockito.when(bookRepository.searchBookByAuthorNameOrBookName("s", "j", paging)).thenReturn(books);
//		Mockito.when(authorRepository.findById(book.getAuthorId())).thenReturn(Optional.of(author));

		Assert.assertEquals(1, bookServiceImpl.getBooks("s", "j", 0).get().size());
	}

	@Test
	public void testGetBooks3() {
		Pagebooks = new PageImpl<Book>(books);
//		Mockito.doNothing().when(bookRepository.findAll(paging));
		Mockito.when(bookRepository.findAll(paging)).thenReturn(Pagebooks);

//		Mockito.when(authorRepository.findById(book.getAuthorId())).thenReturn(Optional.of(author));
//		Mockito.when(bookRepository.searchBookByAuthorNameAndBookName(null, null, paging)).thenReturn(books);
//		Mockito.when(bookRepository.searchBookByAuthorNameOrBookName(null, null, paging)).thenReturn(books);

//		Mockito.when(authorRepository.findById(book.getAuthorId())).thenReturn(Optional.of(author));

		Assert.assertEquals(1, bookServiceImpl.getBooks(null, null, 0).get().size());
	}

	@Test
	public void testGetBooks4() {
		Pagebooks = new PageImpl<Book>(books);
//		Mockito.doNothing().when(bookRepository.findAll(paging));
//		Mockito.when(bookRepository.findAll(paging)).thenReturn(Pagebooks);

//		Mockito.when(authorRepository.findById(book.getAuthorId())).thenReturn(Optional.of(author));
//		Mockito.when(bookRepository.searchBookByAuthorNameAndBookName(null, "j", paging)).thenReturn(books);
		Mockito.when(bookRepository.searchBookByAuthorNameOrBookName(null, "j", paging)).thenReturn(books);

//		Mockito.when(authorRepository.findById(book.getAuthorId())).thenReturn(Optional.of(author));

		Assert.assertEquals(1, bookServiceImpl.getBooks(null, "j", 0).get().size());
	}

	@Test
	public void testAddBookAuthorExist() {
		Author author = new Author();
		author.setAuthorName("kiruthika");
		Mockito.when(userRepository.findById(bookRequestDetails.getUserId())).thenReturn(Optional.of(user));
		Mockito.when(authorRepository.findByAuthorName(author.getAuthorName())).thenReturn(Optional.of(author));
		commonResponse = bookServiceImpl.addBook(bookRequestDetails);
		assertEquals(bookRequestDetails.getBookName(), book.getBookName());
	}

	@Test(expected = UserNotFoundException.class)
	public void testAddBookUserNotFoundException() {
		Mockito.when(userRepository.findById(bookRequestDetails.getUserId())).thenReturn(Optional.empty());
		commonResponse = bookServiceImpl.addBook(bookRequestDetails);
		assertEquals(bookRequestDetails.getBookName(), book.getBookName());
	}

	@Test
	public void testAddBook() {
		Mockito.when(userRepository.findById(bookRequestDetails.getUserId())).thenReturn(Optional.of(user));
		Mockito.when(authorRepository.findByAuthorName(Mockito.anyString())).thenReturn(Optional.empty());
		commonResponse = bookServiceImpl.addBook(bookRequestDetails);
		assertEquals(bookRequestDetails.getBookName(), book.getBookName());
	}

	@Test
	public void testBookRequest() {
		Mockito.when(bookRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(book));
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
		Mockito.when(bookRequestRepository.save(Mockito.any())).thenReturn(bookRequest);
		Optional<BookRequest> bookRequestOptional = bookServiceImpl.requestBook(1, 1);
		assertNotNull(bookRequestOptional);

	}

	@Test(expected = BookNotFoundException.class)
	public void testBookNotFoundException() {
		Mockito.when(bookRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		Optional<BookRequest> bookRequestOptional = bookServiceImpl.requestBook(1, 1);
		assertNotNull(bookRequestOptional);

	}

	@Test(expected = UserNotFoundException.class)
	public void testUserNotFoundException() {
		Mockito.when(bookRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(book));
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		Optional<BookRequest> bookRequestOptional = bookServiceImpl.requestBook(1, 1);
		assertNotNull(bookRequestOptional);

	}

	@Test
	public void testBorrowBook() {

		Mockito.when(bookRepository.findById(book.getBookId())).thenReturn(Optional.of(book));
		commonResponse = bookServiceImpl.borrowBook(bookId, userId);
		assertEquals(user.getUserId(), bookHistory.getUserId());

	}

	@Test(expected = BookNotFoundException.class)
	public void testBorrowBook1() {

		Mockito.when(bookRepository.findById(book.getBookId())).thenReturn(Optional.empty());
		commonResponse = bookServiceImpl.borrowBook(bookId, userId);

	}

}
