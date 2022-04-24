package ru.kuranov.pull.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "/test.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
class SurveyControllerGetAllFilledSurveyTest {

    String baseUrl = "/api/v1/surveys";
    String interviewerId = "1";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void userGetAllFillSurveySuccess() throws Exception {

        mockMvc.perform(get(baseUrl + "/interviewer/" + interviewerId + "/fill-survey").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))

                .andExpect(jsonPath("$[0].beginDate").value("2022-01-01"))
                .andExpect(jsonPath("$[0].endDate").value("2022-12-31"))
                .andExpect(jsonPath("$[0].description").value("First filled survey"))
                .andExpect(jsonPath("$[0].sourceSurveyId").value("1"))
                .andExpect(jsonPath("$[0].interviewerId").value("1"))


                .andExpect(jsonPath("$[0].answers[0].id").value("1"))
                .andExpect(jsonPath("$[0].answers[0].type").value("SIMPLE_STRING_ANSWER"))
                .andExpect(jsonPath("$[0].answers[0].question").value("How many hours in a day?"))
                .andExpect(jsonPath("$[0].answers[0].answerOptions[0]").value("24 hours"))

                .andExpect(jsonPath("$[0].answers[1].id").value("2"))
                .andExpect(jsonPath("$[0].answers[1].type").value("SINGLE_OPTION_ANSWER"))
                .andExpect(jsonPath("$[0].answers[1].question").value("Capital of Japan?"))
                .andExpect(jsonPath("$[0].answers[1].answerOptions[0]").value("Tokio"))

                .andExpect(jsonPath("$[0].answers[2].id").value("3"))
                .andExpect(jsonPath("$[0].answers[2].type").value("MULTI_OPTION_ANSWER"))
                .andExpect(jsonPath("$[0].answers[2].question").value("Summer sports?"))
                .andExpect(jsonPath("$[0].answers[2].answerOptions[0]").value("Badminton"))
                .andExpect(jsonPath("$[0].answers[2].answerOptions[1]").value("Volleyball"))


                .andExpect(jsonPath("$[1].answers[0].id").value("4"))
                .andExpect(jsonPath("$[1].answers[0].type").value("SIMPLE_STRING_ANSWER"))
                .andExpect(jsonPath("$[1].answers[0].question").value("How many time zones are there in Russia?"))
                .andExpect(jsonPath("$[1].answers[0].answerOptions[0]").value("11 time zones"))

                .andExpect(jsonPath("$[1].answers[1].id").value("5"))
                .andExpect(jsonPath("$[1].answers[1].type").value("SINGLE_OPTION_ANSWER"))
                .andExpect(jsonPath("$[1].answers[1].question").value("Capital of China?"))
                .andExpect(jsonPath("$[1].answers[1].answerOptions[0]").value("Beijing"))

                .andExpect(jsonPath("$[1].answers[2].id").value("6"))
                .andExpect(jsonPath("$[1].answers[2].type").value("MULTI_OPTION_ANSWER"))
                .andExpect(jsonPath("$[1].answers[2].question").value("Winter sports?"))
                .andExpect(jsonPath("$[1].answers[2].answerOptions[0]").value("Ski race"))
                .andExpect(jsonPath("$[1].answers[2].answerOptions[1]").value("Biathlon"))


                .andExpect(jsonPath("$[2].answers[0].id").value("7"))
                .andExpect(jsonPath("$[2].answers[0].type").value("SIMPLE_STRING_ANSWER"))
                .andExpect(jsonPath("$[2].answers[0].question").value("How many fingers are on the hand?"))
                .andExpect(jsonPath("$[2].answers[0].answerOptions[0]").value("5 fingers"))

                .andExpect(jsonPath("$[2].answers[1].id").value("8"))
                .andExpect(jsonPath("$[2].answers[1].type").value("SINGLE_OPTION_ANSWER"))
                .andExpect(jsonPath("$[2].answers[1].question").value("Capital of Vietnam?"))
                .andExpect(jsonPath("$[2].answers[1].answerOptions[0]").value("Hanoi"))

                .andExpect(jsonPath("$[2].answers[2].id").value("9"))
                .andExpect(jsonPath("$[2].answers[2].type").value("MULTI_OPTION_ANSWER"))
                .andExpect(jsonPath("$[2].answers[2].question").value("Summer months?"))
                .andExpect(jsonPath("$[2].answers[2].answerOptions[0]").value("December"));
    }

    @Test
    void userGetAllFillSurveyNoSuccess() throws Exception {

        mockMvc.perform(get(baseUrl + "/interviewer/" + "2" + "/fill-survey").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNotFound());
    }
}