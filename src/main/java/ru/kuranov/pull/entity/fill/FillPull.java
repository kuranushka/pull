package ru.kuranov.pull.entity.fill;

import lombok.*;
import ru.kuranov.pull.entity.main.Item;
import ru.kuranov.pull.entity.main.Pull;

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
@Table(name = "fill_pull")
public class FillPull {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "begin_date")
    private LocalDate beginDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "description")
    private String description;

    @Column(name = "source_pull_id")
    private Long sourcePullId;

    @Column(name = "interviewer_id")
    private Long interviewerId;

    @OneToMany
    private List<FillItem> fillItems;
}
