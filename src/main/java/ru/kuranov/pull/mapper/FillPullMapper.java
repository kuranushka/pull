package ru.kuranov.pull.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuranov.pull.dto.FillPullDto;
import ru.kuranov.pull.entity.FillPull;

@Service
@RequiredArgsConstructor
public class FillPullMapper {

    public FillPull getFillPull(FillPullDto fillPullDto) {
        return FillPull.builder()
                .beginDate(fillPullDto.getBeginDate())
                .endDate(fillPullDto.getEndDate())
                .description(fillPullDto.getDescription())
                .items(fillPullDto.getItems())
                .build();
    }
}
