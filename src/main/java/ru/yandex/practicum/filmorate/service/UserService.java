package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserStorage userStorage;

    public Collection<User> findAllUsers() {
        return userStorage.findAll();
    }

    public User getUser(Long id) {
        if (userStorage.getUser(id) == null) {
            throw new NotFoundException("Юзер с id " + id + " не найден");
        }
        return userStorage.getUser(id);
    }

    public User deleteUser(Long id) {
        return userStorage.deleteUser(id);
    }

    public User createUser(User user) {
        return userStorage.create(user);
    }

    public User updateUser(User user) {
        return userStorage.update(user);
    }

    public User addFriend(Long userId, Long friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        checkValidUser(user);
        checkValidUser(friend);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
        log.info("User {} add friend {}", userId, friendId);
        return user;
    }

    public User deleteFriend(Long userId, Long friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        checkValidUser(user);
        checkValidUser(friend);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
        log.info("User {} delete friend {}", userId, friendId);
        return user;
    }

    public Collection<User> getListOfFriends(Long userId) {
        User user = userStorage.getUser(userId);
        checkValidUser(user);
        return user.getFriends().stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    public Collection<User> getListOfMutualFriends(Long userId, Long friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        checkValidUser(user);
        checkValidUser(friend);
        return user.getFriends().stream()
                .filter(id -> friend.getFriends().contains(id))
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    private void checkValidUser(User user) {
        if (user == null) {
            throw new NotFoundException("Юзер не может быть null");
        }
    }
}
