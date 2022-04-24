package ru.kuranov.pull.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.error.message.ErrorMessages.QUESTION_IS_EMPTY;

@AllArgsConstructor
public class QuestionIsEmptyException extends RuntimeException {

    @Getter
    private final String message = QUESTION_IS_EMPTY.toString();
}
