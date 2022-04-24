package ru.kuranov.pull.error.exception;

import lombok.AllArgsConstructor;

import static ru.kuranov.pull.error.message.ErrorMessages.INVALID_VALUE_IN_FIELD;

@AllArgsConstructor
public class NotValidSurveyDtoDateException extends RuntimeException {

    private String error;


    @Override
    public String toString() {
        return "NotValidSurveyDtoDataException: " +
                INVALID_VALUE_IN_FIELD + ":" + error;
    }
}
