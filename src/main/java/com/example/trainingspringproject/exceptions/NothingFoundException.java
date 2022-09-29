package com.example.trainingspringproject.exceptions;

public class NothingFoundException extends RuntimeException {
    public NothingFoundException(String entity, String criteria) {
        super("No entities found! Entity: " + entity + ", criteria: " + criteria + ".");
    }
}
