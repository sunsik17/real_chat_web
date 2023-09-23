package com.zerobase.real_time_chat.exception;

import static com.zerobase.real_time_chat.type.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.zerobase.real_time_chat.type.ErrorCode.INVALID_REQUEST;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(RealChatWebException.class)
	public ErrorResponse handleRealChatWebException(RealChatWebException e) {
		log.error("{} is occurred.", e.getErrorCode());

		return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("DataIntegrityViolationException is occurred.", e);

		return new ErrorResponse(INVALID_REQUEST, INVALID_REQUEST.getDescription());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		log.error("MethodArgumentNotValidException is occurred.", e);

		return new ErrorResponse(INVALID_REQUEST, INVALID_REQUEST.getDescription());
	}

	@ExceptionHandler(Exception.class)
	public ErrorResponse handleException(Exception e) {
		log.error("Exception is occurred.", e);

		return new ErrorResponse(INTERNAL_SERVER_ERROR,
			INTERNAL_SERVER_ERROR.getDescription()
		);
	}
}
