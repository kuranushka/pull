package ru.kuranov.pull.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<?> validationElementErrorHandler(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

}
