package ru.kuranov.pull.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.error.message.ErrorMessages.SURVEY_WITH_THIS_ID_IS_NOT_ACTIVE_EXCEPTION;

@AllArgsConstructor
public class SurveyWithThisIdIsNotActiveException extends RuntimeException {

    @Getter
    private final String message = SURVEY_WITH_THIS_ID_IS_NOT_ACTIVE_EXCEPTION.toString();
}
