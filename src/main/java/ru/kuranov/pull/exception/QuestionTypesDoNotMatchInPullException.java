package ru.kuranov.pull.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.exception.ErrorMessages.QUESTION_TYPES_DO_NOT_MATCH_IN_PULL;

@AllArgsConstructor
public class QuestionTypesDoNotMatchInPullException extends RuntimeException {

    @Getter
    private final String message = QUESTION_TYPES_DO_NOT_MATCH_IN_PULL.toString();
}
