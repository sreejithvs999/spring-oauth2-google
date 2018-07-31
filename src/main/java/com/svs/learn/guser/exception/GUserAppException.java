package com.svs.learn.guser.exception;

public class GUserAppException extends RuntimeException {

	public GUserAppException(String message) {
		super(message);
	}

	public GUserAppException(String message, Throwable t) {
		super(message, t);
	}
}
