package ru.kuranov.pull.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuranov.pull.dto.PullDto;
import ru.kuranov.pull.entity.Pull;

@Service
@RequiredArgsConstructor
public class PullMapper {

    public Pull getPull(PullDto pullDto) {
        return Pull.builder()
                .id(pullDto.getId())
                .beginDate(pullDto.getBeginDate())
                .endDate(pullDto.getEndDate())
                .description(pullDto.getDescription())
                .isActive(pullDto.getIsActive())
                .items(pullDto.getItems())
                .build();
    }

    public PullDto getPullDto(Pull pull) {
        return PullDto.builder()
                .id(pull.getId())
                .beginDate(pull.getBeginDate())
                .endDate(pull.getEndDate())
                .description(pull.getDescription())
                .isActive(pull.getIsActive())
                .items(pull.getItems())
                .build();
    }
}
