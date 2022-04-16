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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "/test.sql", config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
class PullControllerGetPullTest {

    String baseUrl = "/api/v1/pulls";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void userGetPullSuccess() throws Exception {

        mockMvc.perform(get(baseUrl + "/1").contentType(MediaType.APPLICATION_JSON))
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
                .andExpect(jsonPath("$.description").value("Активный опрос"))
                .andExpect(jsonPath("$.isActive").value(is(true)))

                .andExpect(jsonPath("$.items", hasSize(3)))

                .andExpect(jsonPath("$.items[0].id").value(4))
                .andExpect(jsonPath("$.items[0].question").value("Сколько часовых поясов В России?"))
                .andExpect(jsonPath("$.items[0].type").value("SIMPLE_STRING"))
                .andExpect(jsonPath("$.items[0].answer", hasEntry(is("11 часовых поясов"), is(false))))

                .andExpect(jsonPath("$.items[1].id").value(5))
                .andExpect(jsonPath("$.items[1].question").value("Столица Татарстана?"))
                .andExpect(jsonPath("$.items[1].type").value("SINGLE_OPTION"))
                .andExpect(jsonPath("$.items[1].answer", hasEntry(is("Набережные Челны"), is(false))))
                .andExpect(jsonPath("$.items[1].answer", hasEntry(is("Казань"), is(true))))
                .andExpect(jsonPath("$.items[1].answer", hasEntry(is("Уфа"), is(false))))

                .andExpect(jsonPath("$.items[2].id").value(6))
                .andExpect(jsonPath("$.items[2].question").value("Выберите летние виды спорта"))
                .andExpect(jsonPath("$.items[2].type").value("MULTI_OPTION"))
                .andExpect(jsonPath("$.items[2].answer", hasEntry(is("Лыжные гонки"), is(false))))
                .andExpect(jsonPath("$.items[2].answer", hasEntry(is("Воллейбол"), is(true))))
                .andExpect(jsonPath("$.items[2].answer", hasEntry(is("Биатлон"), is(false))))
                .andExpect(jsonPath("$.items[2].answer", hasEntry(is("Бадминтон"), is(true))));
    }

    @Test
    void userGetPullNoSuccess() throws Exception {

        mockMvc.perform(get(baseUrl + "/20").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isConflict());
    }


    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminGetPullSuccess() throws Exception {

        mockMvc.perform(get(baseUrl + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.beginDate").value("01.01.2022"))
                .andExpect(jsonPath("$.endDate").value("31.12.2022"))
                .andExpect(jsonPath("$.description").value("Не активный опрос"))
                .andExpect(jsonPath("$.isActive").value(is(false)))

                .andExpect(jsonPath("$.items", hasSize(3)))

                .andExpect(jsonPath("$.items[0].id").value(1))
                .andExpect(jsonPath("$.items[0].question").value("Сколько часов в сутках?"))
                .andExpect(jsonPath("$.items[0].type").value("SIMPLE_STRING"))
                .andExpect(jsonPath("$.items[0].answer").isEmpty())

                .andExpect(jsonPath("$.items[1].id").value(2))
                .andExpect(jsonPath("$.items[1].question").value("Столица Татарстана?"))
                .andExpect(jsonPath("$.items[1].type").value("SINGLE_OPTION"))
                .andExpect(jsonPath("$.items[1].answer").isEmpty())

                .andExpect(jsonPath("$.items[2].id").value(3))
                .andExpect(jsonPath("$.items[2].question").value("Выберите зимние виды спорта?"))
                .andExpect(jsonPath("$.items[2].type").value("MULTI_OPTION"))
                .andExpect(jsonPath("$.items[2].answer").isEmpty());


        mockMvc.perform(get(baseUrl + "/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.beginDate").value("01.01.2022"))
                .andExpect(jsonPath("$.endDate").value("31.12.2022"))
                .andExpect(jsonPath("$.description").value("Активный опрос"))
                .andExpect(jsonPath("$.isActive").value(is(true)))

                .andExpect(jsonPath("$.items", hasSize(3)))
                .andExpect(jsonPath("$.items[0].id").value(4))

                .andExpect(jsonPath("$.items[0].id").value(4))
                .andExpect(jsonPath("$.items[0].question").value("Сколько часовых поясов В России?"))
                .andExpect(jsonPath("$.items[0].type").value("SIMPLE_STRING"))
                .andExpect(jsonPath("$.items[0].answer", hasEntry(is("11 часовых поясов"), is(false))))

                .andExpect(jsonPath("$.items[1].id").value(5))
                .andExpect(jsonPath("$.items[1].question").value("Столица Татарстана?"))
                .andExpect(jsonPath("$.items[1].type").value("SINGLE_OPTION"))
                .andExpect(jsonPath("$.items[1].answer", hasEntry(is("Набережные Челны"), is(false))))
                .andExpect(jsonPath("$.items[1].answer", hasEntry(is("Казань"), is(true))))
                .andExpect(jsonPath("$.items[1].answer", hasEntry(is("Уфа"), is(false))))

                .andExpect(jsonPath("$.items[2].id").value(6))
                .andExpect(jsonPath("$.items[2].question").value("Выберите летние виды спорта"))
                .andExpect(jsonPath("$.items[2].type").value("MULTI_OPTION"))
                .andExpect(jsonPath("$.items[2].answer", hasEntry(is("Лыжные гонки"), is(false))))
                .andExpect(jsonPath("$.items[2].answer", hasEntry(is("Воллейбол"), is(true))))
                .andExpect(jsonPath("$.items[2].answer", hasEntry(is("Биатлон"), is(false))))
                .andExpect(jsonPath("$.items[2].answer", hasEntry(is("Бадминтон"), is(true))));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN", password = "admin")
    void adminGetPullNoSuccess() throws Exception {

        mockMvc.perform(get(baseUrl + "/20").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isConflict());
    }
}