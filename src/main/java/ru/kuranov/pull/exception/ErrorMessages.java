package ru.kuranov.pull.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorMessages {
    NO_SUCH_PULL_EXCEPTION("Cannot find Pull with that id"),
    POLL_WITH_THIS_ID_IS_NOT_ACTIVE_EXCEPTION("Cannot find active Pull with that id");

    private final String message;

    @Override
    public String toString() {
        return message;
    }

}
