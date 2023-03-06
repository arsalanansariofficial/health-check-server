/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: ResourceConstants.java
 * Date: Sep 28, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package com.upgrad.bookmyconsultation.constants;

/**
 * variable BASE_URL is the base url for accessing resources

 * variable BASE_URL_PATTERN defines the type of pattern used
   for different resources

 * variable BASE_ADMIN_URL is the endpoint for the administrator

 * variable BASE_URL_AUTH is the endpoint for authenticating the user

 * variable HEADER_AUTHORIZATION is the endpoint to attach permissions
   to the user for different resources

 * variable HEADER_CLIENT_ID is the client id that is to be used in the request header

 * variable HEADER_CLIENT_IP_ADDRESS is the ip address of the user

 * variable HEADER_REQUEST_ID is the unique request id

 * variable HEADER_ACCESS_TOKEN is the access token generated from the username and password

 * variable HEADER_LOCATION is the location of the header

 * variable REQUEST_ATTR_REQUEST_CONTEXT is the context of the type of request

 * variable BASIC_AUTH_PREFIX defines the prefix before the basic authentication

 * variable BEARER_AUTH_PREFIX defines the prefix before the bearer authentication

 * variable AUTHORIZED_USER_UUID is the random user id generated for the authorized user
 */
public interface ResourceConstants {

    String BASE_URL = "/v1";

    String BASE_URL_PATTERN = BASE_URL + "/*";

    String BASE_ADMIN_URL = "/v1/admin";

    String BASE_URL_AUTH = BASE_URL + "/auth";

    String HEADER_AUTHORIZATION = "authorization";

    String HEADER_CLIENT_ID = "client-id";

    String HEADER_CLIENT_IP_ADDRESS = "X-FORWARDED-FOR";

    String HEADER_REQUEST_ID = "request-id";

    String HEADER_ACCESS_TOKEN = "access-token";

    String HEADER_LOCATION = "location";

    String REQUEST_ATTR_REQUEST_CONTEXT = "request-context";

    String BASIC_AUTH_PREFIX = "Basic ";

    String BEARER_AUTH_PREFIX = "Bearer ";

    String AUTHORIZED_USER_UUID = "authorized-user-uuid";

}