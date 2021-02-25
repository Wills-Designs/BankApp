package com.revature.exceptions;

/** exception that occurs if authentication of credentials fails */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(){

        super("Authentication Failed");
    }
}
