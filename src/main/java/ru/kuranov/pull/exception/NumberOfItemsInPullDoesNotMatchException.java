package ru.kuranov.pull.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.exception.ErrorMessages.NUMBER_OF_ITEMS_IN_PULL_DOES_NOT_MATCH;
import static ru.kuranov.pull.exception.ErrorMessages.QUESTION_TYPES_ARE_NOT_SUPPORTED;

@AllArgsConstructor
public class NumberOfItemsInPullDoesNotMatchException extends RuntimeException {

    @Getter
    private final String message = NUMBER_OF_ITEMS_IN_PULL_DOES_NOT_MATCH.toString();
}
