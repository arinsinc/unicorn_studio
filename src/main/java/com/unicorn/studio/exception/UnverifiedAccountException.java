package com.unicorn.studio.exception;

public class UnverifiedAccountException extends RuntimeException{
    public UnverifiedAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnverifiedAccountException(String message) {
        super(message);
    }
}
