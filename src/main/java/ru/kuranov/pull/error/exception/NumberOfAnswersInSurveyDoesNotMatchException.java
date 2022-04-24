package ru.kuranov.pull.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.error.message.ErrorMessages.NUMBER_OF_ANSWERS_IN_SURVEY_DOES_NOT_MATCH;

@AllArgsConstructor
public class NumberOfAnswersInSurveyDoesNotMatchException extends RuntimeException {

    @Getter
    private final String message = NUMBER_OF_ANSWERS_IN_SURVEY_DOES_NOT_MATCH.toString();
}
