package ru.kuranov.pull.error.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kuranov.pull.error.exception.*;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<?> validationElementErrorHandler(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoSuchSurveyException.class)
    ResponseEntity<?> validationElementErrorHandler(NoSuchSurveyException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SurveyWithThisIdIsNotActiveException.class)
    ResponseEntity<?> validationElementErrorHandler(SurveyWithThisIdIsNotActiveException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<?> validationElementErrorHandler(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NumberOfAnswersInSurveyDoesNotMatchException.class)
    ResponseEntity<?> validationElementErrorHandler(NumberOfAnswersInSurveyDoesNotMatchException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SingleOptionContainAnswerException.class)
    ResponseEntity<?> validationElementErrorHandler(SingleOptionContainAnswerException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AnswersTypesDoNotMatchInSurveyException.class)
    ResponseEntity<?> validationElementErrorHandler(AnswersTypesDoNotMatchInSurveyException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SimpleStringAnswerIsEmptyException.class)
    ResponseEntity<?> validationElementErrorHandler(SimpleStringAnswerIsEmptyException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MultiOptionAnswerDoesNotContainAnySelectedOptionException.class)
    ResponseEntity<?> validationElementErrorHandler(MultiOptionAnswerDoesNotContainAnySelectedOptionException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotValidSurveyDtoDateException.class)
    ResponseEntity<?> validationElementErrorHandler(NotValidSurveyDtoDateException e) {
        return new ResponseEntity<>(e.toString(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(QuestionIsEmptyException.class)
    ResponseEntity<?> validationElementErrorHandler(QuestionIsEmptyException e) {
        return new ResponseEntity<>(e.toString(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AnswerOptionsIsEmptyException.class)
    ResponseEntity<?> validationElementErrorHandler(AnswerOptionsIsEmptyException e) {
        return new ResponseEntity<>(e.toString(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CanNotChangeSurveyBeginDateException.class)
    ResponseEntity<?> validationElementErrorHandler(CanNotChangeSurveyBeginDateException e) {
        return new ResponseEntity<>(e.toString(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotValidDateException.class)
    ResponseEntity<?> validationElementErrorHandler(NotValidDateException e) {
        return new ResponseEntity<>(e.toString(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoQuestionException.class)
    ResponseEntity<?> validationElementErrorHandler(NoQuestionException e) {
        return new ResponseEntity<>(e.toString(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AnswersIsEmptyException.class)
    ResponseEntity<?> validationElementErrorHandler(AnswersIsEmptyException e) {
        return new ResponseEntity<>(e.toString(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DifferentAnswerTypeException.class)
    ResponseEntity<?> validationElementErrorHandler(DifferentAnswerTypeException e) {
        return new ResponseEntity<>(e.toString(), HttpStatus.CONFLICT);
    }


}
