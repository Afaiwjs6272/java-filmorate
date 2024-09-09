package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilmController.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateFilm() throws Exception {
        Film film = new Film();
        film.setName("TestFilm");
        film.setDuration(10);
        film.setReleaseDate(LocalDate.of(2024, 9, 9));
        film.setDescription("TestDescription");

        mockMvc.perform(post("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestFilm"))
                .andExpect(jsonPath("$.releaseDate").value("2024-09-09"))
                .andExpect(jsonPath("$.description").value("TestDescription"));
    }

    @Test
    public void testUpdateFilm() throws Exception {
        Film film = new Film();
        film.setId(1L);
        film.setName("InitialFilm");
        film.setDuration(10);
        film.setReleaseDate(LocalDate.of(2024, 9, 9));
        film.setDescription("InitialDescription");

        mockMvc.perform(post("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk());

        Film updatedFilm = new Film();
        updatedFilm.setId(1L);
        updatedFilm.setName("UpdatedFilm");
        updatedFilm.setReleaseDate(LocalDate.of(2024, 9, 10));
        updatedFilm.setDescription("UpdatedDescription");

        mockMvc.perform(put("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedFilm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedFilm"))
                .andExpect(jsonPath("$.releaseDate").value("2024-09-10"))
                .andExpect(jsonPath("$.description").value("UpdatedDescription"));
    }

    @Test
    public void testFindAllFilms() throws Exception {
        Film film1 = new Film();
        film1.setId(1L);
        film1.setDuration(10);
        film1.setName("Film1");
        film1.setReleaseDate(LocalDate.of(2024, 9, 9));
        film1.setDescription("Description1");

        Film film2 = new Film();
        film2.setId(2L);
        film2.setName("Film2");
        film2.setDuration(19);
        film2.setReleaseDate(LocalDate.of(2024, 9, 10));
        film2.setDescription("Description2");

        mockMvc.perform(post("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film1)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/films")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film2)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Film1"))
                .andExpect(jsonPath("$[1].name").value("Film2"));
    }
}
