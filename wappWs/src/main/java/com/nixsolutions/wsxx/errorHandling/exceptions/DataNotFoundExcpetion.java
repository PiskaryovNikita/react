package com.nixsolutions.wsxx.errorHandling.exceptions;

public class DataNotFoundExcpetion extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataNotFoundExcpetion(String message) {
		super(message);
	}
}
