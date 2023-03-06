package com.upgrad.bookmyconsultation.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.MessageFormat;

/**
 * serialVersionUID is the unique number of the exception
 * ErrorCode is the error code corresponds for the specific error
 * cause is the actual cause of that error
 * parameters are the parameters that throws that error
 * constructor UnexpectedException() initialize the variables'
 * method getErrorCode() returns the error code
 * method getErrorMessage() returns the error message
 * method getCause() returns the cause
 * method printStackTrace() prints the detailed cause of the error
 */
public class UnexpectedException extends RuntimeException {

    private static final long serialVersionUID = 2737472949025937415L;

    private final ErrorCode errorCode;

    private Throwable cause;

    private final Object[] parameters;

    public UnexpectedException(final ErrorCode errorCode, final Object... parameters) {
        super();
        this.errorCode = errorCode;
        this.parameters = parameters;
    }

    public UnexpectedException(final ErrorCode errorCode, final Throwable cause, final Object... parameters) {
        super();
        this.errorCode = errorCode;
        this.cause = cause;
        this.parameters = parameters;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format(errorCode.getDefaultMessage(), this.parameters);
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public final void printStackTrace(final PrintStream stream) {
        super.printStackTrace(stream);
    }

    @Override
    public final void printStackTrace(final PrintWriter writer) {
        super.printStackTrace(writer);
    }
}