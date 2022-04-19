package ru.kuranov.pull.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorMessages {
    NO_SUCH_PULL_EXCEPTION("Cannot find Pull with that id"),
    POLL_WITH_THIS_ID_IS_NOT_ACTIVE_EXCEPTION("Cannot find active Pull with that id"),
    QUESTION_TYPES_ARE_NOT_SUPPORTED("Types of question not supported"),
    NUMBER_OF_ITEMS_IN_PULL_DOES_NOT_MATCH("Number of items in the pull does not match"),
    SINGLE_OPTION_MUST_CONTAINS_ONE_TRUE_ANSWER("SINGLE_OPTION must contain only one true answer"),
    QUESTION_TYPES_DO_NOT_MATCH_IN_PULL("The question types do not match in the pull"),
    SIMPLE_STRING_ANSWER_IS_EMPTY("SIMPLE_STRING answer is empty"),
    MULTI_OPTION_ANSWER_DOES_NOT_CONTAIN_ANY_SELECTED_OPTION("MULTI_OPTION answer does not contain any selected option");

    private final String message;

    @Override
    public String toString() {
        return message;
    }

}
