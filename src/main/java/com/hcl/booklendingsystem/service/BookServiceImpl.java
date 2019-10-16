package com.hcl.booklendingsystem.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.hcl.booklendingsystem.dto.BookRequestDetails;
import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.dto.GetBooksOutput;
import com.hcl.booklendingsystem.entity.Author;
import com.hcl.booklendingsystem.entity.Book;
import com.hcl.booklendingsystem.entity.BookRequest;
import com.hcl.booklendingsystem.entity.User;
import com.hcl.booklendingsystem.exception.BookNotFoundException;
import com.hcl.booklendingsystem.exception.UserNotFoundException;
import com.hcl.booklendingsystem.repository.AuthorRepository;
import com.hcl.booklendingsystem.repository.BookRepository;
import com.hcl.booklendingsystem.repository.BookRequestRepository;
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
	
	@Autowired
	BookRequestRepository bookRequestRepository;

	Author author;
	Book book;
	CommonResponse commonResponse;

	/**
	 * @author sairam
	 * @param pageNumber is mandatory
	 * @apiNote get the list of books available
	 * @return List<GetBooksOutput> list of GetBooksOutput contains book details
	 */
	@Override
	public List<GetBooksOutput> getBooks(Integer pageNumber) {

		log.info("BookServiceImpl --> getBooks");
		log.debug("BookServiceImpl --> getBooks page number:{}", pageNumber);
		Pageable paging = PageRequest.of(pageNumber, BookLendingSystemConstants.PAGENATION_SIZE);
		Page<Book> books = bookRepository.findAll(paging);
		List<GetBooksOutput> getBooksOutputs = new ArrayList<>();

		if (books.hasContent()) {
			books.getContent().forEach(book -> {
				GetBooksOutput getBooksOutput = new GetBooksOutput();
				Optional<Author> authors = authorRepository.findById(book.getAuthorId());
				authors.ifPresent(author -> getBooksOutput.setAuthorName(author.getAuthorName()));
				BeanUtils.copyProperties(book, getBooksOutput);
				getBooksOutputs.add(getBooksOutput);
			});

		}
		log.info("BookServiceImpl --> getBooks get");

		return getBooksOutputs;

	}

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

	/**
	 * This method will accept bookId and userId and throw BookNotFound exception if the book is not exist
	 * and throw UserNotfound exception if user is not present,else save bookRequest object in respective table
	 * by calling save method in userRepository.
	 * @param bookId
	 * @param userId
	 * @return Optional<BookRequest> 
	 */
	@Override
	public Optional<BookRequest> requestBook(Integer bookId, Integer userId) {
		Optional<Book> bookOptional=bookRepository.findById(bookId);
		if(!bookOptional.isPresent())
			throw new BookNotFoundException(BookLendingSystemConstants.BOOK_NOT_FOUND_EXCEPTION);
		Optional<User>userOptional=userRepository.findById(userId);
		if(!userOptional.isPresent())
			throw new UserNotFoundException(BookLendingSystemConstants.USER_NOT_FOUND);
		BookRequest bookRequest=new BookRequest();
		bookRequest.setBookId(bookId);
		bookRequest.setUserId(userId);
		bookRequest.setBookRequestDate(LocalDateTime.now());
		bookRequest=bookRequestRepository.save(bookRequest);
		return Optional.of(bookRequest);
	}

}
