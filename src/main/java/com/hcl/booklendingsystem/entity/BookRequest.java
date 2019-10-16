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
	public Integer getBookRequestId() {
		return bookRequestId;
	}
	public void setBookRequestId(Integer bookRequestId) {
		this.bookRequestId = bookRequestId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public LocalDateTime getBookRequestDate() {
		return bookRequestDate;
	}
	public void setBookRequestDate(LocalDateTime bookRequestDate) {
		this.bookRequestDate = bookRequestDate;
	}
	
	
	
}
