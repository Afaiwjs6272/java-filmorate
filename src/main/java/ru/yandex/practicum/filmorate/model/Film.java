package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.filmorate.validation.MinimalDate;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class Film {
    private Long id;
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
    @Size(max = 200)
    private String description;
    @MinimalDate
    private LocalDate releaseDate;
    @Positive
    private Integer duration;
}
