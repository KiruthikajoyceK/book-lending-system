package com.hcl.booklendingsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookRequestDetails {

	private Integer userId;
	private String bookName;
	private String authorName;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

}
