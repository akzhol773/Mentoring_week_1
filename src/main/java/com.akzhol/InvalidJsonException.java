package com.akzhol;

public class InvalidJsonException extends RuntimeException{
    public InvalidJsonException(String message) {
        super(message);
    }
}
