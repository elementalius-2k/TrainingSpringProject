package com.example.trainingspringproject.controllers;

import com.example.trainingspringproject.exceptions.AlreadyExistsException;
import com.example.trainingspringproject.exceptions.NotEnoughProductsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(value = {NothingFoundException.class, NotEnoughProductsException.class})
    public ResponseEntity<Object> handleNotFoundException(RuntimeException e) {
        logger.error(e.getMessage(), e);
        return handle(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    public ResponseEntity<Object> handleConflictException(AlreadyExistsException e) {
        logger.error(e.getMessage(), e);
        return handle(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleBadRequestException(RuntimeException e) {
        logger.error(e.getMessage(), e);
        return handle(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleOtherException(RuntimeException e) {
        logger.error(e.getMessage(), e);
        return handle(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> handle(RuntimeException e, HttpStatus status) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("message", e.getMessage());
        return new ResponseEntity<>(body, status);
    }
}
