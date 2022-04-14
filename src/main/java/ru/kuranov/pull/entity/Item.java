package ru.kuranov.pull.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "item")
public class Item {

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
