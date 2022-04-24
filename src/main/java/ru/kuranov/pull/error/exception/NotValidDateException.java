package ru.kuranov.pull.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.error.message.ErrorMessages.NOT_VALID_DATE_EXCEPTION;

@AllArgsConstructor
public class NotValidDateException extends RuntimeException {

    @Getter
    private final String message = NOT_VALID_DATE_EXCEPTION.toString();
}
