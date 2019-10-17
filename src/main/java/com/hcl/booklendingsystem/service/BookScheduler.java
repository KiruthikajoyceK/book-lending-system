package com.hcl.booklendingsystem.service;

import static com.hcl.booklendingsystem.util.BookLendingSystemConstants.AVAILABLE;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hcl.booklendingsystem.entity.Book;
import com.hcl.booklendingsystem.entity.BookHistory;
import com.hcl.booklendingsystem.repository.BookHistoryRepository;
import com.hcl.booklendingsystem.repository.BookRepository;

/**
 * 
 * @author sairam
 *
 */
@Service
public class BookScheduler {
	@Autowired
	BookService bookService;

	@Autowired
	BookHistoryRepository bookHistoryRepository;

	@Autowired
	BookRepository bookRepository;

	public static final Logger LOGGER = LoggerFactory.getLogger(BookScheduler.class);

	/**
	 * releaseBook is schedular it wil run on fixed intervel of time and it will
	 * upadete book status to AVAILABLE who are taken before 2 minutes
	 * 
	 * @apiNote : it will take the date from database directly according to the
	 *          conditions, no need to pass input
	 */
	@Scheduled(fixedRate = 2000)
	public void releaseBook() {
		LOGGER.info(" releaseBook schedular at:{} ", LocalDateTime.now());
		LocalDateTime bookExpiredDate = LocalDateTime.now().minusMinutes(2);
		Optional<List<BookHistory>> booksOpt = bookHistoryRepository.findByBorrowDateLessThan(bookExpiredDate);
		booksOpt.ifPresent(bookHistorys -> 
			bookHistorys.forEach(bookHistory -> {
				Optional<Book> books = bookRepository.findById(bookHistory.getBookId());
				if (books.isPresent()) {
					books.get().setBookStatus(AVAILABLE);
					bookRepository.save(books.get());
				}
			}));
		LOGGER.info(" releaseBook schedular completed ");

	}

}
