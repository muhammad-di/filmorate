package ru.yandex.practicum.filmorate.validation.film.impl;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.exseption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.film.FilmValidator;

@Component
public class FilmNameValidator implements FilmValidator {
    @Override
    public void validate(Film film) {
        if (!StringUtils.hasText(film.getName())) {
            throw new ValidationException("invalid film name");
        }
    }
}
