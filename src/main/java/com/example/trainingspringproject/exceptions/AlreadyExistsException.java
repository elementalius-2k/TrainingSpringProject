package com.example.trainingspringproject.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException() {
        super("Entity with entered data already exists in the database.");
    }
    public AlreadyExistsException(String message) {
        super(message + " is already exists in database.");
    }
}
