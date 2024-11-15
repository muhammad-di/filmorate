package ru.yandex.practicum.filmorate.validation.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.user.impl.EmailValidator;
import ru.yandex.practicum.filmorate.validation.user.impl.LoginValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginValidatorTest {
    private final String expected = "invalid login name";
    private final User firstUser = User.builder()
            .id(1)
            .email("fistUser@email.com")
            .birthday(LocalDate.of(2002, 1, 1))
            .name("firstUserName")
            .login("firstUserLogin")
            .build();

    private static UserValidator validator;

    @BeforeAll
    public static void setUp() {
        validator = new LoginValidator();
    }

    @Test
    public void whenLoginIsBlank_shouldThrowValidationException() {
        User user = firstUser.toBuilder().login("    ").build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(user));
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void whenLoginHasWhiteSpaces_shouldThrowValidationException() {
        User user = firstUser.toBuilder().login("  abc  ").build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(user));
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void whenLoginIsNull_shouldThrowValidationException() {
        User user = firstUser.toBuilder().login(null).build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(user));
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void whenLoginIsValid_shouldNotThrowValidationException() {
        assertDoesNotThrow(() -> validator.validate(firstUser));
    }
}
