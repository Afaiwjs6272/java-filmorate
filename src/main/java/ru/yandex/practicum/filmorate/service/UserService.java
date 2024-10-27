package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService(@Qualifier("UserRepository") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> getUsers() {
        return userStorage.findAll();
    }

    public User getUser(Long id) {
        User user = userStorage.getUser(id);
        checkIfUserExists(user);
        return user;
    }

    public User addUser(User user) {
        return userStorage.create(user);
    }

    public User updateUser(User user) {
        return userStorage.update(user);
    }

    public void addFriend(Long userId, Long friendId) {
        checkIfUserExists(userStorage.getUser(userId));
        checkIfUserExists(userStorage.getUser(friendId));
        userStorage.addFriend(userId, friendId);
    }

    public void removeFriend(Long userId, Long friendId) {
        checkIfUserExists(userStorage.getUser(userId));
        checkIfUserExists(userStorage.getUser(friendId));
        userStorage.removeFriend(userId, friendId);
    }

    public Collection<User> listOfFriends(Long userId) {
        checkIfUserExists(userStorage.getUser(userId));
        return userStorage.getUserFriends(userId);
    }

    public Collection<User> listOfCommonFriends(Long userId, Long friendId) {
        return userStorage.getCommonFriends(userId, friendId);
    }

    private void checkIfUserExists(User user) {
        if (user == null) {
            log.error("User with {} id not found", user.getId());
            throw new NotFoundException("User not found");
        }
    }
}