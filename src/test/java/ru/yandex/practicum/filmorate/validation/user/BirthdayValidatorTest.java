package ru.yandex.practicum.filmorate.validation.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.user.impl.BirthdayValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class BirthdayValidatorTest {
    private final User firstUser = User.builder()
            .id(1)
            .email("fistUser@email.com")
            .name("firstUserName")
            .login("firstUserLogin")
            .build();

    private static UserValidator validator;
    private final String expected = "invalid birthday value";

    @BeforeAll
    public static void setUp() {
        validator = new BirthdayValidator();
    }

    @Test
    public void whenBirthdayInAFutureYearApart_ShouldThrowValidationException() {
        String actual;
        firstUser.setBirthday(LocalDate.now().plusYears(1));

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> validator.validate(firstUser)
        );
        actual = exception.getMessage();

        log.info("\nexpected: {}\nactual:   {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    public void whenBirthdayInAFutureDayApart_ShouldThrowValidationException() {
        String actual;
        firstUser.setBirthday(LocalDate.now().plusDays(1));

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> validator.validate(firstUser)
        );
        actual = exception.getMessage();

        log.info("\nexpected: {}\nactual:   {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    public void whenBirthdayInPresent_ShouldNotThrowValidationException() {
        firstUser.setBirthday(LocalDate.now());

        assertDoesNotThrow(() -> validator.validate(firstUser));
    }

    @Test
    public void whenBirthdayADayAgo_ShouldNotThrowValidationException() {
        firstUser.setBirthday(LocalDate.now().minusDays(1));

        assertDoesNotThrow(() -> validator.validate(firstUser));
    }

    @Test
    public void whenBirthdayAYearAgo_ShouldNotThrowValidationException() {
        firstUser.setBirthday(LocalDate.now().minusYears(1));

        assertDoesNotThrow(() -> validator.validate(firstUser));
    }
}
