package ru.kuranov.pull.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.error.message.ErrorMessages.ANSWERS_TYPES_DO_NOT_MATCH_IN_SURVEY;


@AllArgsConstructor
public class AnswersTypesDoNotMatchInSurveyException extends RuntimeException {

    @Getter
    private final String message = ANSWERS_TYPES_DO_NOT_MATCH_IN_SURVEY.toString();
}
