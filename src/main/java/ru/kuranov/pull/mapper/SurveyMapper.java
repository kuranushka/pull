package ru.kuranov.pull.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuranov.pull.model.dto.SurveyDto;
import ru.kuranov.pull.model.entity.survey.Survey;

@Service
@RequiredArgsConstructor
public class SurveyMapper {

    public Survey getSurvey(SurveyDto surveyDto) {
        return Survey.builder()
                .id(surveyDto.getId())
                .beginDate(surveyDto.getBeginDate())
                .endDate(surveyDto.getEndDate())
                .description(surveyDto.getDescription())
                .isActive(surveyDto.getIsActive())
                .questions(surveyDto.getQuestions())
                .build();
    }

    public SurveyDto getSurveyDto(Survey survey) {
        return SurveyDto.builder()
                .id(survey.getId())
                .beginDate(survey.getBeginDate())
                .endDate(survey.getEndDate())
                .description(survey.getDescription())
                .isActive(survey.getIsActive())
                .questions(survey.getQuestions())
                .build();
    }
}
