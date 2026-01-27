package com.ct.usecase.demo.exception;

public class DuplicateResourceException extends ApplicationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateResourceException(String message) {
        super(message);
    }
}