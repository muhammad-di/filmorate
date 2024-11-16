package ru.yandex.practicum.filmorate.validation.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.user.impl.EmailValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailValidatorTest {
    private final String expected = "invalid email name";
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
        validator = new EmailValidator();
    }

    @Test
    public void whenEmailBlank_shouldThrowValidationException() {
        User user = firstUser.toBuilder().email("    ").build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(user));
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void whenEmailSymbolsAndWhiteSpaces_shouldThrowValidationException() {
        User user = firstUser.toBuilder().email("abc ").build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(user));
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void whenEmailNull_shouldThrowValidationException() {
        User user = firstUser.toBuilder().email(null).build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(user));
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void whenEmailSimpleText_shouldThrowValidationException() {
        User user = firstUser.toBuilder().email("abc").build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(user));
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void whenEmailIsValid_shouldNotThrowValidationException() {
        assertDoesNotThrow(() -> validator.validate(firstUser));
    }
}
