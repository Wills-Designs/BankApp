package com.revature.exceptions;
/** exception that handles issues with duplicates  */
public class ResourcePersistenceException extends RuntimeException{

    public ResourcePersistenceException(String message) {
        super(message);
    }
}
