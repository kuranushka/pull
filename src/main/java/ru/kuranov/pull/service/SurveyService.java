package ru.kuranov.pull.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kuranov.pull.error.exception.*;
import ru.kuranov.pull.mapper.SurveyMapper;
import ru.kuranov.pull.model.dto.SurveyDto;
import ru.kuranov.pull.model.entity.question.Question;
import ru.kuranov.pull.model.entity.survey.Survey;
import ru.kuranov.pull.model.type.Type;
import ru.kuranov.pull.repo.QuestionRepo;
import ru.kuranov.pull.repo.SurveyRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepo surveyRepo;
    private final SurveyMapper surveyMapper;
    private final QuestionRepo questionRepo;

    public List<SurveyDto> findAll() {
        return surveyRepo.findAll().stream()
                .map(surveyMapper::getSurveyDto)
                .collect(Collectors.toList());
    }

    public List<SurveyDto> findAllByIsActive() {
        return surveyRepo.findAll().stream()
                .filter(Survey::getIsActive)
                .map(surveyMapper::getSurveyDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SurveyDto save(SurveyDto surveyDto) {
        Survey survey = surveyMapper.getSurvey(surveyDto);

        List<Question> questions = questionRepo.saveAll(survey.getQuestions());
        survey.setQuestions(questions);

        Survey savedSurvey = surveyRepo.save(survey);
        return surveyMapper.getSurveyDto(savedSurvey);
    }

    public void checkValidQuestions(List<Question> questions) {

        // проверка на остутсвие блока с вопросами в опросе
        if (questions.size() == 0) {
            throw new QuestionIsEmptyException();
        }

        // проверка на отсутствие самих вопросов
        if (questions.stream()
                .peek(question -> {
                    if (question.getQuestion() == null) {
                        throw new NoQuestionException();
                    }
                })
                .anyMatch(question -> question.getQuestion().isEmpty())) {
            throw new QuestionIsEmptyException();
        }

        // проверка на отсутствие вариантов ответов для вопросов с предусмотренными вариантами ответов
        if (questions.stream()
                .peek(question -> {
                    if (!question.getType().equals(Type.SIMPLE_STRING_ANSWER) && question.getAnswerOptions() == null) {
                        throw new AnswerOptionsIsEmptyException();
                    }
                })
                .filter(question -> !question.getType().equals(Type.SIMPLE_STRING_ANSWER))
                .anyMatch(question -> question.getAnswerOptions().size() == 0)) {
            throw new AnswerOptionsIsEmptyException();
        }
    }

    public SurveyDto findById(Long id) {
        Optional<Survey> optionalSurvey = surveyRepo.findById(id);
        return surveyMapper.getSurveyDto(optionalSurvey.orElseThrow(NoSuchSurveyException::new));
    }


    @Transactional
    public void update(SurveyDto surveyDto) {
        Survey survey = surveyRepo.findById(surveyDto.getId()).orElseThrow();

        // проверка на изменение даты на чала опроса
        if (!surveyDto.getBeginDate().equals(survey.getBeginDate())) {
            throw new CanNotChangeSurveyBeginDateException();
        }

        List<Question> questions = surveyDto.getQuestions().stream().map(question -> Question.builder()
                        .question(question.getQuestion())
                        .type(question.getType())
                        .answerOptions(question.getAnswerOptions())
                        .build())
                .collect(Collectors.toList());
        questionRepo.saveAll(questions);


        survey.setEndDate(surveyDto.getEndDate());
        survey.setDescription(surveyDto.getDescription());
        survey.setIsActive(surveyDto.getIsActive());
        survey.setQuestions(questions);

        surveyRepo.save(survey);
    }

    public void deleteSurvey(Long id) {
        Survey survey = surveyRepo.findById(id).orElseThrow(NoSuchSurveyException::new);
        surveyRepo.delete(survey);
    }

    public boolean isActiveSurvey(Long id) {
        Optional<Survey> optionalSurvey = surveyRepo.findById(id);
        return optionalSurvey.orElseThrow(NoSuchSurveyException::new).getIsActive();

    }

    public void checkValidDate(SurveyDto surveyDto) {
        if (surveyDto.getEndDate().isBefore(surveyDto.getBeginDate())) {
            throw new NotValidDateException();
        }
    }
}
