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
import ru.kuranov.pull.dto.PullDto;
import ru.kuranov.pull.entity.main.Item;
import ru.kuranov.pull.entity.type.Type;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "/test.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
class PullControllerCreatePullTest {

    String baseUrl = "/api/v1/pulls";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void userCreatePullNoSuccess() throws Exception {
        mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminCreatePullSuccess() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Item item1 = Item.builder()
                .question("Where the bear hibernates?")
                .type(Type.SIMPLE_STRING)
                .build();

        Item item2 = Item.builder()
                .question("What's in a camel's humps?")
                .type(Type.SINGLE_OPTION)
                .build();

        Item item3 = Item.builder()
                .question("Choose relatives of a domestic cat?")
                .type(Type.MULTI_OPTION)
                .build();

        List<Item> items = List.of(item1, item2, item3);

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PullDto.builder()
                                .beginDate(LocalDate.of(2022, 1, 1))
                                .endDate(LocalDate.of(2022, 12, 31))
                                .description("Three-question survey for tests")
                                .isActive(true)
                                .items(items)
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(baseUrl + "/3"))
                .andExpect(status().isCreated());


        mockMvc.perform(get(baseUrl + "/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.beginDate").value("2022.01.01"))
                .andExpect(jsonPath("$.endDate").value("2022.12.31"))
                .andExpect(jsonPath("$.description").value("Three-question survey for tests"))
                .andExpect(jsonPath("$.isActive").value(is(true)))

                .andExpect(jsonPath("$.items", hasSize(3)))

                .andExpect(jsonPath("$.items[0].id").value(7))
                .andExpect(jsonPath("$.items[0].question").value("Where the bear hibernates?"))
                .andExpect(jsonPath("$.items[0].type").value("SIMPLE_STRING"))
                .andExpect(jsonPath("$.items[0].answer").isEmpty())

                .andExpect(jsonPath("$.items[1].id").value(8))
                .andExpect(jsonPath("$.items[1].question").value("What's in a camel's humps?"))
                .andExpect(jsonPath("$.items[1].type").value("SINGLE_OPTION"))
                .andExpect(jsonPath("$.items[1].answer").isEmpty())

                .andExpect(jsonPath("$.items[2].id").value(9))
                .andExpect(jsonPath("$.items[2].question").value("Choose relatives of a domestic cat?"))
                .andExpect(jsonPath("$.items[2].type").value("MULTI_OPTION"))
                .andExpect(jsonPath("$.items[2].answer").isEmpty());

    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminCreatePullNoSuccess() throws Exception {

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PullDto.builder()
                                .beginDate(LocalDate.of(2022, 1, 1))
                                .endDate(LocalDate.of(2022, 12, 31))
                                .description("Three-question survey for tests")
                                .isActive(true)
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isBadRequest());
    }
}