package com.hcl.booklendingsystem.exception;

public class BookIdAndUserIdEmptyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BookIdAndUserIdEmptyException(String message) {
		super(message);
	}

}
