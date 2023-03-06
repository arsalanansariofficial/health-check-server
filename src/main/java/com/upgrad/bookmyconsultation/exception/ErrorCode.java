package com.upgrad.bookmyconsultation.exception;

/**
 * function getCode() returns the error code associated with api response
 * function getDefaultMessage() returns the default error message associated with the code
 * created by Arsalan Ansari
 */
public interface ErrorCode {
    String getCode();
    String getDefaultMessage();
}