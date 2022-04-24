package ru.kuranov.pull.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.error.message.ErrorMessages.NO_QUESTION_EXCEPTION;

@AllArgsConstructor
public class NoQuestionException extends RuntimeException {

    @Getter
    private final String message = NO_QUESTION_EXCEPTION.toString();
}
