package ru.kuranov.pull.entity.fill;

import lombok.*;
import ru.kuranov.pull.entity.type.Type;

import javax.persistence.*;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "fill_item")
public class FillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question")
    private String question;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ElementCollection
    private Map<String, Boolean> answer;
}
