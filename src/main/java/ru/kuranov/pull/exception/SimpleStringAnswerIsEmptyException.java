package ru.kuranov.pull.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.exception.ErrorMessages.QUESTION_TYPES_DO_NOT_MATCH_IN_PULL;
import static ru.kuranov.pull.exception.ErrorMessages.SIMPLE_STRING_ANSWER_IS_EMPTY;

@AllArgsConstructor
public class SimpleStringAnswerIsEmptyException extends RuntimeException {

    @Getter
    private final String message = SIMPLE_STRING_ANSWER_IS_EMPTY.toString();
}
