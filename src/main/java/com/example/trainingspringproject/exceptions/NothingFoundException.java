package com.example.trainingspringproject.exceptions;

public class NothingFoundException extends RuntimeException {
    public NothingFoundException() {
        super("Nothing was found in the database.");
    }
    public NothingFoundException(String message) {
        super(message + " was not found in the database.");
    }
}
