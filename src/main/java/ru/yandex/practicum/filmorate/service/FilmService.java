package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film getFIlm(Long id) {
        return filmStorage.getFilm(id);
    }

    public Film deleteFilm(Long id) {
        return filmStorage.deleteFilm(id);
    }

    public Film createFilm(Film film) {
        return filmStorage.create(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.update(film);
    }

    public Collection<Film> findAllFilms() {
        return filmStorage.findAll();
    }

    public Film likeFilm(Long filmId, Long userId) {
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId);
        checkValidFilm(film);
        checkValidUser(user);
        film.getLikes().add(userId);
        filmStorage.update(film);
        log.info("User {} liked Film {}", userId, filmId);
        return film;
    }

    public Film removeLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId);
        checkValidFilm(film);
        checkValidUser(user);
        film.getLikes().remove(userId);
        filmStorage.update(film);
        log.info("User {} remove like on Film {}", userId, filmId);
        return film;
    }

    public Collection<Film> getListOfTenMostLikedFilms(int count) {
        return filmStorage.findAll().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count).
                toList();
    }

    private void checkValidFilm(Film film) {
        if (film == null) {
            throw new NotFoundException("Фильм не может быть null");
        }
    }

    private void checkValidUser(User user) {
        if (user == null) {
            throw new NotFoundException("Юзер не может быть null");
        }
    }
}
