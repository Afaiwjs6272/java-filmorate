package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("memoryFilm")
public class InMemoryFilmStorage implements FilmStorage {
   private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Film create(@Valid @RequestBody Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Film {} created " + film.getId());
        return film;
    }

    @Override
    public Film update(@Valid @RequestBody Film newFilm) {
        if (!films.containsKey(newFilm.getId())) {
            throw new NotFoundException("Фильма с таким id нет");
        }
        films.put(newFilm.getId(), newFilm);
        log.info("Film {} updated " + newFilm.getId());
        return newFilm;
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        Film film = getFilm(filmId);
        film.getLikes().add(userId);
        update(film);
        log.info("User = {} like film = {}",userId,filmId);
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        Film film = getFilm(filmId);
        film.getLikes().remove(userId);
        update(film);
        log.info("User = {} remove like from film = {}",userId,filmId);
    }

    @Override
    public Film getFilm(Long id) {
        return films.get(id);
    }

    @Override
    public void deleteFilm(Long id) {
        log.info("Film {} removed", id);
        films.remove(id);
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
