package ru.kuranov.pull.dto;

import lombok.*;
import ru.kuranov.pull.entity.Item;

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
    private Long pullSourceId;
    private Long interviewerId;

    @NotNull
    private List<Item> items;
}
