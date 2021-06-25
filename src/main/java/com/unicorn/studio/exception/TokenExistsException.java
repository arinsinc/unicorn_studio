package com.unicorn.studio.exception;

public class TokenExistsException extends RuntimeException{
    public TokenExistsException(String message) {
        super(message);
    }

    public TokenExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
