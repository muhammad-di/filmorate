package ru.yandex.practicum.filmorate.controller.film;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.film.FilmValidator;
import ru.yandex.practicum.filmorate.validation.film.impl.FilmDescriptionValidator;
import ru.yandex.practicum.filmorate.validation.film.impl.FilmDurationValidator;
import ru.yandex.practicum.filmorate.validation.film.impl.FilmNameValidator;
import ru.yandex.practicum.filmorate.validation.film.impl.FilmReleaseDateValidator;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
public class FilmControllerTest {
    private FilmController controller;
    private Film firstFilm;
    private Film secondFilm;
    private Film thirdFilm;

    @BeforeEach
    public void init() {
        List<FilmValidator> list = List.of(
                new FilmDescriptionValidator(),
                new FilmDurationValidator(),
                new FilmNameValidator(),
                new FilmReleaseDateValidator()
        );
        controller = new FilmController(list);
        firstFilm = Film.builder()
                .description("description of first film")
                .releaseDate(LocalDate.of(1999, 9, 9))
                .duration(999)
                .name("Name of first film")
                .build();
        secondFilm = Film.builder()
                .description("description of second film")
                .releaseDate(LocalDate.of(1999, 9, 10))
                .duration(888)
                .name("Name of second film")
                .build();
        thirdFilm = Film.builder()
                .description("description of third film")
                .releaseDate(LocalDate.of(1999, 9, 11))
                .duration(777)
                .name("Name of third film")
                .build();
    }


    @Test
    public void testCreate_whenGivenArgumentUFilmWithoutIdParameter_ShouldReturnFilm() {
        Film actual = controller.create(firstFilm);
        Film expected = Film.builder()
                .id(actual.getId())
                .description("description of first film")
                .releaseDate(LocalDate.of(1999, 9, 9))
                .duration(999)
                .name("Name of first film")
                .build();

        log.info("\nexpected: {}\nactual:   {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate_whenGivenArgumentPutWithChangedName_ShouldReturnFilmWithNewName() {
        Film returnedFilm = controller.create(firstFilm);
        Film actual;
        Film expected = Film.builder()
                .id(returnedFilm.getId())
                .description("description of first film")
                .releaseDate(LocalDate.of(1999, 9, 9))
                .duration(999)
                .name("New name of first film")
                .build();
        Film updatedFirstFilm = firstFilm.toBuilder()
                .id(returnedFilm.getId())
                .name("New name of first film")
                .build();
        actual = controller.update(updatedFirstFilm);

        log.info("\nexpected: {}\nactual:   {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate_whenGivenArgumentFilmWithChangedDescription_ShouldReturnFilmWithNewDescription() {
        Film returnedFilm = controller.create(firstFilm);
        Film actual;
        Film expected = Film.builder()
                .id(returnedFilm.getId())
                .description("new description of first film")
                .releaseDate(LocalDate.of(1999, 9, 9))
                .duration(999)
                .name("Name of first film")
                .build();
        Film updatedFirstFilm = firstFilm.toBuilder()
                .id(returnedFilm.getId())
                .description("new description of first film")
                .build();
        actual = controller.update(updatedFirstFilm);

        log.info("\nexpected: {}\nactual:   {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate_whenGivenFilmWhoDoesNotExist_ShouldThrowValidationException() {
        String actual;
        String expected = "Film does not exist";
        Film filmWhoDoesNotExist = firstFilm.toBuilder()
                .id(10)
                .build();

        controller.create(firstFilm);
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> controller.update(filmWhoDoesNotExist)
        );
        actual = exception.getMessage();

        log.info("\nexpected: {}\nactual:   {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindAll_whenGivenNoArgument_ShouldReturnCollectionOfUsers() {
        Film firstReturnedFilm = controller.create(firstFilm);
        Film secondReturnedFilm = controller.create(secondFilm);
        Film thirdReturnedFilm = controller.create(thirdFilm);
        Collection<Film> actual;
        Collection<Film> expected = List.of(
                firstFilm.toBuilder().id(firstReturnedFilm.getId()).build(),
                secondFilm.toBuilder().id(secondReturnedFilm.getId()).build(),
                thirdFilm.toBuilder().id(thirdReturnedFilm.getId()).build()
        );
        actual = controller.findAll();

        log.info("\nexpected: {}\nactual:   {}", expected, actual);
        assertIterableEquals(expected, actual);
    }


}
