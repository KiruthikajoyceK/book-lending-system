package com.hcl.booklendingsystem.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.booklendingsystem.entity.BookHistory;

@Repository
public interface BookHistoryRepository extends JpaRepository<BookHistory, Integer> {

	Optional<List<BookHistory>> findByBorrowDateLessThan(LocalDateTime borrowDate);

}
