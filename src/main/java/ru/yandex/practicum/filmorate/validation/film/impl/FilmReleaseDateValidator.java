package ru.yandex.practicum.filmorate.validation.film.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.film.FilmValidator;

import java.time.LocalDate;

@Component
public class FilmReleaseDateValidator implements FilmValidator {
    private static final LocalDate EARLIEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @Override
    public void validate(Film film) {
        if (film.getReleaseDate().isBefore(EARLIEST_RELEASE_DATE)) {
            throw new ValidationException("film release date should not be earlier then 28 december of 1895");
        }
    }
}
