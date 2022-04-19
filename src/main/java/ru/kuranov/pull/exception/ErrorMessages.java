package ru.kuranov.pull.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorMessages {
    NO_SUCH_PULL_EXCEPTION("Cannot find Pull with that id"),
    POLL_WITH_THIS_ID_IS_NOT_ACTIVE_EXCEPTION("Cannot find active Pull with that id"),
    QUESTION_TYPES_ARE_NOT_SUPPORTED("Types of question not supported"),
    NUMBER_OF_ITEMS_IN_PULL_DOES_NOT_MATCH("Number of items in the pull does not match");

    private final String message;

    @Override
    public String toString() {
        return message;
    }

}
