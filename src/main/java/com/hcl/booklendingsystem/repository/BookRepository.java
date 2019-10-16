package com.hcl.booklendingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.booklendingsystem.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
