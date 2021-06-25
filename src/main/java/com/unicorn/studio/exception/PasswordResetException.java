package com.unicorn.studio.exception;

public class PasswordResetException extends RuntimeException{
    public PasswordResetException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordResetException(String message) {
        super(message);
    }
}
