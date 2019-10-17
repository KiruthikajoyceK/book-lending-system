package com.hcl.booklendingsystem.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class BookRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookRequestId;
	private Integer userId;
	private Integer bookId;
	private LocalDateTime bookRequestDate;
	
		
	
}
