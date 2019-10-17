package com.hcl.booklendingsystem.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;

import com.hcl.booklendingsystem.entity.Author;
import com.hcl.booklendingsystem.entity.Book;
import com.hcl.booklendingsystem.entity.BookHistory;
import com.hcl.booklendingsystem.repository.BookHistoryRepository;
import com.hcl.booklendingsystem.repository.BookRepository;

/**
 * 
 * @author sairam
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BookSchedulerTest {

	@InjectMocks
	BookScheduler bookScheduler;
	@Mock
	BookService bookService;

	@Mock
	BookHistoryRepository bookHistoryRepository;

	@Mock
	BookRepository bookRepository;

	Book book;
	List<Book> books;
	Author author;

	BookHistory bookHistory;
	List<BookHistory> bookHistoryList;

	Page<Book> Pagebooks;

	@Before
	public void setUp() {
		book = new Book();
		book.setAuthorId(1);
		book.setBookId(1);
		book.setBookName("java");
		book.setBookStatus("available");
		book.setUserId(1);

		author = new Author();
		author.setAuthorId(1);
		author.setAuthorName("SAIRAM");

		bookHistory = new BookHistory();
		bookHistory.setBookHistoryId(1);
		bookHistory.setBookId(book.getBookId());
		bookHistory.setBorrowDate(LocalDateTime.now().minusMinutes(10));
		bookHistory.setUserId(1);

		bookHistoryList = new ArrayList<>();
		bookHistoryList.add(bookHistory);

		books = new ArrayList<>();
		books.add(book);

	}

	@Test
	public void testReleaseBook() {
//		Mockito.when(bookHistoryRepository.findByBorrowDateLessThan(LocalDateTime.now()))

//				.thenReturn(Optional.of(bookHistoryList));
//		Mockito.when(bookRepository.findById(bookHistory.getBookId())).thenReturn(Optional.of(book));
//		Mockito.when(bookRepository.save(book)).thenReturn(book);
		bookScheduler.releaseBook();
	}

}
