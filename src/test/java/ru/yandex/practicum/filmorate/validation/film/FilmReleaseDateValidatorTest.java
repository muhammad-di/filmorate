package ru.yandex.practicum.filmorate.validation.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.film.impl.FilmReleaseDateValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmReleaseDateValidatorTest {
    private Film firstFilm;
    private FilmValidator validator;
    private final String expectedMessage = "film release date should not be earlier then 28 december of 1895";

    @BeforeEach
    public void setUp() {
        validator = new FilmReleaseDateValidator();
        firstFilm = Film.builder()
                .description("description of first film")
                .releaseDate(LocalDate.of(1999, 9, 9))
                .duration(999)
                .name("Name of first film")
                .build();
    }

    @Test
    public void whenFilmReleaseDateIs28December1895_shouldNotThrowValidationException() {
        firstFilm = firstFilm.toBuilder()
                .releaseDate(LocalDate.of(1895, 12, 28))
                .build();
        assertDoesNotThrow(() -> validator.validate(firstFilm));
    }

    @Test
    public void whenFilmReleaseDateIsADayAfter28December1895_shouldNotThrowValidationException() {
        firstFilm = firstFilm.toBuilder()
                .releaseDate(LocalDate.of(1895, 12, 29))
                .build();
        assertDoesNotThrow(() -> validator.validate(firstFilm));
    }

    @Test
    public void whenFilmReleaseDateIsA100YearsAfter28December1895_shouldNotThrowValidationException() {
        firstFilm = firstFilm.toBuilder()
                .releaseDate(LocalDate.of(1895, 12, 28).plusYears(100))
                .build();
        assertDoesNotThrow(() -> validator.validate(firstFilm));
    }

    @Test
    public void whenFilmReleaseDateIsAOneDayBefore28December1895_shouldThrowValidationException() {
        firstFilm = firstFilm.toBuilder()
                .releaseDate(LocalDate.of(1895, 12, 28).minusDays(1))
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(firstFilm));
        String actual = exception.getMessage();

        assertEquals(expectedMessage, actual);
    }

    @Test
    public void whenFilmReleaseDateIsA100YearsBefore28December1895_shouldThrowValidationException() {
        firstFilm = firstFilm.toBuilder()
                .releaseDate(LocalDate.of(1895, 12, 28).minusYears(100))
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(firstFilm));
        String actual = exception.getMessage();

        assertEquals(expectedMessage, actual);
    }
}
