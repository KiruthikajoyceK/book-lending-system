package com.hcl.booklendingsystem.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.booklendingsystem.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	@Query(value = "SELECT DISTINCT b.book_name,b.book_id,b.author_id,b.book_status,b.user_id FROM book b,author a  WHERE b.author_id=a.author_id AND a.author_name like %:authorName% OR b.book_name like %:bookName%   \n#pageable\n", nativeQuery = true)
	List<Book> searchBookByAuthorNameOrBookName(String bookName, String authorName, Pageable pageable);

	@Query(value = "SELECT DISTINCT b.book_name,b.book_id,b.author_id,b.book_status,b.user_id FROM book b,author a  WHERE b.author_id=a.author_id AND a.author_name like %:authorName% AND b.book_name like %:bookName%   \n#pageable\n", nativeQuery = true)
	List<Book> searchBookByAuthorNameAndBookName(String bookName, String authorName, Pageable pageable);

}
