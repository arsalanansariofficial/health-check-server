package com.upgrad.bookmyconsultation.enums;

/**
 * NOT_FOUND if the authentication token doesn't exist in the database
 * ACTIVE if the authentication token is present and active
 * EXPIRED if the authentication token is expired
 * LOGGED_OUT if the user logs out of the application
 * CONCURRENT_LOGIN if the user logs in concurrent time
 */
public enum UserAuthTokenStatus {
    NOT_FOUND, ACTIVE, EXPIRED, LOGGED_OUT, CONCURRENT_LOGIN
}