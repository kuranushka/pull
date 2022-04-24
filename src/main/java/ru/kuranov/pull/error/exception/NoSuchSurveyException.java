package ru.kuranov.pull.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.error.message.ErrorMessages.NO_SUCH_SURVEY_EXCEPTION;

@AllArgsConstructor
public class NoSuchSurveyException extends RuntimeException {

    @Getter
    private final String message = NO_SUCH_SURVEY_EXCEPTION.toString();
}
