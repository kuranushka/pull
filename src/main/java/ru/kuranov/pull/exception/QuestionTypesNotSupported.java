package ru.kuranov.pull.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.exception.ErrorMessages.QUESTION_TYPES_ARE_NOT_SUPPORTED;

@AllArgsConstructor
public class QuestionTypesNotSupported extends RuntimeException {

    @Getter
    private final String message = QUESTION_TYPES_ARE_NOT_SUPPORTED.toString();
}
