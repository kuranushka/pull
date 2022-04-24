package ru.kuranov.pull.model.entity.survey;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.kuranov.pull.model.entity.question.Question;

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
@Table(name = "survey")
public class Survey {
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

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Question> questions;
}
