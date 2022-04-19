package ru.kuranov.pull.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.kuranov.pull.entity.main.Item;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PullDto {

    private Long id;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate beginDate;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate endDate;

    private String description;

    private Boolean isActive;

    @NotNull
    private List<Item> items;

}
