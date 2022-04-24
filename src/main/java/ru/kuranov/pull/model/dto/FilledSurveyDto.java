package ru.kuranov.pull.model.dto;

import lombok.*;
import ru.kuranov.pull.model.entity.answer.Answer;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilledSurveyDto {

    private LocalDate beginDate;
    private LocalDate endDate;
    private String description;
    private Long sourceSurveyId;
    private Long interviewerId;

    @NotNull
    private List<Answer> answers;
}
