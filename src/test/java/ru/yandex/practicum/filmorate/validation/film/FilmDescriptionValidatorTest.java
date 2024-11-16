package ru.yandex.practicum.filmorate.validation.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.film.impl.FilmDescriptionValidator;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmDescriptionValidatorTest {
    private Film firstFilm;
    private FilmValidator validator;
    private final String expectedMessage = "description should not be more than 200 characters";

    @BeforeEach
    public void setUp() {
        validator = new FilmDescriptionValidator();
        firstFilm = Film.builder()
                .description("description of first film")
                .releaseDate(LocalDate.of(1999, 9, 9))
                .duration(999)
                .name("Name of first film")
                .build();
    }

    @Test
    public void whenFilmDescription25Characters_shouldNotThrowValidationException() {
        assertDoesNotThrow(() -> validator.validate(firstFilm));
    }

    @Test
    public void whenFilmDescription200Characters_shouldNotThrowValidationException() {
        char[] charArr = new char[200];
        Arrays.fill(charArr, 'f');
        String descriptionLength200 = new String(charArr);
        firstFilm = firstFilm.toBuilder()
                .description(descriptionLength200)
                .build();

        assertDoesNotThrow(() -> validator.validate(firstFilm));
    }

    @Test
    public void whenFilmDescription201Characters_shouldThrowValidationException() {
        char[] charArr = new char[201];
        Arrays.fill(charArr, 'f');
        String descriptionLength201 = new String(charArr);
        firstFilm = firstFilm.toBuilder()
                .description(descriptionLength201)
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(firstFilm));
        String actual = exception.getMessage();

        assertEquals(expectedMessage, actual);
    }

    @Test
    public void whenFilmDescription1000Characters_shouldThrowValidationException() {
        char[] charArr = new char[1000];
        Arrays.fill(charArr, 'f');
        String descriptionLength1000 = new String(charArr);
        firstFilm = firstFilm.toBuilder()
                .description(descriptionLength1000)
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(firstFilm));
        String actual = exception.getMessage();

        assertEquals(expectedMessage, actual);
    }
}
