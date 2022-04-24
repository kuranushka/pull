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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "/test.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
class SurveyControllerGetAllSurveyTest {

    String baseUrl = "/api/v1/surveys";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void userGetAllSurvey() throws Exception {
        mockMvc.perform(get(baseUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].beginDate").value("2022-01-01"))
                .andExpect(jsonPath("$[0].endDate").value("2022-12-31"))
                .andExpect(jsonPath("$[0].description").value("First active survey"))
                .andExpect(jsonPath("$[0].isActive").value("true"))

                .andExpect(jsonPath("$[0].questions[0].id").value(1))
                .andExpect(jsonPath("$[0].questions[0].question").value("How many hours in a day?"))
                .andExpect(jsonPath("$[0].questions[0].type").value("SIMPLE_STRING_ANSWER"))

                .andExpect(jsonPath("$[0].questions[1].id").value(2))
                .andExpect(jsonPath("$[0].questions[1].question").value("Capital of Japan?"))
                .andExpect(jsonPath("$[0].questions[1].type").value("SINGLE_OPTION_ANSWER"))

                .andExpect(jsonPath("$[0].questions[2].id").value(3))
                .andExpect(jsonPath("$[0].questions[2].question").value("Summer sports?"))
                .andExpect(jsonPath("$[0].questions[2].type").value("MULTI_OPTION_ANSWER"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].beginDate").value("2022-01-01"))
                .andExpect(jsonPath("$[1].endDate").value("2022-12-31"))
                .andExpect(jsonPath("$[1].description").value("Second active survey"))
                .andExpect(jsonPath("$[1].isActive").value("true"))

                .andExpect(jsonPath("$[1].questions[0].id").value(4))
                .andExpect(jsonPath("$[1].questions[0].question").value("How many time zones are there in Russia?"))
                .andExpect(jsonPath("$[1].questions[0].type").value("SIMPLE_STRING_ANSWER"))

                .andExpect(jsonPath("$[1].questions[1].id").value(5))
                .andExpect(jsonPath("$[1].questions[1].question").value("Capital of China?"))
                .andExpect(jsonPath("$[1].questions[1].type").value("SINGLE_OPTION_ANSWER"))

                .andExpect(jsonPath("$[1].questions[2].id").value(6))
                .andExpect(jsonPath("$[1].questions[2].question").value("Winter sports?"))
                .andExpect(jsonPath("$[1].questions[2].type").value("MULTI_OPTION_ANSWER"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminGetAllSurvey() throws Exception {
        mockMvc.perform(get(baseUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].beginDate").value("2022-01-01"))
                .andExpect(jsonPath("$[0].endDate").value("2022-12-31"))
                .andExpect(jsonPath("$[0].description").value("First active survey"))
                .andExpect(jsonPath("$[0].isActive").value("true"))

                .andExpect(jsonPath("$[0].questions[0].id").value(1))
                .andExpect(jsonPath("$[0].questions[0].question").value("How many hours in a day?"))
                .andExpect(jsonPath("$[0].questions[0].type").value("SIMPLE_STRING_ANSWER"))

                .andExpect(jsonPath("$[0].questions[1].id").value(2))
                .andExpect(jsonPath("$[0].questions[1].question").value("Capital of Japan?"))
                .andExpect(jsonPath("$[0].questions[1].type").value("SINGLE_OPTION_ANSWER"))

                .andExpect(jsonPath("$[0].questions[2].id").value(3))
                .andExpect(jsonPath("$[0].questions[2].question").value("Summer sports?"))
                .andExpect(jsonPath("$[0].questions[2].type").value("MULTI_OPTION_ANSWER"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].beginDate").value("2022-01-01"))
                .andExpect(jsonPath("$[1].endDate").value("2022-12-31"))
                .andExpect(jsonPath("$[1].description").value("Second active survey"))
                .andExpect(jsonPath("$[1].isActive").value("true"))

                .andExpect(jsonPath("$[1].questions[0].id").value(4))
                .andExpect(jsonPath("$[1].questions[0].question").value("How many time zones are there in Russia?"))
                .andExpect(jsonPath("$[1].questions[0].type").value("SIMPLE_STRING_ANSWER"))

                .andExpect(jsonPath("$[1].questions[1].id").value(5))
                .andExpect(jsonPath("$[1].questions[1].question").value("Capital of China?"))
                .andExpect(jsonPath("$[1].questions[1].type").value("SINGLE_OPTION_ANSWER"))

                .andExpect(jsonPath("$[1].questions[2].id").value(6))
                .andExpect(jsonPath("$[1].questions[2].question").value("Winter sports?"))
                .andExpect(jsonPath("$[1].questions[2].type").value("MULTI_OPTION_ANSWER"))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].beginDate").value("2022-01-01"))
                .andExpect(jsonPath("$[2].endDate").value("2022-12-31"))
                .andExpect(jsonPath("$[2].description").value("Third not active survey"))
                .andExpect(jsonPath("$[2].isActive").value("false"))

                .andExpect(jsonPath("$[2].questions[0].id").value(7))
                .andExpect(jsonPath("$[2].questions[0].question").value("How many fingers are on the hand?"))
                .andExpect(jsonPath("$[2].questions[0].type").value("SIMPLE_STRING_ANSWER"))

                .andExpect(jsonPath("$[2].questions[1].id").value(8))
                .andExpect(jsonPath("$[2].questions[1].question").value("Capital of Vietnam?"))
                .andExpect(jsonPath("$[2].questions[1].type").value("SINGLE_OPTION_ANSWER"))

                .andExpect(jsonPath("$[2].questions[2].id").value(9))
                .andExpect(jsonPath("$[2].questions[2].question").value("Summer months?"))
                .andExpect(jsonPath("$[2].questions[2].type").value("MULTI_OPTION_ANSWER"));
    }
}