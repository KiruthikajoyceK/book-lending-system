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
public class BookHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookHistoryId;
	private Integer bookId;
	private Integer userId;
	private LocalDateTime borrowDate;
	public Integer getBookHistoryId() {
		return bookHistoryId;
	}
	public void setBookHistoryId(Integer bookHistoryId) {
		this.bookHistoryId = bookHistoryId;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public LocalDateTime getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(LocalDateTime borrowDate) {
		this.borrowDate = borrowDate;
	}
	
	
}
