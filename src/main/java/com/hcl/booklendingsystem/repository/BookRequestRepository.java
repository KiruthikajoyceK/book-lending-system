package com.hcl.booklendingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.booklendingsystem.entity.BookRequest;
@Repository
public interface BookRequestRepository extends JpaRepository<BookRequest, Integer>{

}
