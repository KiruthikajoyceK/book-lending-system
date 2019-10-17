package com.hcl.booklendingsystem.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.booklendingsystem.dto.BookRequestDetails;
import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.entity.Author;
import com.hcl.booklendingsystem.entity.Book;
import com.hcl.booklendingsystem.entity.BookHistory;
import com.hcl.booklendingsystem.entity.User;
import com.hcl.booklendingsystem.exception.BookNotFoundException;
import com.hcl.booklendingsystem.exception.UserNotFoundException;
import com.hcl.booklendingsystem.repository.AuthorRepository;
import com.hcl.booklendingsystem.repository.BookHistoryRepository;
import com.hcl.booklendingsystem.repository.BookRepository;
import com.hcl.booklendingsystem.repository.UserRepository;
import com.hcl.booklendingsystem.util.BookLendingSystemConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * This class contains the method for add a book for registered user & borrow a
 * book if the book is available.
 * 
 * @author KiruhtikaK
 * @since 2109/16/10
 *
 */
@Slf4j
@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	BookHistoryRepository bookHistoryRepository;

	/**
	 * 
	 * This method for add a book for already registered user and based on author
	 * also.
	 * 
	 * @param bookRequestDetails
	 * @return CommonResponse
	 * 
	 */
	@Transactional
	public CommonResponse addBook(BookRequestDetails bookRequestDetails) {

		log.info(BookLendingSystemConstants.BOOK_SERVICE_START);
		Optional<User> user = userRepository.findById(bookRequestDetails.getUserId());
		if (!user.isPresent()) {
			throw new UserNotFoundException(BookLendingSystemConstants.USER_NOT_EXISTS);

		}
		Optional<Author> authorr = authorRepository.findByAuthorName(bookRequestDetails.getAuthorName());
		Book book = new Book();
		if (!authorr.isPresent()) {
			Author author = new Author();
			author.setAuthorName(bookRequestDetails.getAuthorName());
			authorRepository.save(author);
			book.setAuthorId(author.getAuthorId());

		} else {

			book.setAuthorId(authorr.get().getAuthorId());
		}

		BeanUtils.copyProperties(bookRequestDetails, book);
		book.setBookStatus(BookLendingSystemConstants.AVAILABLE);
		bookRepository.save(book);

		CommonResponse commonResponse = new CommonResponse();
		log.info(BookLendingSystemConstants.BOOK_SERVICE_END);
		return commonResponse;
	}

	/**
	 * This method for borrow a book for the registered user if the book is
	 * available & maintain the book history
	 * 
	 * @param bookId
	 * @param userId
	 * @return CommonResponse
	 */
	@Transactional
	public CommonResponse borrowBook(Integer bookId, Integer userId) {

		log.info(BookLendingSystemConstants.BOOK_SERVICE_START);
		Optional<Book> book = bookRepository.findById(bookId);

		if (!book.isPresent()) {
			throw new BookNotFoundException(BookLendingSystemConstants.BOOK_NOT_FOUND);
		}
		book.get().setBookStatus(BookLendingSystemConstants.BORROW);
		bookRepository.save(book.get());

		BookHistory bookHistory = new BookHistory();
		bookHistory.setBookId(bookId);
		bookHistory.setUserId(userId);
		LocalDateTime borrowDate = LocalDateTime.now();
		bookHistory.setBorrowDate(borrowDate);
		bookHistoryRepository.save(bookHistory);

		CommonResponse commonResponse = new CommonResponse();
		log.info(BookLendingSystemConstants.BOOK_SERVICE_END);
		return commonResponse;
	}

}
