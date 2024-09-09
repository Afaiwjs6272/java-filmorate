package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<Long, User> users;

    @BeforeEach
    public void setUp() {
        users = new HashMap<>();

    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testCreate() throws Exception {
        User user = new User();
        user.setName("JohnDoe");
        user.setBirthday(LocalDate.of(2000,12,11));
        user.setEmail("aasw@mail.ru");
        user.setLogin("assd");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("JohnDoe"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User();
        user.setName("JohnDoe");
        user.setId(1L);
        user.setBirthday(LocalDate.of(2000,12,11));
        user.setEmail("aasw@mail.ru");
        user.setLogin("assd");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        user.setName("JaneDoe");

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("JaneDoe"))
                .andDo(MockMvcResultHandlers.print());
    }
}
