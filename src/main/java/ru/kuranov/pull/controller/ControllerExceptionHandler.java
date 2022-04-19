package ru.kuranov.pull.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kuranov.pull.exception.*;

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

    @ExceptionHandler(NumberOfItemsInPullDoesNotMatchException.class)
    ResponseEntity<?> validationElementErrorHandler(NumberOfItemsInPullDoesNotMatchException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SingleOptionContainAnswerException.class)
    ResponseEntity<?> validationElementErrorHandler(SingleOptionContainAnswerException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QuestionTypesDoNotMatchInPullException.class)
    ResponseEntity<?> validationElementErrorHandler(QuestionTypesDoNotMatchInPullException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SimpleStringAnswerIsEmptyException.class)
    ResponseEntity<?> validationElementErrorHandler(SimpleStringAnswerIsEmptyException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MultiOptionAnswerDoesNotContainAnySelectedOptionException.class)
    ResponseEntity<?> validationElementErrorHandler(MultiOptionAnswerDoesNotContainAnySelectedOptionException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
