package ru.kuranov.pull.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import ru.kuranov.pull.error.exception.AnswersIsEmptyException;
import ru.kuranov.pull.error.exception.NotValidDateException;
import ru.kuranov.pull.model.dto.FilledSurveyDto;
import ru.kuranov.pull.model.entity.answer.Answer;
import ru.kuranov.pull.model.type.Type;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "/test.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
class SurveyControllerFilledSurveyTest {

    String baseUrl = "/api/v1/surveys";
    String interviewerId = "1";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void userFillSurveySuccess() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        FilledSurveyDto validFilledSurvey = getValidFilledSurvey();

        mockMvc.perform(put(baseUrl + "/fill/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilledSurvey))
                        .param("interviewerId", "1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(baseUrl + "/interviewer/" + interviewerId + "/fill-survey").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void userFillSurveyNoSuccessDataChanged() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        FilledSurveyDto dataChangedFilledSurveyDto = getNotValidFilledSurveyDataChanged();

        mockMvc.perform(put(baseUrl + "/fill/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dataChangedFilledSurveyDto))
                        .param("interviewerId", "1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotValidDateException));

    }

    @Test
    void userFillSurveyNoSuccessEmptyAnswers() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        FilledSurveyDto surveyEmptyAnswers = getNotValidFilledSurveyEmptyAnswers();

        mockMvc.perform(put(baseUrl + "/fill/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(surveyEmptyAnswers))
                        .param("interviewerId", "1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AnswersIsEmptyException));

    }

    @Test
    void userFillSurveyNoSuccessFewAnswers() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        FilledSurveyDto fewAnswers = getNotValidAnswersFewAnswers();

        mockMvc.perform(put(baseUrl + "/fill/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fewAnswers))
                        .param("interviewerId", "1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isConflict())
                .andExpect(content().string("Number of answers in the Survey does not match"));

    }


    //Service methods

    private FilledSurveyDto getValidFilledSurvey() {
        return FilledSurveyDto.builder()
                .beginDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 12, 31))
                .description("Filled test survey")
                .sourceSurveyId(1L)
                .interviewerId(1L)
                .answers(getValidAnswers()).build();
    }

    private FilledSurveyDto getNotValidFilledSurveyDataChanged() {
        return FilledSurveyDto.builder()
                .beginDate(LocalDate.of(2022, 11, 1))
                .endDate(LocalDate.of(2022, 12, 31))
                .description("Filled test survey")
                .sourceSurveyId(1L)
                .interviewerId(1L)
                .answers(getValidAnswers()).build();
    }

    private FilledSurveyDto getNotValidFilledSurveyEmptyAnswers() {
        return FilledSurveyDto.builder()
                .beginDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 12, 31))
                .description("Filled test survey")
                .sourceSurveyId(1L)
                .interviewerId(1L)
                .build();
    }

    private List<Answer> getValidAnswers() {
        Answer answer1 = Answer.builder()
                .type(Type.SIMPLE_STRING_ANSWER)
                .question("How many hours in a day?")
                .answerOptions(List.of("Filled simple answer"))
                .build();

        Answer answer2 = Answer.builder()
                .type(Type.SINGLE_OPTION_ANSWER)
                .question("Capital of Japan?")
                .answerOptions(List.of("Filled single answer about Tokio"))
                .build();

        Answer answer3 = Answer.builder()
                .type(Type.MULTI_OPTION_ANSWER)
                .question("Summer sports?")
                .answerOptions(List.of("Filled first answer Summer Sports", "Filled second answer Summer Sports"))
                .build();
        return List.of(answer1, answer2, answer3);
    }

    private FilledSurveyDto getNotValidAnswersFewAnswers() {
        Answer answer1 = Answer.builder()
                .type(Type.SIMPLE_STRING_ANSWER)
                .question("How many hours in a day?")
                .answerOptions(List.of("Filled simple answer"))
                .build();

        List<Answer> answers = List.of(answer1);

        return FilledSurveyDto.builder()
                .beginDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 12, 31))
                .description("Filled test survey")
                .sourceSurveyId(1L)
                .interviewerId(1L)
                .answers(answers)
                .build();
    }

}