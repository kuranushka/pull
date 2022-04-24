package ru.kuranov.pull.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuranov.pull.model.dto.FilledSurveyDto;
import ru.kuranov.pull.model.entity.survey.FilledSurvey;

@Service
@RequiredArgsConstructor
public class FilledSurveyMapper {

    public FilledSurveyDto getFillSurveyDto(FilledSurvey filledSurvey) {
        return FilledSurveyDto.builder()
                .beginDate(filledSurvey.getBeginDate())
                .endDate(filledSurvey.getEndDate())
                .description(filledSurvey.getDescription())
                .sourceSurveyId(filledSurvey.getSourceSurveyId())
                .interviewerId(filledSurvey.getInterviewerId())
                .answers(filledSurvey.getAnswers())
                .build();
    }

    public FilledSurvey getFillSurvey(FilledSurveyDto filledSurveyDto) {
        return FilledSurvey.builder()
                .beginDate(filledSurveyDto.getBeginDate())
                .endDate(filledSurveyDto.getEndDate())
                .description(filledSurveyDto.getDescription())
                .sourceSurveyId(filledSurveyDto.getSourceSurveyId())
                .interviewerId(filledSurveyDto.getInterviewerId())
                .answers(filledSurveyDto.getAnswers())
                .build();
    }
}
