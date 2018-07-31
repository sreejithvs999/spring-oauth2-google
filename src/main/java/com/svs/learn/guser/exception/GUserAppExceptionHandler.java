package com.svs.learn.guser.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GUserAppExceptionHandler {

	@ExceptionHandler(value = GUserAppException.class)
	public ResponseEntity<Object> handleMyLitmus7Exception(GUserAppException e) {

		Map<?, ?> err = Collections.singletonMap("message", e.getMessage());
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleInvalidRequestException(MethodArgumentNotValidException mex) {

		FieldError fe = mex.getBindingResult().getFieldError();
		Map<?, ?> err = fe != null ? Collections.singletonMap("message", fe.getDefaultMessage())
				: Collections.singletonMap("message", "Error processing request");

		return new ResponseEntity<Object>(err, HttpStatus.BAD_REQUEST);
	}
}
