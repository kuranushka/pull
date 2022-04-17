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
import ru.kuranov.pull.dto.FillPullDto;
import ru.kuranov.pull.entity.fill.FillItem;
import ru.kuranov.pull.entity.type.Type;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "/test.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
class PullControllerGetAllFillPullTest {

    String baseUrl = "/api/v1/pulls";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void userGetAllFillPullSuccess() throws Exception {

        mockMvc.perform(get(baseUrl + "/interviewer/1/fill-pull")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));


        mockMvc.perform(get(baseUrl + "/2/fill-pull")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNotFound());


        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        List<FillItem> fillItems = generateValidFillItems();

        mockMvc.perform(put(baseUrl + "/fill/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(FillPullDto.builder()
                                .beginDate(LocalDate.of(2022, 1, 1))
                                .endDate(LocalDate.of(2022, 12, 31))
                                .description("User-filled survey")
                                .sourcePullId(2L)
                                .fillItems(fillItems)
                                .build()))
                        .param("interviewerId", "2"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(baseUrl + "/interviewer/2/fill-pull")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    private List<FillItem> generateValidFillItems() {
        FillItem fillItem1 = FillItem.builder()
                .id(1L)
                .question("How many time zones are there in Russia?")
                .type(Type.SIMPLE_STRING)
                .answer(Map.of("11 time zones", false))
                .build();

        FillItem fillItem2 = FillItem.builder()
                .question("Capital of Tatarstan?")
                .type(Type.SINGLE_OPTION)
                .answer(Map.of("Kazan", true, "Naberezhnye Chelny", false, "Ufa", false))
                .build();

        FillItem fillItem3 = FillItem.builder()
                .id(1L)
                .question("Choose summer sports")
                .type(Type.MULTI_OPTION)
                .answer(Map.of("Badminton", true, "Volleyball", true, "Ski race", false, "Biathlon", false))
                .build();
        return List.of(fillItem1, fillItem2, fillItem3);
    }
}