package ru.kuranov.pull.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.error.message.ErrorMessages.MULTI_OPTION_ANSWER_DOES_NOT_CONTAIN_ANY_SELECTED_OPTION;

@AllArgsConstructor
public class MultiOptionAnswerDoesNotContainAnySelectedOptionException extends RuntimeException {

    @Getter
    private final String message = MULTI_OPTION_ANSWER_DOES_NOT_CONTAIN_ANY_SELECTED_OPTION.toString();
}
