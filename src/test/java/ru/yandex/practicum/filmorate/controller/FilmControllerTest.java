package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTest {

    @Autowired
    private Validator validator;

    @Test
    void errorCreateDescriptionTooLongFilm() {
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("O".repeat(300));
        film.setReleaseDate(LocalDate.of(2022, 1, 1));
        film.setDuration(100);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("description"))
                .count());
    }

    @Test
    void createNullReleaseDateFilm() {
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("O".repeat(300));
        film.setReleaseDate(null);
        film.setDuration(100);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertEquals(0, violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("releaseDate"))
                .count());
    }

    @Test
    void errorCreateReleaseDateBeforeFilmsDateFilm() {
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("O".repeat(300));
        film.setReleaseDate(LocalDate.of(1864, 2, 3));
        film.setDuration(100);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("releaseDate"))
                .count());
    }
}
