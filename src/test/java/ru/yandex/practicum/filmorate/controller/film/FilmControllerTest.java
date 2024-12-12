package ru.yandex.practicum.filmorate.controller.film;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.service.film.impl.FilmServiceImpl;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Slf4j
public class FilmControllerTest {
    private FilmController controller;
    private FilmService filmService;
    private long firstFilmId;
    private Film firstFilm;
    private Film secondFilm;
    private Film thirdFilm;

    @BeforeEach
    public void init() {
        filmService = mock(FilmServiceImpl.class);
        controller = new FilmController(filmService);
        firstFilmId = 1L;
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
        Film actual;
        Film expected = Film.builder()
                .id(firstFilmId)
                .description("description of first film")
                .releaseDate(LocalDate.of(1999, 9, 9))
                .duration(999)
                .name("Name of first film")
                .build();

        when(filmService.create(firstFilm)).thenReturn(firstFilm.toBuilder().id(1).build());
        actual = controller.create(firstFilm);


        log.debug("\nexpected: {}\nactual:   {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate_whenGivenArgumentFilm_ShouldReturnFilm() {
        Film actual;
        Film expected = Film.builder()
                .id(firstFilmId)
                .description("description of first film")
                .releaseDate(LocalDate.of(1999, 9, 9))
                .duration(999)
                .name("Name of first film")
                .build();
        firstFilm.setId(firstFilmId);

        when(filmService.update(firstFilm)).thenReturn(firstFilm.toBuilder().id(firstFilmId).build());
        actual = controller.update(firstFilm);

        log.debug("\nexpected: {}\nactual:   {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindById_whenGivenLong_ShouldReturnFilm() {
        Film actual;
        Film expected = Film.builder()
                .id(firstFilmId)
                .description("description of first film")
                .releaseDate(LocalDate.of(1999, 9, 9))
                .duration(999)
                .name("Name of first film")
                .build();
        firstFilm.setId(firstFilmId);

        when(filmService.findById(firstFilmId)).thenReturn(firstFilm.toBuilder().id(firstFilmId).build());
        actual = controller.findById(firstFilmId);

        log.debug("\nexpected: {}\nactual:   {}", expected, actual);
        assertEquals(expected, actual);
    }


    @Test
    public void testFindAll_whenGivenNoArgument_ShouldReturnCollectionOfUsers() {
        long secondFilmId = 2L;
        long thirdFilmId = 3L;
        Collection<Film> actual;
        Collection<Film> expected = List.of(
                firstFilm.toBuilder().id(firstFilmId).build(),
                secondFilm.toBuilder().id(secondFilmId).build(),
                thirdFilm.toBuilder().id(thirdFilmId).build()
        );

        when(filmService.findAll()).thenReturn(List.of(
                firstFilm.toBuilder().id(firstFilmId).build(),
                secondFilm.toBuilder().id(secondFilmId).build(),
                thirdFilm.toBuilder().id(thirdFilmId).build()
        ));
        actual = controller.findAll();

        log.debug("\nexpected: {}\nactual:   {}", expected, actual);
        assertIterableEquals(expected, actual);
    }


}
