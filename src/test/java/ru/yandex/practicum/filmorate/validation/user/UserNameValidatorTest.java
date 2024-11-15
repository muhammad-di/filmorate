package ru.yandex.practicum.filmorate.validation.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.user.impl.UserNameValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserNameValidatorTest {
    private final User firstUser = User.builder()
            .id(1)
            .email("fistUser@email.com")
            .birthday(LocalDate.of(2002, 1, 1))
            .login("firstUserLogin")
            .build();

    private static UserValidator validator;

    @BeforeAll
    public static void setUp() {
        validator = new UserNameValidator();
    }

    @Test
    public void whenNameIsNull_shouldUseLoginAsUserName() {
        String expected = "firstUserLogin";
        String actual;

        validator.validate(firstUser);
        actual = firstUser.getName();

        assertEquals(expected, actual);
    }
}
