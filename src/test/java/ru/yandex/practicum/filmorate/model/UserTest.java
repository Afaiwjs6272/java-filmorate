package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void beforeAll() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void afterAll() {
        validatorFactory.close();
    }

    @Test
    public void shouldNotCreateUser() {
        User user = new User();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldCreateUserWithBlankName() {
        User user = new User();
        user.setLogin("adm");
        user.setEmail("ads@mail.ru");
        user.setBirthday(LocalDate.of(2000, 12, 11));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    public void shouldNotCreateUserWhenEmailIsWrong() {
        User user = new User();
        user.setLogin("adm");
        user.setEmail("adsmail.ru");
        user.setBirthday(LocalDate.of(2000, 12, 11));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.stream().filter(c -> c.getPropertyPath().toString().equals("email")).count());
    }

    @Test
    public void shouldNotCreateUserWhenEmailIsEmpty() {
        User user = new User();
        user.setLogin("adm");
        user.setBirthday(LocalDate.of(2000, 12, 11));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(2, violations.stream().filter(c -> c.getPropertyPath().toString().equals("email")).count());
    }

    @Test
    public void shouldNotCreateUserWhenBirthdayInFuture() {
        User user = new User();
        user.setLogin("adm");
        user.setEmail("adm@mail.ru");
        user.setBirthday(LocalDate.of(2049, 12, 11));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.stream().filter(c -> c.getPropertyPath().toString().equals("birthday")).count());
    }

    @Test
    public void shouldNotCreateUserWhenLoginContainsWhitespace() {
        User user = new User();
        user.setLogin("adm rads");
        user.setEmail("asd@mail.ru");
        user.setBirthday(LocalDate.of(2000, 12, 11));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.stream().filter(c -> c.getPropertyPath().toString().equals("login")).count());
    }
}
