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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "/test.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
class PullControllerFillPullTest {

    String baseUrl = "/api/v1/pulls/fill";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void userFillPullSuccess() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        List<FillItem> fillItems = generateValidFillItems();

        mockMvc.perform(put(baseUrl + "/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(FillPullDto.builder()
                                .beginDate(LocalDate.of(2022, 1, 1))
                                .endDate(LocalDate.of(2022, 12, 31))
                                .description("User-filled survey")
                                .sourcePullId(2L)
                                .fillItems(fillItems)
                                .build()))
                        .param("interviewerId", "1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNoContent());
    }

    @Test
    void userFillPullNoSuccess() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        List<FillItem> validFillItems = generateValidFillItems();

        mockMvc.perform(put(baseUrl + "/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(FillPullDto.builder()
                                .beginDate(LocalDate.of(2022, 1, 1))
                                .endDate(LocalDate.of(2022, 12, 31))
                                .description("User-filled survey")
                                .sourcePullId(2L)
                                .fillItems(validFillItems)
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isBadRequest());

        mockMvc.perform(put(baseUrl + "/20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(FillPullDto.builder()
                                .beginDate(LocalDate.of(2022, 1, 1))
                                .endDate(LocalDate.of(2022, 12, 31))
                                .description("User-filled survey")
                                .sourcePullId(2L)
                                .fillItems(validFillItems)
                                .build()))
                        .param("interviewerId", "1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isConflict());

        mockMvc.perform(put(baseUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(FillPullDto.builder()
                                .beginDate(LocalDate.of(2022, 1, 1))
                                .endDate(LocalDate.of(2022, 12, 31))
                                .description("User-filled survey")
                                .sourcePullId(2L)
                                .fillItems(validFillItems)
                                .build()))
                        .param("interviewerId", "1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNotFound());

        List<FillItem> notValidFillItemsToManyItems = generateNotValidFillItemsToManyItems();

        mockMvc.perform(put(baseUrl + "/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(FillPullDto.builder()
                                .beginDate(LocalDate.of(2022, 1, 1))
                                .endDate(LocalDate.of(2022, 12, 31))
                                .description("User-filled survey")
                                .sourcePullId(2L)
                                .fillItems(notValidFillItemsToManyItems)
                                .build()))
                        .param("interviewerId", "1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isBadRequest());

        List<FillItem> notValidFillItemsAnotherTypeItems = generateNotValidFillItemsAnotherTypeItems();

        mockMvc.perform(put(baseUrl + "/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(FillPullDto.builder()
                                .beginDate(LocalDate.of(2022, 1, 1))
                                .endDate(LocalDate.of(2022, 12, 31))
                                .description("User-filled survey")
                                .sourcePullId(2L)
                                .fillItems(notValidFillItemsAnotherTypeItems)
                                .build()))
                        .param("interviewerId", "1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNoContent());
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


    private List<FillItem> generateNotValidFillItemsAnotherTypeItems() {
        FillItem fillItem1 = FillItem.builder()
                .id(1L)
                .question("How many time zones are there in Russia?")
                .type(Type.SIMPLE_STRING)
                .answer(Map.of("11 time zones", false))
                .build();

        FillItem fillItem2 = FillItem.builder()
                .question("Capital of Tatarstan?")
                .type(Type.MULTI_OPTION)
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


    private List<FillItem> generateNotValidFillItemsToManyItems() {
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

        FillItem fillItem4 = FillItem.builder()
                .id(1L)
                .question("Choose summer sports")
                .type(Type.MULTI_OPTION)
                .answer(Map.of("Badminton", true, "Volleyball", true, "Ski race", false, "Biathlon", false))
                .build();

        return List.of(fillItem1, fillItem2, fillItem3, fillItem4);
    }
}