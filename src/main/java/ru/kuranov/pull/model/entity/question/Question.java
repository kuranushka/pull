package ru.kuranov.pull.model.entity.question;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.kuranov.pull.model.type.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Schema(example = "SIMPLE_STRING_ANSWER")
    private Type type;

    @Column(name = "question")
    @Schema(example = "Сколько звёзд на небе?")
    private String question;

    @ElementCollection
    @Schema(example = "На небе бесчисленное количество звёзд")
    private List<String> answerOptions;
}
