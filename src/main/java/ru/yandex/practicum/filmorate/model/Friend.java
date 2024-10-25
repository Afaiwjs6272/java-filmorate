package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Friend {
    private Long userId = 0L;
    private Long friendId = 0L;
}