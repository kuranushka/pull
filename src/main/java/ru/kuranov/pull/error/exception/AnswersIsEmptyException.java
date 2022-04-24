package ru.kuranov.pull.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.error.message.ErrorMessages.ANSWERS_IS_EMPTY_EXCEPTION;

@AllArgsConstructor
public class AnswersIsEmptyException extends RuntimeException {

    @Getter
    private final String message = ANSWERS_IS_EMPTY_EXCEPTION.toString();
}
