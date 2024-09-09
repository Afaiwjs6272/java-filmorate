package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("User {} created" + user.getId());
        return user;
    }

    @PutMapping
    public User update(@RequestBody User newUser) {
        if (!users.containsKey(newUser.getId())) {
            throw new ValidationException("Пользователя с таким id не существует");
        }
        users.put(newUser.getId(), newUser);
        log.info("User {} updated " + newUser.getId());
        return newUser;
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
