package ru.yandex.practicum.filmorate.validation.film.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.film.FilmValidator;

@Component
public class FilmDurationValidator implements FilmValidator {
    private final static long MIN_DURATION = 0;

    @Override
    public void validate(Film film) {
        if (film.getDuration() <= MIN_DURATION) {
            throw new ValidationException("a duration of film should be positive");
        }
    }
}
