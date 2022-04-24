package ru.kuranov.pull.error.handler;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.kuranov.pull.error.exception.NotValidSurveyDtoDateException;
import ru.kuranov.pull.model.dto.SurveyDto;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ErrorHandler {

    public void handle(BindingResult bindingResult) {
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(error -> ((FieldError) error).getField())
                .collect(Collectors.toList());
        List<String> fields = Arrays.stream(SurveyDto.class.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());
        for (String error : errors) {
            if (fields.contains(error)) {
                throw new NotValidSurveyDtoDateException(error);
            }
        }
    }
}
