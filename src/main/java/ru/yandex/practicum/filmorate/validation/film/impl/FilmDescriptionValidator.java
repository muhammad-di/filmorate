package ru.yandex.practicum.filmorate.validation.film.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.film.FilmValidator;

@Component
public class FilmDescriptionValidator implements FilmValidator {
    private final static int MAX_LENGTH_OF_FILM_DESCRIPTION = 200;

    @Override
    public void validate(Film film) {
        if (film.getDescription().length() > MAX_LENGTH_OF_FILM_DESCRIPTION) {
            throw new ValidationException("description should not be more than 200 characters");
        }
    }
}
