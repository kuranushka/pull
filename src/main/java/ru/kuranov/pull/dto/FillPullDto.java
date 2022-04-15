package ru.kuranov.pull.dto;

import lombok.*;
import ru.kuranov.pull.entity.fill.FillItem;
import ru.kuranov.pull.entity.main.Item;
import ru.kuranov.pull.entity.main.Pull;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FillPullDto {

    private LocalDate beginDate;
    private LocalDate endDate;
    private String description;
    private Long sourcePullId;
    private Long interviewerId;

    @NotNull
    private List<FillItem> fillItems;
}
