package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> findAll() {
        return filmService.findAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id) {
        return filmService.getFIlm(id);
    }

    @GetMapping("/popular")
    public Collection<Film> getTopPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getListOfTenMostLikedFilms(count);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        return filmService.updateFilm(newFilm);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film userLikedFilm(@PathVariable Long userId,@PathVariable Long id) {
        return filmService.likeFilm(id,userId);
    }

    @DeleteMapping("/{id}")
    public Film deleteFilm(@PathVariable Long id) {
        return filmService.deleteFilm(id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film userRemovingLikeFromFilm(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.removeLike(id, userId);
    }
}
