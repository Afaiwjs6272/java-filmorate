package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    Collection<User> findAll();

    User getUser(Long id);

    User create(User user);

    User update(User user);

    User deleteUser(Long id);
}