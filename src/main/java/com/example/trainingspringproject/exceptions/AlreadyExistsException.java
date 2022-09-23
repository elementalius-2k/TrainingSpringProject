package com.example.trainingspringproject.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message + " is already exists in database.");
    }
}
