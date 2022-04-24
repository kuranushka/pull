package ru.kuranov.pull.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.kuranov.pull.model.entity.question.Question;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyDto {

    private Long id;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginDate;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String description;

    private Boolean isActive;

    @NotNull
    private List<Question> questions;
}
