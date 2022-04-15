package ru.kuranov.pull.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuranov.pull.dto.FillPullDto;
import ru.kuranov.pull.entity.fill.FillItem;
import ru.kuranov.pull.entity.fill.FillPull;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FillPullMapper {

    public FillPullDto getFillPull(FillPull fillPull) {
        return FillPullDto.builder()
                .beginDate(fillPull.getBeginDate())
                .endDate(fillPull.getEndDate())
                .description(fillPull.getDescription())
                .sourcePullId(fillPull.getSourcePullId())
                .interviewerId(fillPull.getInterviewerId())
                .fillItems(fillPull.getFillItems())
                .build();
    }

    public FillPull getFillPull(FillPullDto fillPullDto) {

        List<FillItem> fillItemList = fillPullDto.getFillItems()
                .stream()
                .map(fillItem -> FillItem.builder()
                        .question(fillItem.getQuestion())
                        .type(fillItem.getType())
                        .answer(fillItem.getAnswer())
                        .build())
                .collect(Collectors.toList());

        return FillPull.builder()
                .beginDate(fillPullDto.getBeginDate())
                .endDate(fillPullDto.getEndDate())
                .description(fillPullDto.getDescription())
                .sourcePullId(fillPullDto.getSourcePullId())
                .interviewerId(fillPullDto.getInterviewerId())
                .fillItems(fillItemList)
                .build();
    }
}
