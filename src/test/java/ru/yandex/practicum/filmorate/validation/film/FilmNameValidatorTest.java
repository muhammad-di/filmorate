package ru.yandex.practicum.filmorate.validation.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.film.impl.FilmNameValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmNameValidatorTest {
    private Film firstFilm;
    private FilmValidator validator;
    private final String expectedMessage = "invalid film name";

    @BeforeEach
    public void setUp() {
        validator = new FilmNameValidator();
        firstFilm = Film.builder()
                .description("description of first film")
                .releaseDate(LocalDate.of(1999, 9, 9))
                .duration(999)
                .name("Name of first film")
                .build();
    }

    @Test
    public void whenFilmNameIsText_shouldNotThrowValidationException() {
        assertDoesNotThrow(() -> validator.validate(firstFilm));
    }

    @Test
    public void whenFilmNameIsBlank_shouldThrowValidationException() {
        firstFilm = firstFilm.toBuilder()
                .name("   ")
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(firstFilm));
        String actual = exception.getMessage();

        assertEquals(expectedMessage, actual);
    }

    @Test
    public void whenFilmNameIsNull_shouldThrowValidationException() {
        firstFilm = firstFilm.toBuilder()
                .name(null)
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(firstFilm));
        String actual = exception.getMessage();

        assertEquals(expectedMessage, actual);
    }
}
