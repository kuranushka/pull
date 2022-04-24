package ru.kuranov.pull.error.message;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorMessages {
    NO_SUCH_SURVEY_EXCEPTION("Cannot find Survey with that id"),
    SURVEY_WITH_THIS_ID_IS_NOT_ACTIVE_EXCEPTION("Cannot find active Survey with that id"),
    QUESTION_IS_EMPTY("Question is empty"),
    NUMBER_OF_ANSWERS_IN_SURVEY_DOES_NOT_MATCH("Number of answers in the Survey does not match"),
    SINGLE_OPTION_MUST_CONTAINS_ONE_TRUE_ANSWER("SINGLE_OPTION must contain only one true answer"),
    ANSWERS_TYPES_DO_NOT_MATCH_IN_SURVEY("The answers types do not match in the Survey"),
    SIMPLE_STRING_ANSWER_IS_EMPTY("SIMPLE_STRING answer is empty"),
    MULTI_OPTION_ANSWER_DOES_NOT_CONTAIN_ANY_SELECTED_OPTION("MULTI_OPTION answer does not contain any selected option"),
    INVALID_VALUE_IN_FIELD("Invalid value in field"),
    ANSWER_OPTIONS_IS_EMPTY("Answer options is empty"),
    ANSWERS_IS_EMPTY_EXCEPTION("Answers is empty"),
    CAN_NOT_CHANGE_SURVEY_BEGIN_DATE("Can not change Survey begin date"),
    NOT_VALID_DATE_EXCEPTION("BeginDate or EndDate is not Valid"),
    NO_QUESTION_EXCEPTION("No question in survey"),
    DIFFERENT_ANSWER_TYPE_EXCEPTION("Different answer type exception");

    private final String message;

    @Override
    public String toString() {
        return message;
    }

}
