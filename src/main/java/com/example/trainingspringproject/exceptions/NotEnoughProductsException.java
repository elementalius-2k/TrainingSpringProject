package com.example.trainingspringproject.exceptions;

public class NotEnoughProductsException extends RuntimeException {
    public NotEnoughProductsException(int required, int actual, String productName) {
        super("Required " + required + " copies of product '" + productName + "', but have only " + actual + " copies.");
    }
}
