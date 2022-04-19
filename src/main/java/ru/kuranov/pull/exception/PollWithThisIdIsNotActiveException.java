package ru.kuranov.pull.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.exception.ErrorMessages.POLL_WITH_THIS_ID_IS_NOT_ACTIVE_EXCEPTION;

@AllArgsConstructor
public class PollWithThisIdIsNotActiveException extends RuntimeException {

    @Getter
    private final String message = POLL_WITH_THIS_ID_IS_NOT_ACTIVE_EXCEPTION.toString();
}
