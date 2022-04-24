package ru.kuranov.pull.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.kuranov.pull.error.message.ErrorMessages.CAN_NOT_CHANGE_SURVEY_BEGIN_DATE;

@AllArgsConstructor
public class CanNotChangeSurveyBeginDateException extends RuntimeException {

    @Getter
    private final String message = CAN_NOT_CHANGE_SURVEY_BEGIN_DATE.toString();
}
