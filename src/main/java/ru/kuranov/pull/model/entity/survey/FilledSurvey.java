package ru.kuranov.pull.model.entity.survey;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.kuranov.pull.model.entity.answer.Answer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "fill_survey")
public class FilledSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "begin_date")
    @Schema(example = "2022-01-01")
    private LocalDate beginDate;

    @Column(name = "end_date")
    @Schema(example = "2022-12-31")
    private LocalDate endDate;

    @Column(name = "description")
    @Schema(example = "Опрос посвящённый дню учителя")
    private String description;

    @Column(name = "source_survey_id")
    @Schema(example = "10")
    private Long sourceSurveyId;

    @Column(name = "interviewer_id")
    @Schema(example = "12")
    private Long interviewerId;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Answer> answers;
}
