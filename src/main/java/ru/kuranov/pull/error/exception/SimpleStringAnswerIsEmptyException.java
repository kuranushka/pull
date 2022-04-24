package ru.kuranov.pull.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.error.message.ErrorMessages.SIMPLE_STRING_ANSWER_IS_EMPTY;

@AllArgsConstructor
public class SimpleStringAnswerIsEmptyException extends RuntimeException {

    @Getter
    private final String message = SIMPLE_STRING_ANSWER_IS_EMPTY.toString();
}
