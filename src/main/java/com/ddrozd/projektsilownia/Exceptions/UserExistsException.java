package com.ddrozd.projektsilownia.Exceptions;

public class UserExistsException extends Exception {
    
    public UserExistsException(String errorMessage) {
        super(errorMessage);
    }

}
