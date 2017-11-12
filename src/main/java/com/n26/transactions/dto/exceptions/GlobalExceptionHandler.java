package com.n26.transactions.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.n26.transactions.errors.ApiError;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(GenericException.class)
	@ResponseBody
	public ApiError processGenericException(GenericException ex) {
		return new ApiError(HttpStatus.BAD_REQUEST.name(), ex.getMessage(), ex);
	}

	/**
	 * Error response is not going due to NO CONTENT status code
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<ApiError> processMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		return new ResponseEntity<ApiError>(new ApiError(HttpStatus.NO_CONTENT.name(),
				ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage(), ex), HttpStatus.NO_CONTENT);
	}

}
