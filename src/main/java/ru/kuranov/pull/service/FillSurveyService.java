package ru.kuranov.pull.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuranov.pull.error.exception.*;
import ru.kuranov.pull.mapper.FilledSurveyMapper;
import ru.kuranov.pull.model.dto.FilledSurveyDto;
import ru.kuranov.pull.model.entity.answer.Answer;
import ru.kuranov.pull.model.entity.question.Question;
import ru.kuranov.pull.model.entity.survey.FilledSurvey;
import ru.kuranov.pull.model.entity.survey.Survey;
import ru.kuranov.pull.model.type.Type;
import ru.kuranov.pull.repo.AnswerRepo;
import ru.kuranov.pull.repo.FilledSurveyRepo;
import ru.kuranov.pull.repo.SurveyRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FillSurveyService {

    private final FilledSurveyRepo filledSurveyRepo;
    private final FilledSurveyMapper filledSurveyMapper;
    private final SurveyRepo surveyRepo;
    private final AnswerRepo answerRepo;


    public void checkValidFilledSurvey(FilledSurveyDto filledSurveyDto, Long surveySourceId) {

        // извлечение опроса из БД для проверки
        Survey survey = surveyRepo.findById(surveySourceId).orElseThrow();

        // проверка на отсутствие блока с ответами в заполненном опросе
        if (filledSurveyDto.getAnswers() == null) {
            throw new AnswersIsEmptyException();
        }

        // проверка на отсутствие изменений в датах начала и конца опроса в заполненном опросе
        if (!filledSurveyDto.getBeginDate().equals(survey.getBeginDate())
                || !filledSurveyDto.getEndDate().equals(survey.getEndDate())) {
            throw new NotValidDateException();
        }

        // проверка на одиннаковое количество ответов
        if (!(survey.getQuestions().size() == filledSurveyDto.getAnswers().size())) {
            throw new NumberOfAnswersInSurveyDoesNotMatchException();
        }

        // проверка на соответствие ответов типам
        for (Answer answer : filledSurveyDto.getAnswers()) {
            for (Question question : survey.getQuestions()) {
                if (answer.getQuestion().equals(question.getQuestion())) {
                    if (answer.getType().equals(Type.SIMPLE_STRING_ANSWER)) {
                        if (answer.getAnswerOptions().size() == 0) {
                            throw new SimpleStringAnswerIsEmptyException();
                        }
                    }
                    if (answer.getType().equals(Type.SINGLE_OPTION_ANSWER)) {
                        if (answer.getAnswerOptions().size() != 1) {
                            throw new SingleOptionContainAnswerException();
                        }
                    }
                    if (answer.getType().equals(Type.MULTI_OPTION_ANSWER)) {
                        if (answer.getAnswerOptions().size() == 0) {
                            throw new MultiOptionAnswerDoesNotContainAnySelectedOptionException();
                        }
                    }
                }
            }
        }
    }

    public void saveFilledSurvey(FilledSurveyDto filledSurveyDto, Long surveySourceId, Long interviewerId) {
        FilledSurvey filledSurvey = filledSurveyMapper.getFillSurvey(filledSurveyDto);

        filledSurvey.setSourceSurveyId(surveySourceId);
        filledSurvey.setInterviewerId(interviewerId);

        List<Answer> answers = answerRepo.saveAll(filledSurvey.getAnswers());
        filledSurvey.setAnswers(answers);
        filledSurveyRepo.save(filledSurvey);
    }


    public List<FilledSurveyDto> findAllByInterviewerId(Long interviewerId) {
        return filledSurveyRepo.findAllByInterviewerId(interviewerId).stream()
                .map(filledSurveyMapper::getFillSurveyDto)
                .collect(Collectors.toList());
    }
}
