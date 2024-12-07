package ru.yandex.practicum.filmorate.service.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exseption.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.impl.FilmServiceImpl;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.impl.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.validation.film.FilmValidator;
import ru.yandex.practicum.filmorate.validation.film.impl.FilmDescriptionValidator;
import ru.yandex.practicum.filmorate.validation.film.impl.FilmDurationValidator;
import ru.yandex.practicum.filmorate.validation.film.impl.FilmNameValidator;
import ru.yandex.practicum.filmorate.validation.film.impl.FilmReleaseDateValidator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilmServiceImplTest {
    private FilmService filmService;
    private FilmStorage filmStorage;
    private FilmValidator filmDescriptionValidator;
    private FilmValidator filmDurationValidator;
    private FilmValidator filmNameValidator;
    private FilmValidator filmReleaseDateValidator;

    private Film firstFilm;

    @BeforeEach
    public void init() {
        filmStorage = mock(InMemoryFilmStorage.class);

        filmDescriptionValidator = mock(FilmDescriptionValidator.class);
        filmDurationValidator = mock(FilmDurationValidator.class);
        filmNameValidator = mock(FilmNameValidator.class);
        filmReleaseDateValidator = mock(FilmReleaseDateValidator.class);

        List<FilmValidator> filmValidators = List.of(
                filmDescriptionValidator,
                filmDurationValidator,
                filmNameValidator,
                filmReleaseDateValidator
        );

        filmService = new FilmServiceImpl(filmStorage, filmValidators);
    }

    @BeforeEach
    public void setUp() {
        firstFilm = Film.builder()
                .name("first film name")
                .releaseDate(LocalDate.of(1999, 9, 9))
                .description("first film description")
                .likes(new HashSet<>())
                .duration(9999)
                .build();
    }

    @Test
    public void testCreate_whenGivenArgumentFilm_ShouldReturnUserWithId() {
        Film actual;
        Film expected = firstFilm.toBuilder().id(1).build();

        when(filmStorage.create(firstFilm)).thenReturn(firstFilm.toBuilder().id(1).build());
        actual = filmService.create(firstFilm);

        assertEquals(expected, actual);
    }

    @Test
    public void testCreate_whenGivenArgumentFilmWithInvalidDescription_ShouldTrowValidationException() {
        String actual;
        String expected = "description should not be more than 200 characters";

        doThrow(new ValidationException("description should not be more than 200 characters"))
                .when(filmDescriptionValidator).validate(firstFilm);

        ValidationException exception = assertThrows(ValidationException.class,
                () -> filmService.create(firstFilm));

        actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    public void testCreate_whenGivenArgumentFilmWithInvalidDuration_ShouldTrowValidationException() {
        String actual;
        String expected = "a duration of film should be positive";

        doThrow(new ValidationException("a duration of film should be positive"))
                .when(filmDurationValidator).validate(firstFilm);

        ValidationException exception = assertThrows(ValidationException.class,
                () -> filmService.create(firstFilm));

        actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    public void testCreate_whenGivenArgumentFilmWithInvalidName_ShouldTrowValidationException() {
        String actual;
        String expected = "invalid film name";

        doThrow(new ValidationException("invalid film name"))
                .when(filmNameValidator).validate(firstFilm);

        ValidationException exception = assertThrows(ValidationException.class,
                () -> filmService.create(firstFilm));

        actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    public void testCreate_whenGivenArgumentFilmWithInvalidRelease_ShouldTrowValidationException() {
        String actual;
        String expected = "film release date should not be earlier then 28 december of 1895";

        doThrow(new ValidationException("film release date should not be earlier then 28 december of 1895"))
                .when(filmReleaseDateValidator).validate(firstFilm);

        ValidationException exception = assertThrows(ValidationException.class,
                () -> filmService.create(firstFilm));

        actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    public void testFindById_whenGivenArgumentId_ShouldReturnFilm() {
        long firstFilmId = 1L;
        Film actual;
        Film expected = firstFilm.toBuilder().id(firstFilmId).build();
        firstFilm.setId(firstFilmId);

        when(filmStorage.findById(firstFilmId)).thenReturn(Optional.of(firstFilm));

        actual = filmService.findByIf(firstFilmId);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindById_whenGivenArgumentIdNonExistingFilm_ShouldThrowEntityNotFoundException() {
        long firstFilmId = 222L;
        String actual;
        String expected = "Film does not exist";
        firstFilm.setId(firstFilmId);

        when(filmStorage.findById(firstFilmId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> filmService.findByIf(firstFilmId)
        );
        actual = exception.getMessage();


        assertEquals(expected, actual);
    }
}
