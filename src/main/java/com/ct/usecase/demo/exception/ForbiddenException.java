package com.ct.usecase.demo.exception;


public class ForbiddenException extends ApplicationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ForbiddenException(String message) {
        super(message);
    }
}
