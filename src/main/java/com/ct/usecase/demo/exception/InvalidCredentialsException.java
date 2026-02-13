package com.ct.usecase.demo.exception;


public class InvalidCredentialsException extends ApplicationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCredentialsException(String message) {
        super(message);
    }
}
