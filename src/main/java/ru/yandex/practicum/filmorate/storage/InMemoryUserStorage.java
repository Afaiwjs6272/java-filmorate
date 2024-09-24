package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private HashMap<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User getUser(Long id) {
        return users.get(id);
    }

    @Override
    public User create(@Valid @RequestBody User user) {
        validateUserName(user);
        user.setId(getNextId());
        users.put(user.getId(),user);
        log.info("User {} created " + user.getId());
        return user;
    }

    @Override
    public User update(@Valid @RequestBody User newUser) {
        if (!users.containsKey(newUser.getId())) {
            throw new NotFoundException("Пользователя с таким id нет");
        }
        validateUserName(newUser);
        users.put(newUser.getId(), newUser);
        log.info("User {} updated " + newUser.getId());
        return newUser;
    }

    @Override
    public User deleteUser(Long id) {
        log.info("User {} deleted",id);
        return users.remove(id);
    }

    private void validateUserName(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
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
