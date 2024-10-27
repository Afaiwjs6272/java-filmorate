package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.Comparator;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;

    public FilmService(@Qualifier("FilmRepository") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film getFilm(Long id) {
        return filmStorage.getFilm(id);
    }

    public Collection<Film> getFilms() {
        return filmStorage.findAll();
    }

    public Film addFilm(Film film) {
        return filmStorage.create(film);
    }

    public Film updateFilm(Film film) {
        checkFilmExist(filmStorage.getFilm(film.getId()));
        return filmStorage.update(film);
    }

    public void addLike(Long filmId, Long userId) {
        filmStorage.addLike(filmId, userId);
    }

    public void removeLike(Long filmId, Long userId) {
        filmStorage.removeLike(filmId, userId);
    }


    public Collection<Film> getTopFilms(int count) {
        return filmStorage
                .findAll()
                .stream()
                .sorted(Comparator.comparing(f -> f.getLikes().size(), Comparator.reverseOrder()))
                .limit(count)
                .toList();
    }

    public void checkFilmExist(Film film) {
        if (film == null) {
            log.error("Film not exists {}", film.getId());
            throw new NotFoundException("Film not found");
        }
    }
}