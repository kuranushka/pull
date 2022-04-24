package ru.kuranov.pull.controller;

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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "/test.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
class SurveyControllerGetSurveyTest {

    String baseUrl = "/api/v1/surveys";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void userGetSurveySuccess() throws Exception {

        mockMvc.perform(get(baseUrl + "/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNotFound());


        mockMvc.perform(get(baseUrl + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.beginDate").value("2022-01-01"))
                .andExpect(jsonPath("$.endDate").value("2022-12-31"))
                .andExpect(jsonPath("$.description").value("First active survey"))
                .andExpect(jsonPath("$.isActive").value(is(true)))

                .andExpect(jsonPath("$.questions", hasSize(3)))

                .andExpect(jsonPath("$.questions[0].id").value(1))
                .andExpect(jsonPath("$.questions[0].question").value("How many hours in a day?"))
                .andExpect(jsonPath("$.questions[0].type").value("SIMPLE_STRING_ANSWER"))

                .andExpect(jsonPath("$.questions[1].id").value(2))
                .andExpect(jsonPath("$.questions[1].question").value("Capital of Japan?"))
                .andExpect(jsonPath("$.questions[1].type").value("SINGLE_OPTION_ANSWER"))
                .andExpect(jsonPath("$.questions[1].answerOptions[0]").value("Tokio"))
                .andExpect(jsonPath("$.questions[1].answerOptions[1]").value("Paris"))
                .andExpect(jsonPath("$.questions[1].answerOptions[2]").value("London"))

                .andExpect(jsonPath("$.questions[2].id").value(3))
                .andExpect(jsonPath("$.questions[2].question").value("Summer sports?"))
                .andExpect(jsonPath("$.questions[2].type").value("MULTI_OPTION_ANSWER"))
                .andExpect(jsonPath("$.questions[2].answerOptions[0]").value("Badminton"))
                .andExpect(jsonPath("$.questions[2].answerOptions[1]").value("Volleyball"))
                .andExpect(jsonPath("$.questions[2].answerOptions[2]").value("Ski race"))
                .andExpect(jsonPath("$.questions[2].answerOptions[3]").value("Biathlon"));


        mockMvc.perform(get(baseUrl + "/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.beginDate").value("2022-01-01"))
                .andExpect(jsonPath("$.endDate").value("2022-12-31"))
                .andExpect(jsonPath("$.description").value("Second active survey"))
                .andExpect(jsonPath("$.isActive").value(is(true)))

                .andExpect(jsonPath("$.questions", hasSize(3)))

                .andExpect(jsonPath("$.questions[0].id").value(4))
                .andExpect(jsonPath("$.questions[0].question").value("How many time zones are there in Russia?"))
                .andExpect(jsonPath("$.questions[0].type").value("SIMPLE_STRING_ANSWER"))

                .andExpect(jsonPath("$.questions[1].id").value(5))
                .andExpect(jsonPath("$.questions[1].question").value("Capital of China?"))
                .andExpect(jsonPath("$.questions[1].type").value("SINGLE_OPTION_ANSWER"))
                .andExpect(jsonPath("$.questions[1].answerOptions[0]").value("Moscow"))
                .andExpect(jsonPath("$.questions[1].answerOptions[1]").value("Beijing"))
                .andExpect(jsonPath("$.questions[1].answerOptions[2]").value("Seoul"))

                .andExpect(jsonPath("$.questions[2].id").value(6))
                .andExpect(jsonPath("$.questions[2].question").value("Winter sports?"))
                .andExpect(jsonPath("$.questions[2].type").value("MULTI_OPTION_ANSWER"))
                .andExpect(jsonPath("$.questions[2].answerOptions[0]").value("Badminton"))
                .andExpect(jsonPath("$.questions[2].answerOptions[1]").value("Volleyball"))
                .andExpect(jsonPath("$.questions[2].answerOptions[2]").value("Ski race"))
                .andExpect(jsonPath("$.questions[2].answerOptions[3]").value("Biathlon"));
    }

    @Test
    void userGetSurveyNoSuccess() throws Exception {

        mockMvc.perform(get(baseUrl + "/20").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminGetSurveySuccess() throws Exception {

        mockMvc.perform(get(baseUrl + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.beginDate").value("2022-01-01"))
                .andExpect(jsonPath("$.endDate").value("2022-12-31"))
                .andExpect(jsonPath("$.description").value("First active survey"))
                .andExpect(jsonPath("$.isActive").value(is(true)))

                .andExpect(jsonPath("$.questions", hasSize(3)))

                .andExpect(jsonPath("$.questions[0].id").value(1))
                .andExpect(jsonPath("$.questions[0].question").value("How many hours in a day?"))
                .andExpect(jsonPath("$.questions[0].type").value("SIMPLE_STRING_ANSWER"))

                .andExpect(jsonPath("$.questions[1].id").value(2))
                .andExpect(jsonPath("$.questions[1].question").value("Capital of Japan?"))
                .andExpect(jsonPath("$.questions[1].type").value("SINGLE_OPTION_ANSWER"))
                .andExpect(jsonPath("$.questions[1].answerOptions[0]").value("Tokio"))
                .andExpect(jsonPath("$.questions[1].answerOptions[1]").value("Paris"))
                .andExpect(jsonPath("$.questions[1].answerOptions[2]").value("London"))

                .andExpect(jsonPath("$.questions[2].id").value(3))
                .andExpect(jsonPath("$.questions[2].question").value("Summer sports?"))
                .andExpect(jsonPath("$.questions[2].type").value("MULTI_OPTION_ANSWER"))
                .andExpect(jsonPath("$.questions[2].answerOptions[0]").value("Badminton"))
                .andExpect(jsonPath("$.questions[2].answerOptions[1]").value("Volleyball"))
                .andExpect(jsonPath("$.questions[2].answerOptions[2]").value("Ski race"))
                .andExpect(jsonPath("$.questions[2].answerOptions[3]").value("Biathlon"));


        mockMvc.perform(get(baseUrl + "/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.beginDate").value("2022-01-01"))
                .andExpect(jsonPath("$.endDate").value("2022-12-31"))
                .andExpect(jsonPath("$.description").value("Second active survey"))
                .andExpect(jsonPath("$.isActive").value(is(true)))

                .andExpect(jsonPath("$.questions", hasSize(3)))

                .andExpect(jsonPath("$.questions[0].id").value(4))
                .andExpect(jsonPath("$.questions[0].question").value("How many time zones are there in Russia?"))
                .andExpect(jsonPath("$.questions[0].type").value("SIMPLE_STRING_ANSWER"))

                .andExpect(jsonPath("$.questions[1].id").value(5))
                .andExpect(jsonPath("$.questions[1].question").value("Capital of China?"))
                .andExpect(jsonPath("$.questions[1].type").value("SINGLE_OPTION_ANSWER"))
                .andExpect(jsonPath("$.questions[1].answerOptions[0]").value("Moscow"))
                .andExpect(jsonPath("$.questions[1].answerOptions[1]").value("Beijing"))
                .andExpect(jsonPath("$.questions[1].answerOptions[2]").value("Seoul"))

                .andExpect(jsonPath("$.questions[2].id").value(6))
                .andExpect(jsonPath("$.questions[2].question").value("Winter sports?"))
                .andExpect(jsonPath("$.questions[2].type").value("MULTI_OPTION_ANSWER"))
                .andExpect(jsonPath("$.questions[2].answerOptions[0]").value("Badminton"))
                .andExpect(jsonPath("$.questions[2].answerOptions[1]").value("Volleyball"))
                .andExpect(jsonPath("$.questions[2].answerOptions[2]").value("Ski race"))
                .andExpect(jsonPath("$.questions[2].answerOptions[3]").value("Biathlon"));


        mockMvc.perform(get(baseUrl + "/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.beginDate").value("2022-01-01"))
                .andExpect(jsonPath("$.endDate").value("2022-12-31"))
                .andExpect(jsonPath("$.description").value("Third not active survey"))
                .andExpect(jsonPath("$.isActive").value(is(false)))

                .andExpect(jsonPath("$.questions", hasSize(3)))

                .andExpect(jsonPath("$.questions[0].id").value(7))
                .andExpect(jsonPath("$.questions[0].question").value("How many fingers are on the hand?"))
                .andExpect(jsonPath("$.questions[0].type").value("SIMPLE_STRING_ANSWER"))

                .andExpect(jsonPath("$.questions[1].id").value(8))
                .andExpect(jsonPath("$.questions[1].question").value("Capital of Vietnam?"))
                .andExpect(jsonPath("$.questions[1].type").value("SINGLE_OPTION_ANSWER"))
                .andExpect(jsonPath("$.questions[1].answerOptions[0]").value("Oslo"))
                .andExpect(jsonPath("$.questions[1].answerOptions[1]").value("Stockholm"))
                .andExpect(jsonPath("$.questions[1].answerOptions[2]").value("Hanoi"))

                .andExpect(jsonPath("$.questions[2].id").value(9))
                .andExpect(jsonPath("$.questions[2].question").value("Summer months?"))
                .andExpect(jsonPath("$.questions[2].type").value("MULTI_OPTION_ANSWER"))
                .andExpect(jsonPath("$.questions[2].answerOptions[0]").value("May"))
                .andExpect(jsonPath("$.questions[2].answerOptions[1]").value("December"))
                .andExpect(jsonPath("$.questions[2].answerOptions[2]").value("August"))
                .andExpect(jsonPath("$.questions[2].answerOptions[3]").value("July"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminGetSurveyNoSuccess() throws Exception {

        mockMvc.perform(get(baseUrl + "/20").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNotFound());
    }
}