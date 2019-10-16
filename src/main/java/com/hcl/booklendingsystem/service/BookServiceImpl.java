package com.hcl.booklendingsystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hcl.booklendingsystem.dto.GetBooksOutput;
import com.hcl.booklendingsystem.entity.Author;
import com.hcl.booklendingsystem.entity.Book;
import com.hcl.booklendingsystem.repository.AuthorRepository;
import com.hcl.booklendingsystem.repository.BookRepository;
import com.hcl.booklendingsystem.util.BookLendingSystemConstants;

/**
 * 
 * @author sairam
 *
 */
@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;
	@Autowired
	AuthorRepository authorRepository;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);


	/**
	 * @author sairam
	 * @param pageNumber is mandatory
	 * @apiNote get the list of books available
	 * @return List<GetBooksOutput> list of GetBooksOutput contains book details
	 */
	@Override
	public List<GetBooksOutput> getBooks(Integer pageNumber) {
		
		LOGGER.info("BookServiceImpl --> getBooks");
		LOGGER.debug("BookServiceImpl --> getBooks page number:{}",pageNumber);
		Pageable paging = PageRequest.of(pageNumber, BookLendingSystemConstants.PAGENATION_SIZE);
		Page<Book> books = bookRepository.findAll(paging);
		new GetBooksOutput();
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
		LOGGER.info("BookServiceImpl --> getBooks get");

		return getBooksOutputs;

		
	}

	
}
