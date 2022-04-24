package ru.kuranov.pull.error.exception;

import lombok.AllArgsConstructor;
import ru.kuranov.pull.model.type.Type;

import static ru.kuranov.pull.error.message.ErrorMessages.DIFFERENT_ANSWER_TYPE_EXCEPTION;

@AllArgsConstructor
public class DifferentAnswerTypeException extends RuntimeException {

    private Type type;
    private Type type1;

    @Override
    public String toString() {
        return "DifferentAnswerTypeException: " +
                DIFFERENT_ANSWER_TYPE_EXCEPTION + " need: " + type + " , but there us: " + type1;
    }

}
