package com.upgrad.bookmyconsultation.handler;

import com.upgrad.bookmyconsultation.constants.UserConstants;
import com.upgrad.bookmyconsultation.controller.ext.ErrorResponse;
import com.upgrad.bookmyconsultation.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.io.PrintWriter;
import java.io.StringWriter;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AuthenticationFailedException.class)
	public final ResponseEntity<ErrorResponse> handleAuthenticationFailedException(AuthenticationFailedException ex, WebRequest request) {
		return new ResponseEntity<>(errorResponse(ex), UNAUTHORIZED);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public final ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
		return new ResponseEntity<>(errorResponse(ex), UNAUTHORIZED);
	}

	@ExceptionHandler(AuthorizationFailedException.class)
	public final ResponseEntity<ErrorResponse> handleAuthorizationFailedException(AuthorizationFailedException ex, WebRequest request) {
		return new ResponseEntity<>(errorResponse(ex), FORBIDDEN);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
		return new ResponseEntity<>(errorResponse(ex), NOT_FOUND);
	}

	@ExceptionHandler(ApplicationException.class)
	public final ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex, WebRequest request) {
		return new ResponseEntity<>(errorResponse(ex), UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(RestException.class)
	public final ResponseEntity<ErrorResponse> handleRestException(RestException ex, WebRequest request) {
		return new ResponseEntity<>(errorResponse(ex), UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<>(errorResponse(ex), INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ErrorResponse> handleInvalidInput(InvalidInputException e) {
		return new ResponseEntity<>(errorResponse(e), HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param slotUnavailableException object of the slotUnavailableException
	 * @return ResponseEntity<ErrorResponse> to provide a pretty response
	 * created by Arsalan Ansari
	 */
	@ExceptionHandler(SlotUnavailableException.class)
	public ResponseEntity<ErrorResponse> handleSlotUnavailableException(SlotUnavailableException slotUnavailableException) {

		slotUnavailableException.printStackTrace();
		final StringWriter stringWriter = new StringWriter();
		slotUnavailableException.printStackTrace(new PrintWriter(stringWriter));

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(UserConstants.SLOT_UNAVAILABLE_ERROR_CODE);
		errorResponse.setMessage(UserConstants.SLOT_UNAVAILABLE_ERROR_MSG);
		errorResponse.setRootCause(stringWriter.getBuffer().toString());

		return ResponseEntity.badRequest().body(errorResponse);
	}

	private ErrorResponse errorResponse(final ApplicationException exc) {
		exc.printStackTrace();
		return new ErrorResponse().code(exc.getErrorCode().getCode()).message(exc.getMessage());
	}

	private ErrorResponse errorResponse(final RestException exc) {
		exc.printStackTrace();
		return new ErrorResponse().code(exc.getErrorCode().getCode()).message(exc.getMessage());
	}

	private ErrorResponse errorResponse(final RuntimeException exc) {
		exc.printStackTrace();
		final StringWriter stringWriter = new StringWriter();
		exc.printStackTrace(new PrintWriter(stringWriter));
		String message = exc.getMessage();
		if (message == null) {
			message = GenericErrorCode.GEN_001.getDefaultMessage();
		}
		return new ErrorResponse().code(GenericErrorCode.GEN_001.getCode()).message(message).rootCause(stringWriter.getBuffer().toString());
	}

	private ErrorResponse errorResponse(final InvalidInputException invalidInputException) {
		invalidInputException.printStackTrace();
		final StringWriter stringWriter = new StringWriter();
		invalidInputException.printStackTrace(new PrintWriter(stringWriter));
		String message = invalidInputException.getMessage();
		if (message == null) {
			message = GenericErrorCode.GEN_001.getDefaultMessage();
		}
		return new ErrorResponse().code(GenericErrorCode.GEN_001.getCode()).message(message).rootCause(stringWriter.getBuffer().toString());
	}

}