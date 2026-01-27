package com.ct.usecase.demo.exception;

public abstract class ApplicationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ApplicationException(String message) {
        super(message);
    }
}
