package org.seats.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlers {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleDustRabbitException(Exception e) {
		return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}

	private ResponseEntity<?> errorResponse(int httpStatus, String e) {
		return ResponseEntity
			.status(httpStatus)
			.body(new SeatErrorResponse(e));
	}

	private record SeatErrorResponse(String message) {
	}
}
