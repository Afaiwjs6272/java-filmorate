package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class User {
    private Long id;
    @NotNull
    @NotEmpty
    @Email
    private String email;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^\\S*$", message = "No whitespaces allowed")
    private String login;
    private String name;
    @Past
    private LocalDate birthday;


    public String getName() {
        if (name == null) {
            return login;
        }
        return name;
    }
}
