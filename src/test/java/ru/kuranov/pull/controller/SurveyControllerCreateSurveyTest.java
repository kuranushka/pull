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
import ru.kuranov.pull.error.exception.NotValidDateException;
import ru.kuranov.pull.error.exception.NotValidSurveyDtoDateException;
import ru.kuranov.pull.model.dto.SurveyDto;
import ru.kuranov.pull.model.entity.question.Question;
import ru.kuranov.pull.model.type.Type;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "/test.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
class SurveyControllerCreateSurveyTest {

    String baseUrl = "/api/v1/surveys";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void userCreateSurveyNoSuccess() throws Exception {
        mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminCreateSurveySuccess() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        SurveyDto surveyDto = getValidSurveyDto();

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(surveyDto)))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(baseUrl + "/4"))
                .andExpect(status().isCreated());


        mockMvc.perform(get(baseUrl + "/4").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.beginDate").value("2022-01-01"))
                .andExpect(jsonPath("$.endDate").value("2022-12-31"))
                .andExpect(jsonPath("$.description").value("Three-question survey for tests"))
                .andExpect(jsonPath("$.isActive").value(is(true)))

                .andExpect(jsonPath("$.questions", hasSize(3)))

                .andExpect(jsonPath("$.questions[0].id").value(10))
                .andExpect(jsonPath("$.questions[0].question").value("First question?"))
                .andExpect(jsonPath("$.questions[0].type").value("SIMPLE_STRING_ANSWER"))
                .andExpect(jsonPath("$.questions[0].answerOptions").isEmpty())

                .andExpect(jsonPath("$.questions[1].id").value(11))
                .andExpect(jsonPath("$.questions[1].question").value("Second question?"))
                .andExpect(jsonPath("$.questions[1].type").value("SINGLE_OPTION_ANSWER"))
                .andExpect(jsonPath("$.questions[1].answerOptions[0]").value("First variant"))
                .andExpect(jsonPath("$.questions[1].answerOptions[1]").value("Second variant"))

                .andExpect(jsonPath("$.questions[2].id").value(12))
                .andExpect(jsonPath("$.questions[2].question").value("Third question?"))
                .andExpect(jsonPath("$.questions[2].type").value("MULTI_OPTION_ANSWER"))
                .andExpect(jsonPath("$.questions[2].answerOptions[0]").value("First variant"))
                .andExpect(jsonPath("$.questions[2].answerOptions[1]").value("Second variant"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminCreateSurveyWithoutQuestionsBlockNoSuccess() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        SurveyDto surveyDto = getInvalidSurveyDtoWithoutQuestionsBlock();

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(surveyDto)))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotValidSurveyDtoDateException));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminCreateSurveyInvalidDateNoSuccess() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        SurveyDto surveyDto = getValidSurveyDtoInvalidDateString();

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(surveyDto)))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotValidDateException));
    }

    private SurveyDto getValidSurveyDto() {
        Question question1 = Question.builder()
                .type(Type.SIMPLE_STRING_ANSWER)
                .question("First question?")
                .build();
        Question question2 = Question.builder()
                .type(Type.SINGLE_OPTION_ANSWER)
                .question("Second question?")
                .answerOptions(List.of("First variant", "Second variant"))
                .build();
        Question question3 = Question.builder()
                .type(Type.MULTI_OPTION_ANSWER)
                .question("Third question?")
                .answerOptions(List.of("First variant", "Second variant"))
                .build();

        List<Question> questions = List.of(question1, question2, question3);
        return SurveyDto.builder()
                .beginDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 12, 31))
                .description("Three-question survey for tests")
                .isActive(true)
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