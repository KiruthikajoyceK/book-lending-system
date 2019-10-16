package com.hcl.booklendingsystem.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.booklendingsystem.dto.BookRequestDetails;
import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.entity.Author;
import com.hcl.booklendingsystem.entity.Book;
import com.hcl.booklendingsystem.entity.User;
import com.hcl.booklendingsystem.exception.UserNotFoundException;
import com.hcl.booklendingsystem.repository.AuthorRepository;
import com.hcl.booklendingsystem.repository.BookRepository;
import com.hcl.booklendingsystem.repository.UserRepository;
import com.hcl.booklendingsystem.util.BookLendingSystemConstants;

import lombok.extern.slf4j.Slf4j;

/**
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

	Author author;
	Book book;
	CommonResponse commonResponse;

	/**
	 * @param bookRequestDetails
	 * @return CommonResponse
	 * 
	 *         This method for add a book for already registered user and based on
	 *         author also.
	 */
	@Transactional
	public CommonResponse addBook(BookRequestDetails bookRequestDetails) {

		log.info(BookLendingSystemConstants.BOOK_SERVICE);
		Optional<User> user = userRepository.findById(bookRequestDetails.getUserId());

		if (!user.isPresent()) {
			throw new UserNotFoundException(BookLendingSystemConstants.USER_NOT_EXISTS);

		}
		Optional<Author> authorr = authorRepository.findByAuthorName(bookRequestDetails.getAuthorName());
		if (!authorr.isPresent()) {
			author = new Author();
			author.setAuthorName(bookRequestDetails.getAuthorName());
			authorRepository.save(author);

			book = new Book();
			book.setAuthorId(author.getAuthorId());
			book.setBookName(bookRequestDetails.getBookName());
			book.setUserId(bookRequestDetails.getUserId());
			book.setBookStatus(BookLendingSystemConstants.AVAILABLE);
			bookRepository.save(book);
		} else {
			book = new Book();
			book.setAuthorId(authorr.get().getAuthorId());
			book.setBookName(bookRequestDetails.getBookName());
			book.setUserId(bookRequestDetails.getUserId());
			book.setBookStatus(BookLendingSystemConstants.AVAILABLE);
			bookRepository.save(book);
		}
		commonResponse = new CommonResponse();

		return commonResponse;
	}

}
