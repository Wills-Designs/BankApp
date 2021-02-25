package com.revature.exceptions;

/** custom exception that is thrown if the user enters an invalid request */
public class InvalidRequestException extends RuntimeException{

    public InvalidRequestException () {
        super("invalid request made!");
    }

    public InvalidRequestException(String message) {
        super(message);
    }

}