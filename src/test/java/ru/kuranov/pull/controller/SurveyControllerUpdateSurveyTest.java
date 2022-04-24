package ru.kuranov.pull.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import ru.kuranov.pull.error.exception.AnswerOptionsIsEmptyException;
import ru.kuranov.pull.error.exception.NoQuestionException;
import ru.kuranov.pull.error.exception.NotValidDateException;
import ru.kuranov.pull.error.exception.QuestionIsEmptyException;
import ru.kuranov.pull.model.dto.SurveyDto;
import ru.kuranov.pull.model.entity.question.Question;
import ru.kuranov.pull.model.type.Type;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "/test.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
class SurveyControllerUpdateSurveyTest {

    String baseUrl = "/api/v1/surveys";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void userUpdateSurveyNoSuccess() throws Exception {
        mockMvc.perform(put(baseUrl + "/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminUpdateSurveySuccess() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        SurveyDto surveyDto = getValidSurveyDto();

        mockMvc.perform(put(baseUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(surveyDto)))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNoContent());


        mockMvc.perform(get(baseUrl + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.beginDate").value("2022-01-01"))
                .andExpect(jsonPath("$.endDate").value("2022-05-30"))
                .andExpect(jsonPath("$.description").value("Updated Three-question survey for tests"))
                .andExpect(jsonPath("$.isActive").value(is(false)))

                .andExpect(jsonPath("$.questions", hasSize(4)))

                .andExpect(jsonPath("$.questions[0].id").value(10))
                .andExpect(jsonPath("$.questions[0].question").value("Updated First question?"))
                .andExpect(jsonPath("$.questions[0].type").value("SIMPLE_STRING_ANSWER"))
                .andExpect(jsonPath("$.questions[0].answerOptions").isEmpty())

                .andExpect(jsonPath("$.questions[1].id").value(11))
                .andExpect(jsonPath("$.questions[1].question").value("Updated Second question?"))
                .andExpect(jsonPath("$.questions[1].type").value("MULTI_OPTION_ANSWER"))
                .andExpect(jsonPath("$.questions[1].answerOptions[0]").value("Updated First variant"))
                .andExpect(jsonPath("$.questions[1].answerOptions[1]").value("Updated Second variant"))

                .andExpect(jsonPath("$.questions[2].id").value(12))
                .andExpect(jsonPath("$.questions[2].question").value("Updated Third question?"))
                .andExpect(jsonPath("$.questions[2].type").value("SINGLE_OPTION_ANSWER"))
                .andExpect(jsonPath("$.questions[2].answerOptions[0]").value("Updated First variant"))
                .andExpect(jsonPath("$.questions[2].answerOptions[1]").value("Updated Second variant"))

                .andExpect(jsonPath("$.questions[3].id").value(13))
                .andExpect(jsonPath("$.questions[3].question").value("New Fourth question?"))
                .andExpect(jsonPath("$.questions[3].type").value("SIMPLE_STRING_ANSWER"))
                .andExpect(jsonPath("$.questions[3].answerOptions").isEmpty());

    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminUpdateSurveyNoSuccess() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        SurveyDto surveyDto = getValidSurveyDto();

        mockMvc.perform(put(baseUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(surveyDto)))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNoContent());


        mockMvc.perform(put(baseUrl + "/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SurveyDto.builder()
                                .beginDate(LocalDate.of(2022, 1, 1))
                                .endDate(LocalDate.of(2022, 12, 31))
                                .description("Three-question survey for tests")
                                .isActive(true)
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminUpdateSurveyNoSuccessNotValidDate() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        SurveyDto notValidDateSurveyDto = getNotValidDateSurveyDto();

        mockMvc.perform(put(baseUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notValidDateSurveyDto)))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotValidDateException));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminUpdateSurveyNoSuccessEmptyQuestions() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        SurveyDto notValidEmptyQuestionsSurveyDto = getNotValidEmptyQuestionsSurveyDto();

        mockMvc.perform(put(baseUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notValidEmptyQuestionsSurveyDto)))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof QuestionIsEmptyException));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminUpdateSurveyNoSuccessWithOutQuestion() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        SurveyDto notValidWithOutQuestionSurveyDto = getNotValidWithOutQuestionSurveyDto();

        mockMvc.perform(put(baseUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notValidWithOutQuestionSurveyDto)))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoQuestionException));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminUpdateSurveyNoSuccessNoAnswerOptionsInSingleOptionAnswer() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        SurveyDto answerSurveyDto = getNotValidNoAnswerOptionsInSingleOptionAnswerSurveyDto();

        mockMvc.perform(put(baseUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerSurveyDto)))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AnswerOptionsIsEmptyException));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminUpdateSurveyNoSuccessNoAnswerOptionsInMultiOptionAnswer() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        SurveyDto answerSurveyDto = getNotValidNoAnswerOptionsInMultiOptionAnswerSurveyDto();

        mockMvc.perform(put(baseUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerSurveyDto)))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AnswerOptionsIsEmptyException));
    }


    private SurveyDto getValidSurveyDto() {
        Question question1 = Question.builder()
                .type(Type.SIMPLE_STRING_ANSWER)
                .question("Updated First question?")
                .build();
        Question question2 = Question.builder()
                .type(Type.MULTI_OPTION_ANSWER)
                .question("Updated Second question?")
                .answerOptions(List.of("Updated First variant", "Updated Second variant"))
                .build();
        Question question3 = Question.builder()
                .type(Type.SINGLE_OPTION_ANSWER)
                .question("Updated Third question?")
                .answerOptions(List.of("Updated First variant", "Updated Second variant"))
                .build();
        Question question4 = Question.builder()
                .type(Type.SIMPLE_STRING_ANSWER)
                .question("New Fourth question?")
                .build();

        List<Question> questions = List.of(question1, question2, question3, question4);
        return SurveyDto.builder()
                .beginDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 5, 30))
                .description("Updated Three-question survey for tests")
                .isActive(false)
                .questions(questions)
                .build();
    }

    private SurveyDto getNotValidDateSurveyDto() {
        Question question1 = Question.builder()
                .type(Type.SIMPLE_STRING_ANSWER)
                .question("Updated First question?")
                .build();

        List<Question> questions = List.of(question1);
        return SurveyDto.builder()
                .beginDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2021, 5, 30))
                .description("Updated Three-question survey for tests")
                .isActive(false)
                .questions(questions)
                .build();
    }

    private SurveyDto getNotValidEmptyQuestionsSurveyDto() {
        List<Question> questions = new ArrayList<>();
        return SurveyDto.builder()
                .beginDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 5, 30))
                .description("Updated Three-question survey for tests")
                .isActive(false)
                .questions(questions)
                .build();
    }

    private SurveyDto getNotValidWithOutQuestionSurveyDto() {
        Question question1 = Question.builder()
                .type(Type.SIMPLE_STRING_ANSWER)
                .build();

        List<Question> questions = List.of(question1);
        return SurveyDto.builder()
                .beginDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 5, 30))
                .description("Updated Three-question survey for tests")
                .isActive(false)
                .questions(questions)
                .build();
    }

    private SurveyDto getNotValidNoAnswerOptionsInSingleOptionAnswerSurveyDto() {

        Question question = Question.builder()
                .type(Type.SINGLE_OPTION_ANSWER)
                .question("Updated Third question?")
                .build();


        List<Question> questions = List.of(question);
        return SurveyDto.builder()
                .beginDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 5, 30))
                .description("Updated Three-question survey for tests")
                .isActive(false)
                .questions(questions)
                .build();
    }

    private SurveyDto getNotValidNoAnswerOptionsInMultiOptionAnswerSurveyDto() {

        Question question = Question.builder()
                .type(Type.MULTI_OPTION_ANSWER)
                .question("Updated Third question?")
                .build();


        List<Question> questions = List.of(question);
        return SurveyDto.builder()
                .beginDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 5, 30))
                .description("Updated Three-question survey for tests")
                .isActive(false)
                .questions(questions)
                .build();
    }


    private SurveyDto getInvalidSurveyDtoWithoutQuestionsBlock() {
        return SurveyDto.builder()
                .beginDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 12, 31))
                .description("Three-question survey for tests")
                .isActive(true)
                .build();
    }

    private SurveyDto getValidSurveyDtoInvalidDateString() {
        Question question1 = Question.builder()
                .type(Type.SIMPLE_STRING_ANSWER)
                .question("First question?")
                .build();

        List<Question> questions = List.of(question1);
        return SurveyDto.builder()
                .beginDate(LocalDate.of(2022, 10, 10))
                .endDate(LocalDate.of(2022, 10, 6))
                .description("Three-question survey for tests")
                .isActive(true)
                .questions(questions)
                .build();
    }
}