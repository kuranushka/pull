package ru.kuranov.pull.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.error.message.ErrorMessages.ANSWER_OPTIONS_IS_EMPTY;

@AllArgsConstructor
public class AnswerOptionsIsEmptyException extends RuntimeException {

    @Getter
    private final String message = ANSWER_OPTIONS_IS_EMPTY.toString();
}
