package ru.kuranov.pull.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.error.message.ErrorMessages.SINGLE_OPTION_MUST_CONTAINS_ONE_TRUE_ANSWER;

@AllArgsConstructor
public class SingleOptionContainAnswerException extends RuntimeException {

    @Getter
    private final String message = SINGLE_OPTION_MUST_CONTAINS_ONE_TRUE_ANSWER.toString();
}
