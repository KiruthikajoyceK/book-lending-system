package com.hcl.booklendingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.booklendingsystem.entity.BookHistory;

@Repository
public interface BookHistoryRepository extends JpaRepository<BookHistory, Integer> {

}
