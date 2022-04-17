package ru.kuranov.pull.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.exception.ErrorMessages.NO_SUCH_PULL_EXCEPTION;

@AllArgsConstructor
public class NoSuchPullException extends RuntimeException {

    @Getter
    private final String message = NO_SUCH_PULL_EXCEPTION.toString();
}
