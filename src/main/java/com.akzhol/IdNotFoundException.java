package com.akzhol;

public class IdNotFoundException extends RuntimeException{
    public IdNotFoundException(String message) {
        super(message);
    }
}
