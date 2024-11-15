package ru.yandex.practicum.filmorate.validation.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.film.impl.FilmDescriptionValidator;
import ru.yandex.practicum.filmorate.validation.film.impl.FilmDurationValidator;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmDurationValidatorTest {
    private Film firstFilm;
    private FilmValidator validator;
    private final String expectedMessage = "a duration of film should be positive";

    @BeforeEach
    public void setUp() {
        validator = new FilmDurationValidator();
        firstFilm = Film.builder()
                .description("description of first film")
                .releaseDate(LocalDate.of(1999, 9, 9))
                .duration(999)
                .name("Name of first film")
                .build();
    }

    @Test
    public void whenFilmDuration1Characters_shouldNotThrowValidationException() {
        firstFilm = firstFilm.toBuilder()
                .duration(1)
                .build();
        assertDoesNotThrow(() -> validator.validate(firstFilm));
    }

    @Test
    public void whenFilmDuration999Characters_shouldNotThrowValidationException() {
        assertDoesNotThrow(() -> validator.validate(firstFilm));
    }

    @Test
    public void whenFilmDuration0Characters_shouldThrowValidationException() {
        firstFilm = firstFilm.toBuilder()
                .duration(0)
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(firstFilm));
        String actual = exception.getMessage();

        assertEquals(expectedMessage, actual);
    }

    @Test
    public void whenFilmDescriptionMinus1Characters_shouldThrowValidationException() {
        firstFilm = firstFilm.toBuilder()
                .duration(-1)
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(firstFilm));
        String actual = exception.getMessage();

        assertEquals(expectedMessage, actual);
    }

    @Test
    public void whenFilmDescriptionMinus1000Characters_shouldThrowValidationException() {
        firstFilm = firstFilm.toBuilder()
                .duration(-1000)
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(firstFilm));
        String actual = exception.getMessage();

        assertEquals(expectedMessage, actual);
    }
}
