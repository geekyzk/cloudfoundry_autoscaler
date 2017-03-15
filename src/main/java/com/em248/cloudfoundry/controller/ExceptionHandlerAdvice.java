package com.em248.cloudfoundry.controller;

import com.em248.cloudfoundry.entity.ErrorMessage;
import com.em248.cloudfoundry.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * 全局异常处理类
 */

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorMessage> entityNotFound(EntityNotFoundException ex){
		ResponseEntity<ErrorMessage> response = new ResponseEntity<>(new ErrorMessage(ex.getMessage()),HttpStatus.NOT_FOUND);
		return response;
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorMessage> illegalArguments(IllegalArgumentException ex){
		ResponseEntity<ErrorMessage> response = new ResponseEntity<>(new ErrorMessage(ex.getMessage()),HttpStatus.BAD_REQUEST);
		return response;
	}
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ErrorMessage> illegalState(IllegalStateException ex){
		ResponseEntity<ErrorMessage> response = new ResponseEntity<>(new ErrorMessage(ex.getMessage()),HttpStatus.CONFLICT);
		return response;
	}
	
}
