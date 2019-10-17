package com.hcl.booklendingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.booklendingsystem.entity.Author;

@Repository

public interface AuthorRepository extends JpaRepository<Author, Integer> {

	public Optional<Author> findByAuthorName(String authorName);

}
