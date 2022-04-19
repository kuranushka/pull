package ru.kuranov.pull.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kuranov.pull.exception.NoSuchPullException;
import ru.kuranov.pull.exception.PollWithThisIdIsNotActiveException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<?> validationElementErrorHandler(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoSuchPullException.class)
    ResponseEntity<?> validationElementErrorHandler(NoSuchPullException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PollWithThisIdIsNotActiveException.class)
    ResponseEntity<?> validationElementErrorHandler(PollWithThisIdIsNotActiveException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<?> validationElementErrorHandler(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
