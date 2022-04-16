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
import org.springframework.test.web.servlet.MockMvc;
import ru.kuranov.pull.dto.PullDto;
import ru.kuranov.pull.entity.main.Item;
import ru.kuranov.pull.entity.type.Type;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "/test.sql")
class PullControllerUpdatePullTest {

    String baseUrl = "/api/v1/pulls";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void userUpdatePullNoSuccess() throws Exception {
        mockMvc.perform(put(baseUrl + "/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminUpdatePullSuccess() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Item item1 = Item.builder()
                .question("Где зимует медведь?")
                .type(Type.SIMPLE_STRING)
                .build();

        Item item2 = Item.builder()
                .question("Что в горбах у верблюда?")
                .type(Type.SINGLE_OPTION)
                .build();

        Item item3 = Item.builder()
                .question("Выберите родственников домашней кошки?")
                .type(Type.MULTI_OPTION)
                .build();

        List<Item> items = List.of(item1, item2, item3);

        mockMvc.perform(put(baseUrl + "/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PullDto.builder()
                                .beginDate(LocalDate.of(2022, 1, 1))
                                .endDate(LocalDate.of(2022, 12, 31))
                                .description("Обновленный Опрос из трёх вопросов для тестов")
                                .isActive(true)
                                .items(items)
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNoContent());


        mockMvc.perform(get(baseUrl + "/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.beginDate").value("01.01.2022"))
                .andExpect(jsonPath("$.endDate").value("31.12.2022"))
                .andExpect(jsonPath("$.description").value("Обновленный Опрос из трёх вопросов для тестов"))
                .andExpect(jsonPath("$.isActive").value(is(true)))

                .andExpect(jsonPath("$.items", hasSize(3)))

                .andExpect(jsonPath("$.items[0].id").value(7))
                .andExpect(jsonPath("$.items[0].question").value("Где зимует медведь?"))
                .andExpect(jsonPath("$.items[0].type").value("SIMPLE_STRING"))
                .andExpect(jsonPath("$.items[0].answer").isEmpty())

                .andExpect(jsonPath("$.items[1].id").value(8))
                .andExpect(jsonPath("$.items[1].question").value("Что в горбах у верблюда?"))
                .andExpect(jsonPath("$.items[1].type").value("SINGLE_OPTION"))
                .andExpect(jsonPath("$.items[1].answer").isEmpty())

                .andExpect(jsonPath("$.items[2].id").value(9))
                .andExpect(jsonPath("$.items[2].question").value("Выберите родственников домашней кошки?"))
                .andExpect(jsonPath("$.items[2].type").value("MULTI_OPTION"))
                .andExpect(jsonPath("$.items[2].answer").isEmpty());

    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminUpdatePullNoSuccess() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Item item1 = Item.builder()
                .question("Где зимует медведь?")
                .type(Type.SIMPLE_STRING)
                .build();

        Item item2 = Item.builder()
                .question("Что в горбах у верблюда?")
                .type(Type.SINGLE_OPTION)
                .build();

        Item item3 = Item.builder()
                .question("Выберите родственников домашней кошки?")
                .type(Type.MULTI_OPTION)
                .build();

        List<Item> items = List.of(item1, item2, item3);

        mockMvc.perform(put(baseUrl + "/20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PullDto.builder()
                                .beginDate(LocalDate.of(2022, 1, 1))
                                .endDate(LocalDate.of(2022, 12, 31))
                                .description("Опрос из трёх вопросов для тестов")
                                .isActive(true)
                                .items(items)
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isConflict());

        mockMvc.perform(put(baseUrl + "/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PullDto.builder()
                                .beginDate(LocalDate.of(2022, 1, 1))
                                .endDate(LocalDate.of(2022, 12, 31))
                                .description("Опрос из трёх вопросов для тестов")
                                .isActive(true)
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isBadRequest());
    }
}